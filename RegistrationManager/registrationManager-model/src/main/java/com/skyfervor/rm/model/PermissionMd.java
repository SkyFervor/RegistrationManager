package com.skyfervor.rm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skyfervor.framework.orm.BaseModel;

@Entity
@Table(name = "Permission")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class PermissionMd extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PermissionId")
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	@Column(name = "RoleId")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "Menu")
	public Byte getMenu() {
		return menu;
	}

	public void setMenu(Byte menu) {
		this.menu = menu;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
