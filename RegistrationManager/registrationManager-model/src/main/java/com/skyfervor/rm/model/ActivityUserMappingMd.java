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
@Table(name = "ActivityUserMapping")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class ActivityUserMappingMd extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	private Long activityUserMappingId;
	/**
	 * 活动ID
	 */
	private Long activityId;
	/**
	 * 报名者ID
	 */
	private Long userId;
	/**
	 * 报名说明
	 */
	private String description;
	/**
	 * 是否申请状态
	 */
	private Byte applied;
	/**
	 * 报名时间
	 */
	private Date applyTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivityUserMappingId")
	public Long getActivityUserMappingId() {
		return activityUserMappingId;
	}

	public void setActivityUserMappingId(Long activityUserMappingId) {
		this.activityUserMappingId = activityUserMappingId;
	}

	@Column(name = "ActivityId")
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Column(name = "UserId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Applied")
	public Byte getApplied() {
		return applied;
	}

	public void setApplied(Byte applied) {
		this.applied = applied;
	}

	@Column(name = "ApplyTime")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

}