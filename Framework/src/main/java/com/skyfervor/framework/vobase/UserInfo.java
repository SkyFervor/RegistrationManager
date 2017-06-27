package com.skyfervor.framework.vobase;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfo implements Serializable, IUserInfo {
	
	/**
	 * 当前用户ID
	 */
	protected Long currentUserId;
	
	/**
	 * 当前用户名称
	 */
	protected String currentUserName;
	
	/**
	 * 当前用户名称
	 */
	protected String currentUserLoginToken;
	
	
	public Long getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	
	public String getCurrentUserLoginToken() {
		return currentUserLoginToken;
	}

	public void setCurrentUserLoginToken(String currentUserLoginToken) {
		this.currentUserLoginToken = currentUserLoginToken;
	}
}
