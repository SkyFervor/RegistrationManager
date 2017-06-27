package com.skyfervor.rm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.service.ActivityUserMappingService;
import com.skyfervor.rm.vo.ActivityVo;
import com.skyfervor.rm.vo.ActivityUserMappingVo;

@Controller
@RequestMapping(value = "/activity")
public class ActivityApplyController {
	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityUserMappingService activityUserMappingService;

	// 活动报名 - 页面
	@RequestMapping(value = "/enrollpage")
	@ResponseBody
	public ModelAndView enrollPage(@RequestParam("urlParam") Long activityId) throws Exception {
		//ActivityUserMappingVo vo = JsonTranslator.fromString(urlParam, ActivityUserMappingVo.class);

		// 获取活动信息
		ActivityVo activityVo = activityService.queryById(activityId);

		// 获取登录者报名信息
		ActivityUserMappingVo vo = new ActivityUserMappingVo();
		vo.setActivityId(activityId);
		vo.setUserId(UserContext.getUserID());
		ActivityUserMappingVo activityUserMappingVo = activityUserMappingService
				.query(vo);

		ModelAndView mv = new ModelAndView("/activity/activity_enroll");
		mv.addObject("activityVo", activityVo);
		mv.addObject("activityUserMappingVo", activityUserMappingVo);
		return mv;
	}

	// 活动报名 - 权限判定
	@RequestMapping(value = "/enrollpower")
	@ResponseBody
	public Byte enrollPower(Long activityId) throws Exception {
		ActivityUserMappingVo vo = new ActivityUserMappingVo();
		vo.setActivityId(activityId);
		vo.setUserId(UserContext.getUserID());
		return activityUserMappingService.checkEnrollPower(vo);
	}

	// 活动报名
	@RequestMapping(value = "/enroll")
	@ResponseBody
	public Boolean enroll(String input) throws Exception {
		ActivityUserMappingVo vo = JsonTranslator.fromString(input, ActivityUserMappingVo.class);
		vo.setUserId(UserContext.getUserID());
		return activityUserMappingService.updateToEnroll(vo);
	}

}
