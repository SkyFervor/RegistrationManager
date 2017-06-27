package com.skyfervor.rm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.skyfervor.framework.vobase.BaseVo;

public class ActivityVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long activityId;
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 活动类型
	 */
	private Integer type;
	/**
	 * 活动类型 用于显示
	 */
	private String typeName;
	/**
	 * 发布人ID
	 */
	private Long publisherId;
	/**
	 * 发布人姓名
	 */
	private String publisherName;
	/**
	 * 人员限制 是否全选
	 */
	private Byte isAllSelect;
	/**
	 * 最小人数限制
	 */
	private Integer minimum;
	/**
	 * 最大人数限制
	 */
	private Integer maximum;
	/**
	 * 活动说明
	 */
	private String description;
	/**
	 * 报名开始时间
	 */
	private Date beginTime;
	/**
	 * 报名结束时间
	 */
	private Date endTime;
	/**
	 * 活动时间
	 */
	private Date activityTime;
	/**
	 * 活动状态
	 */
	private Integer status;
	/**
	 * 活动状态 用于显示
	 */
	private String statusName;
	/**
	 * 报名人ID
	 */
	private List<Long> activityPersonId;
	/**
	 * 修改人ID
	 */
	private Long modifyUserId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public Byte getIsAllSelect() {
		return isAllSelect;
	}

	public void setIsAllSelect(Byte isAllSelect) {
		this.isAllSelect = isAllSelect;
	}

	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<Long> getActivityPersonId() {
		return activityPersonId;
	}

	public void setActivityPersonId(List<Long> activityPersonId) {
		this.activityPersonId = activityPersonId;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Override
	public String toString() {
		return "ActivityVo [activityId=" + activityId + ", name=" + name + ", type=" + type
				+ ", typeName=" + typeName + ", publisherId=" + publisherId + ", publisherName="
				+ publisherName + ", isAllSelect=" + isAllSelect + ", minimum=" + minimum
				+ ", maximum=" + maximum + ", description=" + description + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", activityTime=" + activityTime
				+ ", status=" + status + ", statusName=" + statusName + ", activityPersonId="
				+ activityPersonId + ", modifyUserId=" + modifyUserId + "]";
	}

}