package com.skyfervor.framework.utility;

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {

	/**
	 * 比较两个Byte类型是否相等，屏蔽了为空的判断。
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean IsEquals(Byte b1, Byte b2) {
		if (b1 == null && b2 == null)
			return true;
		if (b1 != null)
			return b1.equals(b2);
		return false;
	}

	/**
	 * 比较两个Integer类型是否相等，屏蔽了为空的判断。
	 * 
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static boolean IsEquals(Integer i1, Integer i2) {
		if (i1 == null && i2 == null)
			return true;
		if (i1 != null)
			return i1.equals(i2);
		return false;
	}

	/**
	 * 比较两个Integer类型是否相等，屏蔽了为空的判断。
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static boolean IsEquals(Long l1, Long l2) {
		if (l1 == null && l2 == null)
			return true;
		if (l1 != null)
			return l1.equals(l2);
		return false;
	}
}
