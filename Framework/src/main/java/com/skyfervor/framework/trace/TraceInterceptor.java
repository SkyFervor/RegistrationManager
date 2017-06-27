package com.skyfervor.framework.trace;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.DirectFieldAccessor;

import com.alibaba.dubbo.extend.tools.Postman;

public class TraceInterceptor implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);
	
	public static final String TRACE_KAY = "trace_key";
	public static final String TRACE_STACK = "trace_stack";
	
	private String projectPrdfix;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
				
		if (!Postman.exitParam(TRACE_STACK)) {
			Postman.putParam(TRACE_STACK, new LinkedList<String>());
		}
		
		if (getTraceStack().isEmpty()) {
			Postman.putParam(TRACE_KAY, UUID.randomUUID().toString().replaceAll("-", ""));
		}
		
		return doInvoke(invocation);
	}
	
	private Object doInvoke(MethodInvocation invocation) throws Throwable {
		String className = TraceInterceptor.getTargetClass(invocation).getName();
		try {
			if (projectPrdfix == null || className.startsWith(projectPrdfix)) {
				getTraceStack().push(getTargetClass(invocation).getSimpleName() + "." + invocation.getMethod().getName());
				logger.debug("TraceInterceptor -> push method name -> method name:{}", getTraceStack().peek());
			}
			return invocation.proceed();
		} finally {
			if (projectPrdfix == null || className.startsWith(projectPrdfix)) {
				logger.debug("TraceInterceptor -> pop method name -> method name:{}", getTraceStack().peek());
				getTraceStack().pop();
			}
		}
	}
	
	public static Class<? extends Object> getTargetClass(MethodInvocation invocation) {
		
		Object object = invocation.getThis();
		
		while (AopUtils.isAopProxy(object) && !AopUtils.isJdkDynamicProxy(object)) {
			object = AopUtils.getTargetClass(object);
		}
		
		while (Proxy.isProxyClass(object.getClass())) {
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(object);
			if (!invocationHandler.getClass().getName().equals("org.springframework.aop.framework.JdkDynamicAopProxy")) {
				return object.getClass();
			}
			AdvisedSupport advised = (AdvisedSupport) new DirectFieldAccessor(invocationHandler).getPropertyValue("advised");  
	        try {
	        	object = advised.getTargetSource().getTarget();
			} catch (Exception e) {
				logger.warn("TraceInterceptor -> getTargetClass -> getTarget -> Exception:{}", e);
				break;
			}
		}
		
		return object.getClass();
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedList<String> getTraceStack() {
		Object object = Postman.getParam(TRACE_STACK);
		if (object != null && !(object instanceof LinkedList) && object instanceof List) {
			Postman.putParam(TRACE_STACK, new LinkedList<String>((List<String>)object));
		}
		return (LinkedList<String>) Postman.getParam(TRACE_STACK);
	}
	
	public static String getTraceStackToString() {
		
		if (getTraceStack() == null || getTraceStack().isEmpty()) {
			return "[]";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String name : getTraceStack()) {
			sb.append(name).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	
	public static String getTraceKey() {
		return (String) Postman.getParam(TRACE_KAY);
	}
	
	public static String getInvoke() {
		if (getTraceStack() == null || getTraceStack().size() == 0) {
			return "";
		}
		
		return getTraceStack().peek();
	}
	
	public static String getParentInvoke() {
		if (getTraceStack() == null || getTraceStack().size() <= 1) {
			return "Root";
		}
		
		return getTraceStack().get(1);
	}
	
	public static void setTraceKey(String value) {
		Postman.putParam(TRACE_KAY, value);
	}
	
	public void setProjectPrdfix(String projectPrdfix) {
		this.projectPrdfix = projectPrdfix;
	}
}
