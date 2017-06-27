package com.skyfervor.framework.vobase;

import java.io.Serializable;
import java.util.Date;

import com.skyfervor.framework.orm.IDataEntity;

/**
 * ViewObject 公共字段
 */
@SuppressWarnings("serial")
public abstract class BaseVo extends UserInfo implements Serializable, IDataEntity {

	/**
	 * 逻辑删除标识符
	 */
	protected Byte enumDataEntityStatus;

	/**
	 * 创建人
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
	 * 最后更新人
	 */
	protected String lastUpdateOperator;

	/**
	 * 最后更新人ID
	 */
	protected Long lastUpdateOperatorId;

	/**
	 * 最后更新时间
	 */
	protected Date lastUpdateTime;

	/**
	 * @return the enumDataEntityStatus
	 */
	public Byte getEnumDataEntityStatus() {
		return enumDataEntityStatus;
	}

	/**
	 * @param enumDataEntityStatus
	 *            the enumDataEntityStatus to set
	 */
	public void setEnumDataEntityStatus(Byte enumDataEntityStatus) {
		this.enumDataEntityStatus = enumDataEntityStatus;
	}

	/**
	 * @return the createOperator
	 */
	public String getCreateOperator() {
		return createOperator;
	}

	/**
	 * @param createOperator
	 *            the createOperator to set 如非必要，请勿使用此方法，操作数据库时以Md统一set保存
	 */
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	/**
	 * @return the createOperatorId
	 */
	public Long getCreateOperatorId() {
		return createOperatorId;
	}

	/**
	 * @param createOperatorId
	 *            the createOperatorId to set 如非必要，请勿使用此方法，操作数据库时以Md统一set保存
	 */
	public void setCreateOperatorId(Long createOperatorId) {
		this.createOperatorId = createOperatorId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set 如非必要，请勿使用此方法，操作数据库时以Md统一set保存
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastUpdateOperator
	 */
	public String getLastUpdateOperator() {
		return lastUpdateOperator;
	}

	/**
	 * @param lastUpdateOperator
	 *            the lastUpdateOperator to set 如非必要，请勿使用此方法，操作数据库时以Md统一set保存
	 */
	public void setLastUpdateOperator(String lastUpdateOperator) {
		this.lastUpdateOperator = lastUpdateOperator;
	}

	/**
	 * @return the lastUpdateOperatorId
	 */
	public Long getLastUpdateOperatorId() {
		return lastUpdateOperatorId;
	}

	/**
	 * @param lastUpdateOperatorId
	 *            the lastUpdateOperatorId to set 如非必要，请勿使用此方法，操作数据库时以Md统一set保存
	 */
	public void setLastUpdateOperatorId(Long lastUpdateOperatorId) {
		this.lastUpdateOperatorId = lastUpdateOperatorId;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            the lastUpdateTime to set 如非必要，请勿使用此方法，操作数据库时以Md统一set保存
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createOperator == null) ? 0 : createOperator.hashCode());
		result = prime * result + (int) (createOperatorId ^ (createOperatorId >>> 32));
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + enumDataEntityStatus;
		result = prime * result + ((lastUpdateOperator == null) ? 0 : lastUpdateOperator.hashCode());
		result = prime * result + (int) (lastUpdateOperatorId ^ (lastUpdateOperatorId >>> 32));
		result = prime * result + ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseVo other = (BaseVo) obj;
		if (createOperator == null) {
			if (other.createOperator != null)
				return false;
		} else if (!createOperator.equals(other.createOperator))
			return false;
		if (createOperatorId != null ? !createOperatorId.equals(other.createOperatorId)
				: other.createOperatorId != null)
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (enumDataEntityStatus != null ? !enumDataEntityStatus.equals(other.enumDataEntityStatus)
				: other.enumDataEntityStatus != null)
			return false;
		if (lastUpdateOperator == null) {
			if (other.lastUpdateOperator != null)
				return false;
		} else if (!lastUpdateOperator.equals(other.lastUpdateOperator))
			return false;
		if (lastUpdateOperatorId != null ? !lastUpdateOperatorId.equals(other.lastUpdateOperatorId)
				: other.lastUpdateOperatorId != null)
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"BaseVo [enumDataEntityStatus=%s, createOperator=%s, createOperatorId=%s, createTime=%s, lastUpdateOperator=%s, lastUpdateOperatorId=%s, lastUpdateTime=%s]",
				enumDataEntityStatus, createOperator, createOperatorId, createTime, lastUpdateOperator,
				lastUpdateOperatorId, lastUpdateTime);
	}
}
