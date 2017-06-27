package com.skyfervor.framework.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext appContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}

	public static ApplicationContext getContext() {
		return appContext;
	}

	public static <T> T getBean(Class<T> requiredType) {
		return appContext.getBean(requiredType);
	}
}
