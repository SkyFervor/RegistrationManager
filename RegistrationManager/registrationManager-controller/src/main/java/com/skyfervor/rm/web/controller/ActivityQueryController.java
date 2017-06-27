package com.skyfervor.rm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.framework.vobase.PageVo;
import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.service.ActivityUserMappingService;
import com.skyfervor.rm.vo.ActivityVo;
import com.skyfervor.rm.vo.ActivityUserMappingVo;

@Controller
@RequestMapping(value = "/activity")
public class ActivityQueryController {
	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityUserMappingService activityUserMappingService;

	// 活动列表 - 分页
	@RequestMapping(value = "/activitydata")
	@ResponseBody
	public PageVo<ActivityVo> activityData(String input, Integer page, Integer rows)
			throws Exception {
		page = page != null ? page : 1;
		rows = rows != null ? rows : 10;
		ActivityVo vo = JsonTranslator.fromString(input, ActivityVo.class);

		// 获取活动信息分页
		return activityService.queryPage(vo, page, rows);
	}

	// 活动详情 - 页面
	@RequestMapping(value = "/detailpage")
	public ModelAndView detailPage(@RequestParam("urlParam") Long activityId) throws Exception {
		//ActivityVo vo = JsonTranslator.fromString(urlParam, ActivityVo.class);

		// 获取活动信息
		ActivityVo activityVo = activityService.queryById(activityId);

		ModelAndView mv = new ModelAndView("/activity/activity_detail");
		mv.addObject("activityVo", activityVo);
		return mv;
	}

	// 活动人员 - 分页
	@RequestMapping(value = "/personneldata")
	@ResponseBody
	public PageVo<ActivityUserMappingVo> personnelData(String input, Integer page, Integer rows)
			throws Exception {
		page = page != null ? page : 1;
		rows = rows != null ? rows : 10;
		ActivityUserMappingVo vo = JsonTranslator.fromString(input, ActivityUserMappingVo.class);

		return activityUserMappingService.queryPage(vo, page, rows);
	}
}
