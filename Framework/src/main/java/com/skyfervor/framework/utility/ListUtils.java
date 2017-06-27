package com.skyfervor.framework.utility;

import java.util.List;

/**
 * 用于操作list集合的工具类
 */
public class ListUtils extends org.apache.commons.collections.ListUtils {

	public static <T> T theFirstOfList(List<T> list) {
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
