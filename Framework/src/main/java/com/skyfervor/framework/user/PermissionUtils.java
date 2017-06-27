package com.skyfervor.framework.user;

import java.util.HashMap;
import java.util.Map;

import com.skyfervor.framework.context.SpringContext;
import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.constant.Constant;

public class PermissionUtils {
	private static PermissionService permissionService = SpringContext
			.getBean(PermissionService.class);

	/**
	 * 返回人员权限的列表数组
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Boolean> getPermissionList() throws Exception {
		Map<String, Boolean> map = new HashMap<>();

		PermissionVo permissionVo = new PermissionVo();
		permissionVo.setRoleId(UserContext.getRoleID());
		permissionVo.setMenu(Constant.Permission.MENU_TRUE);
		for (PermissionVo vo : permissionService.queryList(permissionVo)) {
			map.put(vo.getUrl(), true);
		}
		return map;

	}
}
