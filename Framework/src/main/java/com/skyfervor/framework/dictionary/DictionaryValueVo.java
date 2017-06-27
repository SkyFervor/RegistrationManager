package com.skyfervor.framework.dictionary;

import java.io.Serializable;

import com.skyfervor.framework.vobase.BaseVo;

public class DictionaryValueVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
	 */
	private Long dictionaryValueId;
	/**
	 * 字典类型ID
	 */
	private Long dictionaryTypeId;
	/**
	 * 字典类型名
	 */
	private String dictionaryTypeName;
	/**
	 * 字典类型描述
	 */
	private String dictionaryTypeDesc;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 值
	 */
	private Integer value;
	/**
	 * 排序
	 */
	private Integer indexId;
	/**
	 * 是否显示
	 */
	private Byte display;
	/**
	 * 是否显示 用于显示
	 */
	private String displayName;
	/**
	 * 修改权限标识 0不可修改，1可修改
	 */
	private Byte modify;

	public Long getDictionaryValueId() {
		return dictionaryValueId;
	}

	public void setDictionaryValueId(Long dictionaryValueId) {
		this.dictionaryValueId = dictionaryValueId;
	}

	public Long getDictionaryTypeId() {
		return dictionaryTypeId;
	}

	public void setDictionaryTypeId(Long dictionaryTypeId) {
		this.dictionaryTypeId = dictionaryTypeId;
	}

	public String getDictionaryTypeName() {
		return dictionaryTypeName;
	}

	public void setDictionaryTypeName(String dictionaryTypeName) {
		this.dictionaryTypeName = dictionaryTypeName;
	}

	public String getDictionaryTypeDesc() {
		return dictionaryTypeDesc;
	}

	public void setDictionaryTypeDesc(String dictionaryTypeDesc) {
		this.dictionaryTypeDesc = dictionaryTypeDesc;
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public Byte getDisplay() {
		return display;
	}

	public void setDisplay(Byte display) {
		this.display = display;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Byte getModify() {
		return modify;
	}

	public void setModify(Byte modify) {
		this.modify = modify;
	}

	@Override
	public String toString() {
		return "DictionaryValueVo [dictionaryValueId=" + dictionaryValueId + ", dictionaryTypeId="
				+ dictionaryTypeId + ", dictionaryTypeName=" + dictionaryTypeName
				+ ", dictionaryTypeDesc=" + dictionaryTypeDesc + ", name=" + name + ", description="
				+ description + ", value=" + value + ", indexId=" + indexId + ", display=" + display
				+ ", displayName=" + displayName + ", modify=" + modify + "]";
	}
}
