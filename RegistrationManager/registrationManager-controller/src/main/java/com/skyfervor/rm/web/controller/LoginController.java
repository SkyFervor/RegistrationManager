package com.skyfervor.rm.web.controller;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.user.UserVo;
import com.skyfervor.framework.utility.CookieUtils;
import com.skyfervor.framework.utility.JsonTranslator;
import com.skyfervor.framework.utility.StringUtils;
import com.skyfervor.framework.utility.TokenUtils;
import com.skyfervor.framework.utility.VerifyCodeUtils;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping("/authimage")
	public void authImage(HttpServletRequest request, HttpServletResponse response, Integer width,
			Integer height) throws Exception {
		response.setContentType("image/jpeg");

		//生成随机字串  
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		//存入会话session  
		HttpSession session = request.getSession(true);
		session.setAttribute(Constant.Login.NAME_AUTH, verifyCode.toUpperCase());
		//生成图片  
		VerifyCodeUtils.outputImage(width, height, response.getOutputStream(), verifyCode);
	}

	/**
	 * 登录
	 * 
	 * @param vo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Byte login(String input, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserVo vo = JsonTranslator.fromString(input, UserVo.class);
		HttpSession session = request.getSession();

		// 验证验证码
		Object auth = session.getAttribute(Constant.Login.NAME_AUTH);
		if (auth != null && !StringUtils.IsEquals(auth.toString(), vo.getAuth().toUpperCase()))
			return Constant.Login.AUTHCODE_INCORRECT;

		// 获取用户信息
		UserVo userVo = userService.queryLoginByName(vo);
		if (userVo == null)
			return Constant.Login.USERNAME_INCORRECT;

		// 验证密码
		String enPassword = TokenUtils.getPassword(userVo.getPassword(), auth.toString());
		if (!StringUtils.IsEquals(enPassword, vo.getPassword()))
			return Constant.Login.PASSWORD_INCORRECT;

		// 处理session和cookie
		session.removeAttribute(Constant.Login.NAME_AUTH);

		Cookie cookieUserId = new Cookie(Constant.Cookie.NAME_USERID,
				userVo.getUserId().toString());
		CookieUtils.setCookies(response, cookieUserId, "/", Constant.Cookie.AGE);

		Cookie cookieUserName = new Cookie(Constant.Cookie.NAME_USERNAME,
				URLEncoder.encode(userVo.getUserName(), "UTF-8"));
		CookieUtils.setCookies(response, cookieUserName, "/", Constant.Cookie.AGE);

		String token = TokenUtils.getToken(userVo.getPassword(), Constant.Cookie.TOKEN_SALT);
		Cookie cookieToken = new Cookie(Constant.Cookie.NAME_TOKEN, token);
		CookieUtils.setCookies(response, cookieToken, "/", Constant.Cookie.AGE);

		return Constant.Login.SUCCESS;
	}

	/**
	 * 注销
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		CookieUtils.delCookie(response, Constant.Cookie.NAME_USERID, "/");
		CookieUtils.delCookie(response, Constant.Cookie.NAME_USERNAME, "/");
		CookieUtils.delCookie(response, Constant.Cookie.NAME_TOKEN, "/");

		return "/home/login";
	}

	/**
	 * 修改密码
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/password")
	@ResponseBody
	public Boolean password(String input) throws Exception {
		UserVo vo = JsonTranslator.fromString(input, UserVo.class);

		// 非当前登录账号
		if (!StringUtils.IsEquals(vo.getLoginName(), UserContext.getLoginName()))
			return false;

		// 获取用户信息
		UserVo userVo = userService.queryLoginByName(vo);
		if (userVo == null)
			return false;

		// 验证密码
		if (!StringUtils.IsEquals(userVo.getPassword(), vo.getPassword()))
			return false;

		userVo.setPassword(vo.getNewPassword());
		return userService.update(userVo);
	}

}
