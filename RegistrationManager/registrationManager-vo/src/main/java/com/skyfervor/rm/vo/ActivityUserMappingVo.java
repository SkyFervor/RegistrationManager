package com.skyfervor.rm.vo;

import java.io.Serializable;
import java.util.Date;

import com.skyfervor.framework.vobase.BaseVo;

public class ActivityUserMappingVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
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
	 * 报名者姓名
	 */
	private String userName;
	/**
	 * 报名说明
	 */
	private String description;
	/**
	 * 报名状态
	 */
	private Byte applied;
	/**
	 * 报名时间
	 */
	private Date applyTime;

	public Long getActivityUserMappingId() {
		return activityUserMappingId;
	}

	public void setActivityUserMappingId(Long activityUserMappingId) {
		this.activityUserMappingId = activityUserMappingId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Byte getApplied() {
		return applied;
	}

	public void setApplied(Byte applied) {
		this.applied = applied;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Override
	public String toString() {
		return "ActivityUserMappingVo [activityUserMappingId=" + activityUserMappingId
				+ ", activityId=" + activityId + ", userId=" + userId + ", userName=" + userName
				+ ", description=" + description + ", applied=" + applied + ", applyTime="
				+ applyTime + "]";
	}

}