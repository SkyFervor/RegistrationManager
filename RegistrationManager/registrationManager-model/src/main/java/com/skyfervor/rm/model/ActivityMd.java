package com.skyfervor.rm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.skyfervor.framework.orm.BaseModel;

@Entity
@Table(name = "Activity")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class ActivityMd extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
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
	 * 发布人ID
	 */
	private Long publisherId;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivityId")
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Column(name = "Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "Type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "PublisherId")
	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	@Column(name = "IsAllSelect")
	public Byte getIsAllSelect() {
		return isAllSelect;
	}

	public void setIsAllSelect(Byte isAllSelect) {
		this.isAllSelect = isAllSelect;
	}

	@Column(name = "Minimum")
	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	@Column(name = "Maximum")
	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "BeginTime")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Column(name = "EndTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "ActivityTime")
	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	@Column(name = "Status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}