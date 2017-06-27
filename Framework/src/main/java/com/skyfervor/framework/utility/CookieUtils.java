package com.skyfervor.framework.utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public static void setCookies(HttpServletResponse response, Cookie cookie, String path,
			Integer maxAge) {
		if (path != null)
			cookie.setPath(path);
		if (maxAge != null)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void delCookie(HttpServletResponse response, String name, String path) {
		Cookie cookie = new Cookie(name, null);
		setCookies(response, cookie, path, 0);
	}

}
