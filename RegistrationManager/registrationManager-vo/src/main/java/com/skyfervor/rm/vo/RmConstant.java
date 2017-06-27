package com.skyfervor.rm.vo;

public class RmConstant {
	public static final String URL_LOGIN = "/view/home/login.jsp";

	public class Dictionary {
		public static final String ACTIVITY_TYPE = "ActivityType"; // 活动类型
		public static final String ACTIVITY_STATUS = "ActivityStatus"; // 活动状态

		// Activity Status 活动状态
		public static final int STATUS_PENDING = 1; // 未开始
		public static final int STATUS_REGISTERING = 2; // 报名中
		public static final int STATUS_OVERDUE = 3; // 已过期
		public static final int STATUS_INVALID = 4; // 失效
	}

	public class Activity {
		// ActivityUserMapping Applied 报名状态
		public static final byte APPLIED_FALSE = 0; // 未报名
		public static final byte APPLIED_TRUE = 1; // 报名

		// Activity IsAllSelect 人员限制
		public static final byte ALLSELECT_FALSE = 0; // 不全选
		public static final byte ALLSELECT_TRUE = 1; // 全选

		// ActivityEnroll Power 判定报名权限
		public static final byte ENROLL_SUCCESS = 1; // 通过
		public static final byte ENROLL_FULL = 2; // 报名人数已满
		public static final byte ENROLL_NOT_IN = 3; // 不在活动人员列表中

		// ActivityModify Power 判定修改权限
		public static final byte MODIFY_SUCCESS = 1; // 通过
		public static final byte MODIFY_NOT_PUBLISHER = 2; // 不是活动发布人
		public static final byte MODIFY_STARTED = 3; // 活动已开始报名
		public static final byte MODIFY_FAILED = 4;
	}

	public class Report {
		// ActivityReport Type 报表类型
		public static final byte TYPE_PERSON = 1; // 按人统计
		public static final byte TYPE_ACTIVITY = 2; // 按活动统计
	}
}
