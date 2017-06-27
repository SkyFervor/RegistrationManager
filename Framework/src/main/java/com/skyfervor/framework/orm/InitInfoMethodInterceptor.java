package com.skyfervor.framework.orm;

import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.vobase.IUserInfo;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class InitInfoMethodInterceptor implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(InitInfoMethodInterceptor.class);
	
	private static ThreadLocal<Stack<String>> stackThreadLocal = new ThreadLocal<Stack<String>>();
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		if (stackThreadLocal.get() == null) {
			stackThreadLocal.set(new Stack<String>());
		}
		
		if (stackThreadLocal.get().size() == 0 && !UserContext.isWeb()) {
			
			Object[] objects = invocation.getArguments();
			
			UserContext.clearLoginInfo();
			
			boolean initSign = false;
		
			for (Object object : objects) {
				if (object instanceof IUserInfo) {
					IUserInfo userInfo = (IUserInfo) object;
					if (userInfo.getCurrentUserLoginToken() != null) {
						try {
//							UserContext.initLoginInfo(userInfo.getCurrentUserLoginToken());
							UserContext.initLoginInfo(userInfo.getCurrentUserId(),null,-1);
							logger.debug("invoke -> UserContext.initLoginInfo -> token:{}", userInfo.getCurrentUserLoginToken());
							//return doInvoke(invocation);
							initSign = true;
						} catch (Exception e) {
							logger.error("invoke -> UserContext.initLoginInfo -> token:{} -> Exception:{}", userInfo.getCurrentUserLoginToken(), e);
							throw e;
						}
					}
				} else if (object instanceof IUserInfo[]) {
					for(IUserInfo userInfo : (IUserInfo[]) object) {
						if (userInfo.getCurrentUserLoginToken() != null) {
							try {
//								UserContext.initLoginInfo(userInfo.getCurrentUserLoginToken());
								UserContext.initLoginInfo(userInfo.getCurrentUserId(),null,-1);
								logger.debug("invoke -> UserContext.initLoginInfo -> token:{}", userInfo.getCurrentUserLoginToken());
								//return doInvoke(invocation);
								initSign = true;
								break;
							} catch (Exception e) {
								logger.error("invoke -> UserContext.initLoginInfo -> token:{} -> Exception:{}", userInfo.getCurrentUserLoginToken(), e);
								throw e;
							}
						}
					}
				}
				
				if (initSign == true) {
					break;
				}
			}
			
			if (initSign == false) {
				if (invocation.getMethod().getName().startsWith("insert")
						|| invocation.getMethod().getName().startsWith("update")) {					
					logger.info("invoke -> UserContext.initLoginInfo -> not have IUserInfo type param -> method:{}", invocation.getMethod().getDeclaringClass().getSimpleName() + "." + invocation.getMethod().getName());
				}
			}
		}
		
		return doInvoke(invocation);
	}
	
	private Object doInvoke(MethodInvocation invocation) throws Throwable {
		try {
			stackThreadLocal.get().push(invocation.getMethod().getName());
			return invocation.proceed();
		} finally {
			stackThreadLocal.get().pop();
		}
	}
}
