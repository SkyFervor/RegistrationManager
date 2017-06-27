package com.skyfervor.framework.utility;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date; 
import java.util.Iterator; 
import java.util.Map; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标准debug info语句的输出
 * example：
 *   String template = "{}类的{}方法，参数列表({},{},{},{},{},{},{},{})";
 *   String[] arr = { "obj1", "obj2", "obj3" };
 *	 String rs = LogInfoHelper.getInfo(template, "ContractController","test", "测试", 1, arr);
 *   logger.info(rs);
 *   
 */ 
public class LogInfoHelper {

	private static final Pattern p = Pattern.compile("\\{\\}");

	/**
	 * 用参数依次替换template中的占位符{}，直到所有占位符替换完。如果占位符多于参数个数，自动用空串填充；如果占位符少于参数个数，多余的参数
	 * 
	 * @param template
	 *            输出信息的主体
	 * @param args
	 *            参数列表,参数类型为任意类型
	 * @return
	 */
	public static String getInfo(String template, Object... args) {
		try{
			StringBuffer outPrint = new StringBuffer();
			if (template != null && !template.equals("")) {
				if(args == null || args.length < 1){//无参数
					return template;
				}
	 			outPrint = new StringBuffer(template);
				Matcher m = p.matcher(outPrint);
				// 使用find()方法查找第一个匹配的对象
				boolean result = m.find();
				// 使用循环将句子里所有的占位符{}找出并替换再将内容加到sb里
				int i = 0;
				while (result) {
					if (i < args.length) {
						m.appendReplacement(outPrint, format(args[i]));
					} else {
						// 参数列表个数少于占位符，用空串替换剩余的占位符
						m.appendReplacement(outPrint, "");
					}
					// 继续查找下一个匹配对象
					result = m.find();
					i++;
				}//end while
				outPrint.append(")");
				outPrint = new StringBuffer(outPrint.subSequence(template.length(),
						outPrint.length()));
				
				if((")").equals(outPrint.toString())){//无占位符
					return template;
				}
			}//end if template is null
			return outPrint.toString();

		}catch(Exception e){
			
		}
		return "";
	}

	/**
	 * 用参数替换template中的第一个占位符{}，如果有其他占位符{}，替换为空字符串
	 * 
	 * @param args
	 *            数组对象
	 * @return
	 */
	private static String getArrayInfo(Object[] args) {
		String arrayObject = "";
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				arrayObject += "【" + args[i].toString() + "】";
			}
		}
		return arrayObject;
	}

	/**
	 * 用参数替换template中的第一个占位符{}，如果有其他占位符{}，替换为空字符串
	 * 
	 * @param c
	 *            一个集合对象
	 * @return
	 */
	private static String getCollectionInfo(Collection<? extends Object> c) {
		String arrayObject = "";
		if (c != null && c.size() > 0) {
			Iterator<? extends Object> it = c.iterator();
			while (it.hasNext()) {
				arrayObject += "【" + it.next().toString() + "】";
			}
		}
		return arrayObject;
	}

	/**
	 * 用参数替换template中的第一个占位符{}，如果有其他占位符{}，替换为空字符串
	 * 
	 * @param template
	 * @param map
	 *            key-value类型
	 * @return
	 */
	private static String getMapInfo(Map<String, Object> map) {
		String arrayObject = "";
		if (map != null && !map.isEmpty()) {
			Iterator<? extends Object> it = map.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				arrayObject += String.valueOf(key) + "=" + format(map.get(key))
						+ ",";
			}
			if (!arrayObject.equals("")) {
				arrayObject = arrayObject
						.substring(0, arrayObject.length() - 1);
			}
		}
		return arrayObject;
	}

	@SuppressWarnings("unchecked")
	private static String format(Object obj) {
		if (obj instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return String.valueOf(sdf.format(obj));
		} else if (obj instanceof Collection) {
			String collectionStr = getCollectionInfo((Collection<? extends Object>) obj);
			return String.valueOf(collectionStr);
		} else if (obj instanceof Map) {
			String mapStr = getMapInfo((Map<String, Object>) obj);
			return String.valueOf(mapStr);
		} else if (obj instanceof Object[]) {
			String arrayStr = getArrayInfo((Object[]) obj);
			return String.valueOf(arrayStr);
		} else {
			if(obj == null ){
				return "";
			}
			return String.valueOf(obj);
		}
	}
  
}
