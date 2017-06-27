package com.skyfervor.framework.dictionary;

import java.io.Serializable;

import com.skyfervor.framework.vobase.BaseVo;

public class DictionaryTypeVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
	 */
	private Long dictionaryTypeId;
	/**
	 * 类型名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 扩展信息，放置类名，根据类名查找所有内容
	 */
	private String enumExtendInfo;

	public Long getDictionaryTypeId() {
		return dictionaryTypeId;
	}

	public void setDictionaryTypeId(Long dictionaryTypeId) {
		this.dictionaryTypeId = dictionaryTypeId;
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

	public String getEnumExtendInfo() {
		return enumExtendInfo;
	}

	public void setEnumExtendInfo(String enumExtendInfo) {
		this.enumExtendInfo = enumExtendInfo;
	}

	@Override
	public String toString() {
		return "DictionaryTypeVo [dictionaryTypeId=" + dictionaryTypeId + ", name=" + name
				+ ", description=" + description + ", enumExtendInfo=" + enumExtendInfo + "]";
	}
}
