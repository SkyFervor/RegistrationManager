package com.skyfervor.rm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skyfervor.framework.orm.BaseModel;

@Entity
@Table(name = "DictionaryType")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class DictionaryTypeMd extends BaseModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
	 */
	private Long dictionaryTypeId;
	/**
	 * 类型名
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 扩展信息
	 */
	private String enumExtendInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DictionaryTypeId")
	public Long getDictionaryTypeId() {
		return dictionaryTypeId;
	}

	public void setDictionaryTypeId(Long dictionaryTypeId) {
		this.dictionaryTypeId = dictionaryTypeId;
	}

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "EnumExtendInfo")
	public String getEnumExtendInfo() {
		return enumExtendInfo;
	}

	public void setEnumExtendInfo(String enumExtendInfo) {
		this.enumExtendInfo = enumExtendInfo;
	}

}
