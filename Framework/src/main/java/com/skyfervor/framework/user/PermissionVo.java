package com.skyfervor.framework.user;

import java.io.Serializable;

import com.skyfervor.framework.vobase.BaseVo;

public class PermissionVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long permissionId;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 菜单权限标识 0非菜单权限，1菜单权限
	 */
	private Byte menu;
	/**
	 * URL路径
	 */
	private String url;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Byte getMenu() {
		return menu;
	}

	public void setMenu(Byte menu) {
		this.menu = menu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PermissionVo [permissionId=" + permissionId + ", roleId=" + roleId + ", menu="
				+ menu + ", url=" + url + "]";
	}

}
