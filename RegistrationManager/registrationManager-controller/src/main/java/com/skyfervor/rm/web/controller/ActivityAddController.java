package com.skyfervor.rm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.vo.ActivityVo;

@Controller
@RequestMapping(value = "/activity")
public class ActivityAddController {
	@Autowired
	private ActivityService activityService;

	// 活动添加
	@RequestMapping(value = "/add")
	@ResponseBody
	public Boolean add(String input) throws Exception {
		ActivityVo vo = JsonTranslator.fromString(input, ActivityVo.class);
		vo.setPublisherId(UserContext.getUserID());
		vo = activityService.insert(vo);
		return vo != null ? true : false;
	}
}
