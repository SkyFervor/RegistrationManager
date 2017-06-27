package com.skyfervor.framework.user;

import java.io.Serializable;

import com.skyfervor.framework.vobase.BaseVo;

public class RoleVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
	 */
	private Long roleId;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色描述
	 */
	private String description;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RoleVo [roleId=" + roleId + ", name=" + name + ", description=" + description + "]";
	}

}
