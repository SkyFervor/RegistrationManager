package com.skyfervor.rm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skyfervor.framework.orm.BaseModel;

@Entity
@Table(name = "DictionaryValue")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class DictionaryValueMd extends BaseModel {

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
	 * 排序ID
	 */
	private Integer indexId;
	/**
	 * 是否显示 0不显示 1显示
	 */
	private Byte display;
	/**
	 * 修改权限标识 0不可修改，1可修改
	 */
	private Byte modify;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DictionaryValueId")
	public Long getDictionaryValueId() {
		return dictionaryValueId;
	}

	public void setDictionaryValueId(Long dictionaryValueId) {
		this.dictionaryValueId = dictionaryValueId;
	}

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

	@Column(name = "Value")
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column(name = "IndexId")
	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	@Column(name = "Display")
	public Byte getDisplay() {
		return display;
	}

	public void setDisplay(Byte display) {
		this.display = display;
	}

	@Column(name = "Modify")
	public Byte getModify() {
		return modify;
	}

	public void setModify(Byte modify) {
		this.modify = modify;
	}

}
