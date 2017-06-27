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
import com.skyfervor.rm.vo.ActivityVo;

@Controller
@RequestMapping(value = "/activity")
public class ActivityModifyController {
	@Autowired
	private ActivityService activityService;
	
	// 活动修改 - 页面
	@RequestMapping(value = "/modifypage")
	@ResponseBody
	public ModelAndView modifyPage(@RequestParam("urlParam") Long activityId) throws Exception {
		// 获取活动信息
		ActivityVo activityVo = activityService.queryWithPersonnelById(activityId);

		ModelAndView mv = new ModelAndView("/activity/activity_modify");
		mv.addObject("activityVo", activityVo);
		return mv;
	}

	// 活动修改 - 权限判定
	@RequestMapping(value = "/modifypower")
	@ResponseBody
	public Byte modifyPower(Long activityId) throws Exception {
		ActivityVo vo = new ActivityVo();
		vo.setActivityId(activityId);
		vo.setModifyUserId(UserContext.getUserID());
		return activityService.checkModifyPower(vo, true);
	}

	// 活动修改
	@RequestMapping(value = "/modify")
	@ResponseBody
	public Boolean modify(String input) throws Exception {
		ActivityVo vo = JsonTranslator.fromString(input, ActivityVo.class);
		vo.setModifyUserId(UserContext.getUserID());
		return activityService.updateModify(vo);
	}
	
	@RequestMapping(value = "/stop")
	@ResponseBody
	public Byte stop(Long activityId) throws Exception {
		ActivityVo vo = new ActivityVo();
		vo.setActivityId(activityId);
		vo.setModifyUserId(UserContext.getUserID());
		return activityService.updateStop(vo);
	}

}
