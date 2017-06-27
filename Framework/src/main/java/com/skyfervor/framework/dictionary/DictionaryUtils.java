package com.skyfervor.framework.dictionary;

import java.util.Collections;
import java.util.List;

import com.skyfervor.framework.context.SpringContext;
import com.skyfervor.framework.utility.NumberUtils;
import com.skyfervor.framework.utility.StringUtils;

public class DictionaryUtils {
	/**
	 * 根据字典类型名称，获取所有字典明细数据
	 * 
	 * @param typeName
	 * @return
	 * @throws Exception
	 */
	public static List<DictionaryValueVo> getValuesByTypeName(String typeName) throws Exception {
		return getValueListByTypeName(typeName, null);
	}

	/**
	 * 根据字典类型名称及是否显示获取所有字典明细数据
	 * 
	 * @param typeName
	 * @param display
	 * @return
	 * @throws Exception
	 */
	public static List<DictionaryValueVo> getValueListByTypeName(String typeName, Boolean display)
			throws Exception {
		DictionaryService dictionaryService = SpringContext.getBean(DictionaryService.class);
		DictionaryTypeVo typeVo = dictionaryService.queryTypeByName(typeName);
		if (typeVo == null)
			return Collections.emptyList();

		return dictionaryService.queryValueListByTypeId(typeVo.getDictionaryTypeId(), display);
	}

	/**
	 * 判断指定类型的字典中是否存在对应的Value值
	 * 
	 * @param typeName
	 * @param valueName
	 * @return
	 * @throws Exception
	 */
	public static boolean containsValueName(String typeName, String valueName, Boolean display)
			throws Exception {
		List<DictionaryValueVo> dictionaryValues = getValueListByTypeName(typeName, display);
		for (DictionaryValueVo valueVo : dictionaryValues) {
			if (StringUtils.equals(valueVo.getName(), valueName))
				return true;
		}
		return false;
	}

	/**
	 * 判断指定类型的字典中是否存在对应的Value值
	 * 
	 * @param typeName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static boolean containsValue(String typeName, int value, Boolean display)
			throws Exception {
		List<DictionaryValueVo> dictionaryValues = getValueListByTypeName(typeName, display);
		for (DictionaryValueVo valueVo : dictionaryValues) {
			if (NumberUtils.IsEquals(valueVo.getValue(), value))
				return true;
		}
		return false;
	}

	/**
	 * 根据字典值获取对应的描述
	 * 
	 * @param typeName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String getValueDescription(String typeName, int value) throws Exception {
		List<DictionaryValueVo> dictionaryValues = getValuesByTypeName(typeName);
		for (DictionaryValueVo valueVo : dictionaryValues) {
			if (NumberUtils.IsEquals(valueVo.getValue(), value))
				return valueVo.getDescription();
		}
		return "";
	}
}
