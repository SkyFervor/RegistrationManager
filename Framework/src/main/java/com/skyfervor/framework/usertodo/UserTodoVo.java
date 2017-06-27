package com.skyfervor.framework.usertodo;

import java.io.Serializable;
import java.util.Date;

import com.skyfervor.framework.vobase.BaseVo;

public class UserTodoVo extends BaseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long userTodoId;
	/**
	 * 待办内容
	 */
	private String todoContent;
	/**
	 * 事项状态 0未读，1已读
	 */
	private Byte todoStatus;
	/**
	 * 事项状态 用于显示
	 */
	private String todoStatusName;
	/**
	 * 所属用户ID
	 */
	private Long bdUserId;
	/**
	 * 所属用户名
	 */
	private String bdUserName;
	/**
	 * 被关联的数据ID，用于执行时的参数
	 */
	private Long associatedDataId;
	/**
	 * 任务被置为已读的时间
	 */
	private Date readTime;

	public Long getUserTodoId() {
		return userTodoId;
	}

	public void setUserTodoId(Long userTodoId) {
		this.userTodoId = userTodoId;
	}

	public String getTodoContent() {
		return todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	public Byte getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(Byte todoStatus) {
		this.todoStatus = todoStatus;
	}

	public String getTodoStatusName() {
		return todoStatusName;
	}

	public void setTodoStatusName(String todoStatusName) {
		this.todoStatusName = todoStatusName;
	}

	public Long getBdUserId() {
		return bdUserId;
	}

	public void setBdUserId(Long bdUserId) {
		this.bdUserId = bdUserId;
	}

	public String getBdUserName() {
		return bdUserName;
	}

	public void setBdUserName(String bdUserName) {
		this.bdUserName = bdUserName;
	}

	public Long getAssociatedDataId() {
		return associatedDataId;
	}

	public void setAssociatedDataId(Long associatedDataId) {
		this.associatedDataId = associatedDataId;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	@Override
	public String toString() {
		return "UserTodoVo [userTodoId=" + userTodoId + ", todoContent=" + todoContent
				+ ", todoStatus=" + todoStatus + ", bdUserId=" + bdUserId + ", bdUserName="
				+ bdUserName + ", associatedDataId=" + associatedDataId + ", readTime=" + readTime
				+ "]";
	}
}
