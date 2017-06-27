package com.skyfervor.rm.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.user.UserVo;
import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.framework.utility.StringUtils;
import com.skyfervor.framework.vobase.PageVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;

	// 活动发布人 - 智能筛选
	@RequestMapping(value = "/suggest")
	@ResponseBody
	public List<UserVo> suggest(String term) throws Exception {
		if (StringUtils.isEmpty(term))
			return null;

		// 设置用户名
		UserVo userVo = new UserVo();
		userVo.setUserName(term);
		return userService.queryListByName(userVo);
	}

	@RequestMapping(value = "/userdata")
	@ResponseBody
	public PageVo<UserVo> userData(String input, Integer page, Integer rows) throws Exception {
		page = page != null ? page : 1;
		rows = rows != null ? rows : 10;
		UserVo vo = JsonTranslator.fromString(input, UserVo.class);
		return userService.queryPage(vo, page, rows);
	}

	@RequestMapping(value = "/checkaddpower")
	@ResponseBody
	public Byte checkAddPower(String loginName) throws Exception {
		UserVo vo = new UserVo();
		vo.setLoginName(loginName);
		return userService.checkAddPower(vo);
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public Boolean add(String input) throws Exception {
		UserVo vo = JsonTranslator.fromString(input, UserVo.class);
		vo = userService.insert(vo);
		return vo != null ? true : false;
	}

	@RequestMapping(value = "/modifypage")
	@ResponseBody
	public ModelAndView modifypage(@RequestParam("urlParam") Long userId) throws Exception {
		UserVo vo = userService.queryById(userId);
		ModelAndView mv = new ModelAndView("/user/user_modify");
		mv.addObject("userVo", vo);
		return mv;
	}

	@RequestMapping(value = "/modify")
	@ResponseBody
	public Boolean modify(String input) throws Exception {
		UserVo vo = JsonTranslator.fromString(input, UserVo.class);
		return userService.update(vo);
	}

	@RequestMapping(value = "/del")
	@ResponseBody
	public void del(Long userId) throws Exception {
		userService.delete(userId);
	}
}
