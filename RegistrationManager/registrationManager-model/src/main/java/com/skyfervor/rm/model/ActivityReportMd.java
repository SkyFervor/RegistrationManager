package com.skyfervor.rm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ActivityReport")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class ActivityReportMd {

	/**
	 * 自增主键
	 */
	private Long activityReportId;
	/**
	 * 活动ID
	 */
	private Long activityId;
	/**
	 * 活动名称
	 */
	private String activityName;
	/**
	 * 活动时间
	 */
	private Date activityTime;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户姓名
	 */
	private String userName;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ActivityReportId")
	public Long getActivityReportId() {
		return activityReportId;
	}

	public void setActivityReportId(Long activityReportId) {
		this.activityReportId = activityReportId;
	}

	@Column(name = "ActivityId")
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Column(name = "ActivityName")
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	@Column(name = "ActivityTime")
	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	@Column(name = "UserId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "UserName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
