package com.skyfervor.framework.utility;

import java.util.List;

import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.context.SpringContext;
import com.skyfervor.framework.dictionary.DictionaryUtils;
import com.skyfervor.framework.dictionary.DictionaryValueVo;
import com.skyfervor.framework.user.RoleService;
import com.skyfervor.framework.user.RoleVo;
import com.skyfervor.framework.user.UserService;
import com.skyfervor.framework.user.UserVo;

public class HtmlTagHelper {
	/**
	 * 根据字典生成下拉option选项
	 * 
	 * @param typeName
	 * @param defaultValue
	 * @param display
	 * @return
	 * @throws Exception
	 */
	public static String getDictionaryOptions(String typeName, Integer defaultValue,
			Boolean display) throws Exception {
		List<DictionaryValueVo> data = DictionaryUtils.getValueListByTypeName(typeName, display);

		StringBuilder mhtml = new StringBuilder();
		for (DictionaryValueVo vo : data) {
			mhtml.append("<option value=\"").append(vo.getValue()).append("\"");
			if (NumberUtils.IsEquals(defaultValue, vo.getValue())) {
				mhtml.append(" selected=\"selected\"");
			}
			mhtml.append(">").append(vo.getDescription()).append("</option>");
		}
		return mhtml.toString();
	}

	/**
	 * 根据字典生成单选按钮
	 * 
	 * @param typeName
	 * @param defaultValue
	 * @param display
	 * @return
	 * @throws Exception
	 */
	public static String getDictionaryRadios(String typeName, Integer defaultValue, Boolean display)
			throws Exception {
		List<DictionaryValueVo> data = DictionaryUtils.getValueListByTypeName(typeName, display);

		StringBuilder mhtml = new StringBuilder();
		for (DictionaryValueVo vo : data) {
			mhtml.append("<input type=\"radio\" name=\"").append(vo.getName()).append("\" value=\"")
					.append(vo.getValue()).append("\"");
			if (NumberUtils.IsEquals(defaultValue, vo.getValue()))
				mhtml.append(" checked=\"checked\"");
			mhtml.append(" />");

			mhtml.append("<span>").append(vo.getDescription()).append("</span>");
		}
		return mhtml.toString();
	}

	/**
	 * 根据字典生成多选按钮
	 * 
	 * @param typeName
	 * @param defaultValue
	 * @param display
	 * @return
	 * @throws Exception
	 */
	public static String getCheckBoxByDictionary(String typeName, List<Integer> defaultValue,
			Boolean display) throws Exception {
		List<DictionaryValueVo> data = DictionaryUtils.getValueListByTypeName(typeName, display); //根据字典类型，获取所有所有字典明显数据

		StringBuilder mhtml = new StringBuilder(); //构建一个字符串的生成器
		for (DictionaryValueVo vo : data) {
			mhtml.append("<input type=\"checkbox\" name=\"").append(vo.getName())
					.append("\" value=\"").append(vo.getValue()).append("\"");
			if (defaultValue != null)
				for (Integer value : defaultValue)
					if (NumberUtils.IsEquals(value, vo.getValue()))
						mhtml.append(" checked=\"checked\"");
			mhtml.append(" />");

			mhtml.append("<span>").append(vo.getDescription()).append("</span>");
		}

		return mhtml.toString();
	}

	/**
	 * 根据用户信息生成下拉option选项
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getUserOptions() throws Exception {
		return getUserOptions(null, false);
	}

	/**
	 * 根据用户信息生成下拉option选项
	 * 
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static String getUserOptions(List<Long> defaultValue, Boolean disabled)
			throws Exception {
		UserService userService = SpringContext.getBean(UserService.class);
		UserVo userVo = new UserVo();
		if (disabled != null)
			userVo.setStatus(disabled ? Constant.User.DISABLED : Constant.User.NORMAL);
		List<UserVo> data = userService.queryListByName(userVo);

		StringBuilder mhtml = new StringBuilder();
		for (UserVo vo : data) {
			mhtml.append("<option value=\"").append(vo.getUserId()).append("\"");
			if (defaultValue != null)
				for (Long value : defaultValue)
					if (NumberUtils.IsEquals(value, vo.getUserId()))
						mhtml.append(" selected=\"selected\"");

			mhtml.append(">").append(vo.getUserName()).append("</option>");
		}
		return mhtml.toString();
	}

	/**
	 * 获取用户总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static long getUserCount() throws Exception {
		UserService userService = SpringContext.getBean(UserService.class);
		return userService.getCount(new UserVo());
	}

	/**
	 * 根据角色信息生成下拉option选项
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getRoleOptions() throws Exception {
		return getRoleOptions(null);
	}

	/**
	 * 根据用户信息生成下拉option选项
	 * 
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static String getRoleOptions(Long defaultValue) throws Exception {
		RoleService roleService = SpringContext.getBean(RoleService.class);
		List<RoleVo> data = roleService.queryList(new RoleVo());

		StringBuilder mhtml = new StringBuilder();
		for (RoleVo vo : data) {
			mhtml.append("<option value=\"").append(vo.getRoleId()).append("\"");

			if (defaultValue != null && NumberUtils.IsEquals(defaultValue, vo.getRoleId()))
				mhtml.append(" selected=\"selected\"");

			mhtml.append(">").append(vo.getName()).append("</option>");
		}
		return mhtml.toString();
	}

}
