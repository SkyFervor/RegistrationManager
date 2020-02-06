package com.skyfervor.framework.constant;

public class Constant {

	public static final long DefaultTime = -2209017600000L; // 默认时间1900-01-01 00:00:00

	public class DataBase {
		// EnumDataEntityStatus 逻辑删除状态
		public static final byte Normal = 0; // 正常
		public static final byte Deleted = 1; // 逻辑删除
		public static final byte Locked = 2; // 锁定
	}

	public static class Cookie {
		public static final String NAME_USERID = "userId"; // 用户ID对应的cookie名
		public static final String NAME_USERNAME = "userName"; // 用户UserName对应的cookie名
		public static final String NAME_TOKEN = "token"; // 登录Token对应的cookie名
	}

	public class Login {
		public static final String NAME_AUTH = "auth"; // 验证码对应的session名

		// 登录结果
		public static final byte SUCCESS = 1; // 成功
		public static final byte USERNAME_INCORRECT = 2; // 账号错误
		public static final byte PASSWORD_INCORRECT = 3; // 密码错误
		public static final byte AUTHCODE_INCORRECT = 4; // 验证码错误
	}

	public class Permission {
		// Menu 菜单权限
		public static final byte MENU_FALSE = 0; // 非菜单权限
		public static final byte MENU_TRUE = 1; // 菜单权限
	}

	public class Dictionary {
		public static final String USERTODO_STATUS = "UserTodoStatus";
		public static final String USER_STATUS = "UserStatus";

		// Display 值显示状态
		public static final byte DISPLAY_FALSE = 0;
		public static final String DISPLAY_FALSE_DESC = "不显示";
		public static final byte DISPLAY_TRUE = 1;
		public static final String DISPLAY_TRUE_DESC = "显示";

		// Modify 修改权限
		public static final byte MODIFY_FALSE = 0; // 可修改
		public static final byte MODIFY_TRUE = 1; // 不可修改
	}

	public class UserTodo {
		// Status 提醒事项状态
		public static final byte UNREAD = 1; // 未读
		public static final byte READED = 2; // 已读
	}

	public class User {
		// Status 账号状态
		public static final byte NORMAL = 1; // 正常
		public static final byte DISABLED = 2; // 禁用

		// UserAdd Power 判断添加权限
		public static final byte ADD_SUCCESS = 1; // 通过
		public static final byte ADD_FAILURE = 2; // 登录名重复
	}
}
