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
@Table(name = "UserTodo")
@org.hibernate.annotations.Entity(dynamicInsert = true)
public class UserTodoMd extends BaseModel {

	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
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
	 * 所属用户ID
	 */
	private Long bdUserId;
	/**
	 * 所属用户名称
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserTodoId")
	public Long getUserTodoId() {
		return userTodoId;
	}

	public void setUserTodoId(Long userTodoId) {
		this.userTodoId = userTodoId;
	}

	@Column(name = "TodoContent")
	public String getTodoContent() {
		return todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	@Column(name = "TodoStatus")
	public Byte getTodoStatus() {
		return todoStatus;
	}

	public void setTodoStatus(Byte todoStatus) {
		this.todoStatus = todoStatus;
	}

	@Column(name = "BdUserId")
	public Long getBdUserId() {
		return bdUserId;
	}

	public void setBdUserId(Long bdUserId) {
		this.bdUserId = bdUserId;
	}

	@Column(name = "BdUserName")
	public String getBdUserName() {
		return bdUserName;
	}

	public void setBdUserName(String bdUserName) {
		this.bdUserName = bdUserName;
	}

	@Column(name = "AssociatedDataId")
	public Long getAssociatedDataId() {
		return associatedDataId;
	}

	public void setAssociatedDataId(Long associatedDataId) {
		this.associatedDataId = associatedDataId;
	}

	@Column(name = "ReadTime")
	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}
