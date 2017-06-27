package com.skyfervor.framework.vobase;

public interface IUserInfo {
	public String getCurrentUserLoginToken();
	public void setCurrentUserLoginToken(String currentUserLoginToken);
	public Long getCurrentUserId();
	public void setCurrentUserId(Long currentUserId);
	public String getCurrentUserName();
	public void setCurrentUserName(String currentUserName);
}
