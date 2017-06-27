package com.skyfervor.rm.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ActivityReportVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
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
	/**
	 * 筛选条件 ID列表
	 */
	private List<Long> idList;
	/**
	 * 筛选条件 开始时间
	 */
	private Date beginTime;
	/**
	 * 筛选条件 结束时间
	 */
	private Date endTime;
	/**
	 * 筛选条件 是否报名
	 */
	private Byte applied;
	/**
	 * 报表数据
	 */
	private Integer count;
	/**
	 * 报表显示的维度
	 */
	private Byte method;

	public Long getActivityReportId() {
		return activityReportId;
	}

	public void setActivityReportId(Long activityReportId) {
		this.activityReportId = activityReportId;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
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

	public Byte getApplied() {
		return applied;
	}

	public void setApplied(Byte applied) {
		this.applied = applied;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Byte getMethod() {
		return method;
	}

	public void setMethod(Byte method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "ActivityReportVo [activityReportId=" + activityReportId + ", activityId="
				+ activityId + ", activityName=" + activityName + ", activityTime=" + activityTime
				+ ", userId=" + userId + ", userName=" + userName + ", idList=" + idList
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + ", count=" + count
				+ ", method=" + method + "]";
	}

}
