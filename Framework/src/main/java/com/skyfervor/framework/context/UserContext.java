package com.skyfervor.framework.context;

public class UserContext {
	private static ThreadLocal<Long> userId = new ThreadLocal<>();
	private static ThreadLocal<String> loginName = new ThreadLocal<>();
	private static ThreadLocal<Long> roleId = new ThreadLocal<>();
	private static boolean isWeb = false;

	/**
	 * 是否前台进程
	 * 
	 * @return
	 */
	public static boolean isWeb() {
		return isWeb;
	}

	/**
	 * 设置前台进程
	 * 
	 * @param isWeb
	 */
	public static void setWeb(boolean isWeb) {
		UserContext.isWeb = isWeb;
	}

	public static void initLoginInfo(long id, String name, long role) {
		userId.set(id);
		loginName.set(name);
		roleId.set(role);
	}
	//	/**
	//	 * 初始化默认的用户登陆信息。导入接口或批处理专用。
	//	 */
	//	public static void initLoginInfoForBatch() {
	//		LoginInfo tloginInfo = new LoginInfo();
	//		tloginInfo.setLoginName("system");
	//		tloginInfo.setUserID(-99);
	//		tloginInfo.setBusinessPermissionList(new ArrayList<Integer>());
	//
	//		loginInfo.set(tloginInfo);
	//	}
	//
	//	/**
	//	 * 根据loginToken，初始化用户登陆信息。
	//	 * @param loginToken
	//	 * @throws CRMPermissionException
	//	 */
	//	public static void initLoginInfo(String loginToken) throws CRMPermissionException {
	//		if (StringUtils.isBlank(loginToken)) return;
	//
	//		if (loginInfo.get() == null || !loginInfo.get().getLoginToken().equals(loginToken)){
	//			loginInfo.set(null);
	//
	//			try {
	//				GetLoginInfoRequest getLoginInfoReq = new GetLoginInfoRequest();
	//				getLoginInfoReq.setLoginToken(loginToken);
	//				UserService userService = WebServiceHelper.GetUserService();
	//				if (userService == null) {
	//					logger.error("无法获取UserService服务:" + loginToken);
	//					throw new CRMPermissionException("");
	//				}
	//				GetLoginInfoResponse getLoginInfoRes = userService.GetLoginInfo(getLoginInfoReq);
	//				if (getLoginInfoRes == null || !getLoginInfoRes.isIsSuccess()) {
	//					logger.error("当前Token初始化用户信息失败:" + loginToken);
	//					throw new CRMPermissionException("");
	//				}
	//
	//				LoginInfo tloginInfo = getLoginInfoRes.getLoginInfo();
	//
	//				loginInfo.set(tloginInfo);
	//			} catch (RemoteException e) {
	//				logger.error("当前Token初始化用户信息异常:" + loginToken, e);
	//				throw new CRMPermissionException("[" + loginToken + "]用户登录失败");
	//			}
	//		}
	//	}
	//
	//	public static void initLoginInfo(LoginInfo tloginInfo) {
	//		loginInfo.set(tloginInfo);
	//	}

	public static void clearLoginInfo() {
		userId.set(null);
		loginName.set(null);
		roleId.set(null);
	}

	//	/**
	//	 * 获取当前登陆用户信息
	//	 * @return
	//	 */
	//	public static LoginInfo getLoginInfo() {
	//		return loginInfo.get();
	//	}
	/**
	 * 获取当前登陆用户ID
	 * 
	 * @return
	 */
	public static long getUserID() {
		if (userId.get() != null)
			return userId.get();
		else
			return -1;
	}

	/**
	 * 获取当前登陆用户名
	 * 
	 * @return
	 */
	public static String getLoginName() {
		if (loginName.get() != null)
			return loginName.get();
		else
			return "system";
	}

	/**
	 * 获取当前登录用户的角色ID
	 * 
	 * @return
	 */
	public static long getRoleID() {
		if (roleId.get() != null)
			return roleId.get();
		else
			return -1;
	}

}
