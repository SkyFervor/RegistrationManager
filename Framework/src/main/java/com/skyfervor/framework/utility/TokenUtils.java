package com.skyfervor.framework.utility;

import static com.skyfervor.framework.utility.MD5Utils.MD5;

public class TokenUtils {

	public static String getPassword(String password, String auth) {
		return MD5(password + auth);
	}

	public static String getToken(String password, String salt) {
		return MD5(password + salt);
	}
}
