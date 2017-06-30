package com.skyfervor.framework.trace;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skyfervor.framework.utility.JsonTranslator;

public class CatchErrorInterceptor implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(CatchErrorInterceptor.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {		
		return doInvoke(invocation);
	}
	
	private Object doInvoke(MethodInvocation invocation) throws Throwable {
		try {
			return invocation.proceed();
		} catch (Throwable e) {
			if (!(e instanceof CatchErrorException)) {
				logger.error("CatchError -> catch exception -> {} throw a Exception:{}, invoke stack:{}, param:{}", invocation.getMethod(), e, e.getStackTrace(), JsonTranslator.toString(invocation.getArguments()));
				throw new CatchErrorException(e.getMessage(), e.getCause());
			}
			throw e;
		} 
	}
}
