package com.skyfervor.framework.user;

import java.io.Serializable;

import com.skyfervor.framework.vobase.BaseVo;

public class UserVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long userId;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 登录用户名
	 */
	private String loginName;
	/**
	 * 状态
	 */
	private Byte status;
	/**
	 * 状态 用于显示
	 */
	private String statusName;
	/**
	 * 验证码
	 */
	private String auth;
	/**
	 * 新密码
	 */
	private String newPassword;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UserVo [userId=" + userId + ", roleId=" + roleId + ", roleName=" + roleName
				+ ", userName=" + userName + ", password=" + password + ", loginName=" + loginName
				+ ", status=" + status + ", statusName=" + statusName + ", auth=" + auth
				+ ", newPassword=" + newPassword + "]";
	}

}
