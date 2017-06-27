package com.skyfervor.framework.utility;

import java.lang.reflect.Field;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作String字符串工具类
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

	public static String getUUIDString() {
		return UUID.randomUUID().toString();
	}

	public static Object CorrectString(Object object) {

		if (object == null) {
			return object;
		}

		Field[] fields = ReflectionUtils.getAllDeclaredFields(object.getClass());
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(String.class)) {
				String value = null;
				field.setAccessible(true);
				try {
					value = (String) field.get(object);
				} catch (Exception e) {
					logger.error("CorrectString -> field.get fail -> Exception:{}", e);
					continue;
				}

				if (value == null || value.isEmpty()) {
					continue;
				}

				String regex = "\t"; // 正则表达式

				String newValue = value.replaceAll(regex, "");
				if (value.length() != newValue.length()) {
					try {
						field.set(object, newValue);
					} catch (Exception e) {
						logger.error("CorrectString -> field.set fail -> Exception:{}", e);
						continue;
					}
					logger.info("CorrectString Removal character, old:{}, new:{}", value, newValue);
				}
			}
		}

		return object;
	}

	/**
	 * 比较两个字符串是否相等，屏蔽了为空的判断。
	 * 
	 * @param sone
	 * @param stow
	 * @return
	 */
	public static boolean IsEquals(String sone, String stow) {
		if (sone == null && stow == null)
			return true;
		if (sone != null)
			return sone.equals(stow);
		return false;
	}
}
