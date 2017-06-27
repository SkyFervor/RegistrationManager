package com.skyfervor.framework.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 反射工具类
 */
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {

	/**
	 * 通过反射获取class所有的成员变量，包括继承父类获得的变量
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllDeclaredFields(Class<?> clazz) {
		Assert.notNull(clazz, "Class must not be null");
		List<Field> fields = new ArrayList<Field>();
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			fields.addAll(Arrays.asList(searchType.getDeclaredFields()));
			searchType = searchType.getSuperclass();
		}
		return fields.toArray(new Field[fields.size()]);
	}

}
