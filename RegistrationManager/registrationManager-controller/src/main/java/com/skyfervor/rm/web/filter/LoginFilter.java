package com.skyfervor.rm.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.context.SpringContext;
import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.user.PermissionService;
import com.skyfervor.framework.user.PermissionVo;
import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.user.UserVo;
import com.skyfervor.framework.utility.CookieUtils;
import com.skyfervor.framework.utility.StringUtils;
import com.skyfervor.framework.utility.TokenUtils;
import com.skyfervor.rm.vo.RmConstant;

public class LoginFilter implements Filter {
	private static final String[] IGNORE_URI = { "/providercrm", "/login/authimage", "/login/login",
			RmConstant.URL_LOGIN };

	private static UserService userService = SpringContext.getBean(UserService.class);

	private static PermissionService permissionService = SpringContext
			.getBean(PermissionService.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String url = httpRequest.getServletPath();
		if (isIgnoreUri(url)) {
			chain.doFilter(request, response);
			return;
		}

		// 登录验证
		if (!checkLogin(httpRequest, httpResponse)) {
			failed(httpRequest, httpResponse);
			return;
		}

		// 权限验证
		if (!checkPermission(url)) {
			failed(httpRequest, httpResponse);
			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * 判断忽略路径
	 * 
	 * @param url
	 * @return
	 */
	private boolean isIgnoreUri(String url) {
		String lowUri = url.toLowerCase();

		for (String ignore : IGNORE_URI)
			if (lowUri.startsWith(ignore))
				return true;
		return false;
	}

	/**
	 * 登录验证
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) {
		// 读取cookie，获取对应数据
		Cookie cookieUserId = CookieUtils.getCookie(request, Constant.Cookie.NAME_USERID);
		Cookie cookieToken = CookieUtils.getCookie(request, Constant.Cookie.NAME_TOKEN);

		if (cookieUserId == null || StringUtils.isEmpty(cookieUserId.getValue()))
			return false;
		if (cookieToken == null || StringUtils.isEmpty(cookieToken.getValue()))
			return false;

		try {
			// 获取用户信息
			long userId = Long.parseLong(cookieUserId.getValue());
			UserVo userVo = userService.queryLoginById(userId);

			// 验证Token
			String token = TokenUtils.getToken(userVo.getPassword(), userVo.getCreateTime().toString());
			if (!StringUtils.IsEquals(token, cookieToken.getValue()))
				return false;

			// 初始化UserContext
			UserContext.initLoginInfo(userId, userVo.getLoginName(), userVo.getRoleId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 权限验证
	 * 
	 * @param url
	 * @return
	 */
	private boolean checkPermission(String url) {
		long roleId = UserContext.getRoleID();
		if (roleId == -1)
			return false;

		try {
			PermissionVo permissionVo = new PermissionVo();
			permissionVo.setRoleId(roleId);
			permissionVo.setUrl(url);

			return permissionService.contains(permissionVo);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 验证失败
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void failed(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		UserContext.clearLoginInfo();
		CookieUtils.delCookie(response, Constant.Cookie.NAME_USERID, "/");
		CookieUtils.delCookie(response, Constant.Cookie.NAME_USERNAME, "/");
		CookieUtils.delCookie(response, Constant.Cookie.NAME_TOKEN, "/");
		response.sendRedirect(request.getContextPath() + RmConstant.URL_LOGIN);
	}
}
