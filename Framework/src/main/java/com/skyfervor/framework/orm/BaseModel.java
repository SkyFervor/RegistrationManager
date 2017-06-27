package com.skyfervor.framework.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Model公共字段
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseModel implements Serializable {
	
	/**
	 * 逻辑删除标识符
	 */
	protected Byte enumDataEntityStatus;
	
	/**
	 * 创建者
	 */
	protected String createOperator;
	
	/**
	 * 创建者ID
	 */
	protected Long createOperatorId;
	
	/**
	 * 创建时间
	 */
	protected Date createTime;
	
	/**
	 * 最后更新者
	 */
	protected String lastUpdateOperator;
	
	/**
	 * 最后更新者ID
	 */
	protected Long lastUpdateOperatorId;
	
	/**
	 * 最后更新时间
	 */
	protected Date lastUpdateTime;

	/**
	 * @return the enumDataEntityStatus
	 */
	@Column(name = "EnumDataEntityStatus", length = 1)
	public Byte getEnumDataEntityStatus() {
		return enumDataEntityStatus;
	}

	/**
	 * @param enumDataEntityStatus the enumDataEntityStatus to set
	 */
	public void setEnumDataEntityStatus(Byte enumDataEntityStatus) {
		this.enumDataEntityStatus = enumDataEntityStatus;
	}

	/**
	 * @return the createOperator
	 */
	@Column(name = "CreateOperator")
	public String getCreateOperator() {
		return createOperator;
	}

	/**
	 * @param createOperator the createOperator to set
	 */
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	/**
	 * @return the createOperatorId
	 */
	@Column(name = "CreateOperatorId")
	public Long getCreateOperatorId() {
		return createOperatorId;
	}

	/**
	 * @param createOperatorId the createOperatorId to set
	 */
	public void setCreateOperatorId(Long createOperatorId) {
		this.createOperatorId = createOperatorId;
	}

	/**
	 * @return the createTime
	 */
	@Column(name = "CreateTime")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastUpdateOperator
	 */
	@Column(name = "LastUpdateOperator")
	public String getLastUpdateOperator() {
		return lastUpdateOperator;
	}

	/**
	 * @param lastUpdateOperator the lastUpdateOperator to set
	 */
	public void setLastUpdateOperator(String lastUpdateOperator) {
		this.lastUpdateOperator = lastUpdateOperator;
	}

	/**
	 * @return the lastUpdateOperatorId
	 */
	@Column(name = "LastUpdateOperatorId")
	public Long getLastUpdateOperatorId() {
		return lastUpdateOperatorId;
	}

	/**
	 * @param lastUpdateOperatorId the lastUpdateOperatorId to set
	 */
	public void setLastUpdateOperatorId(Long lastUpdateOperatorId) {
		this.lastUpdateOperatorId = lastUpdateOperatorId;
	}

	/**
	 * @return the lastUpdateTime
	 */
	@Column(name = "LastUpdateTime")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
