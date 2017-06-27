package com.skyfervor.rm.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.usertodo.UserTodoService;
import com.skyfervor.framework.usertodo.UserTodoVo;
import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.framework.vobase.PageVo;

@Controller
@RequestMapping(value = "/usertodo")
public class UserTodoController {

	@Autowired
	private UserTodoService userTodoService;

	@RequestMapping(value = "/count")
	@ResponseBody
	public Long count() throws Exception {
		UserTodoVo userTodoVo = new UserTodoVo();
		userTodoVo.setBdUserId(UserContext.getUserID());
		userTodoVo.setTodoStatus(Constant.UserTodo.UNREAD);
		return userTodoService.getCount(userTodoVo);
	}

	@RequestMapping(value = "/usertododata")
	@ResponseBody
	public PageVo<UserTodoVo> userTodoData(String input, Integer page, Integer rows,
			HttpServletRequest request) throws Exception {
		page = page != null ? page : 1;
		rows = rows != null ? rows : 10;
		UserTodoVo vo = JsonTranslator.fromString(input, UserTodoVo.class);
		vo.setBdUserId(UserContext.getUserID());

		return userTodoService.queryPage(vo, page, rows);
	}

	@RequestMapping(value = "/detailpage")
	public ModelAndView detail(@RequestParam(value = "urlParam") String voString,
			RedirectAttributes attr) throws Exception {
		UserTodoVo vo = JsonTranslator.fromString(voString, UserTodoVo.class);
		userTodoService.updateToReaded(vo);

		attr.addAttribute("urlParam", vo.getAssociatedDataId());
		return new ModelAndView("redirect:/activity/detailpage");
	}

}
