package com.skyfervor.framework.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.skyfervor.framework.constant.Constant;

/**
 * 日期操作公共辅助类
 * 
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	/**
	 * 获取默认日期 1900-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getDefaultDate() {
		return new Date(Constant.DefaultTime);
	}

	/**
	 * 获取当前系统时间，精确到秒。
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获取传入日期的星期。 星期日：1；星期一：2；星期二：3；。。。星期六：7
	 * 
	 * @param pdate
	 * @return
	 */
	public static int getDayOfWeek(Date pdate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(pdate);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取传入日期所在周数 例如 : 2015-12-07 的周数是 50
	 * 
	 * @param pdate
	 * @return
	 */
	public static int getWeekOfYear(Date pdate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(pdate);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取传入日期月份 1月：0 2月：1 。。。 12月：11
	 * 
	 * @param pdate
	 * @return
	 */
	public static int getMonth(Date pdate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(pdate);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 获取传入日期年份
	 * 
	 * @param pdate
	 * @return
	 */
	public static int getYear(Date pdate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(pdate);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 根据输入的字符串转为 日期对象。 要求输入的日期格式为 yyyy-MM-dd 或者yyyy/MM/dd，否则返回 null
	 * 
	 * @param inputStr
	 * @return
	 */
	public static Date getDateFromStr(String inputStr) {
		Date date = null;
		try {
			date = DateUtils.parseDate(inputStr, new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			date = DateUtils.truncate(date, Calendar.DATE);
		} catch (ParseException e) {
		}

		return date;
	}

	/**
	 * 将日期转换为yyyy-MM-dd格式的日期字符串
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDateToStr(Date date) {
		Date source = date;
		if (source == null) {
			source = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(source);
	}

	/**
	 * 将日期时间转换为yyyy-MM-dd HH:mm格式的日期字符串
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDateTimeToStr(Date date) {
		Date source = date;
		if (source == null) {
			source = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(source);
	}

	/**
	 * 将日期字符串转换为yyyy-MM-dd HH:mm格式的日期时间
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date formatStrToDateTime(String dateStr) throws ParseException {
		if (dateStr == null || ("").equals(dateStr)) {
			return new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.parse(dateStr);
	}

	/**
	 * 将日期时间转换为yyyy-MM-dd HH:mm:ss格式的日期字符串
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatFullDateTimeToStr(Date date) {
		Date source = date;
		if (source == null) {
			source = new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(source);
	}

	/**
	 * 将日期字符串转换为yyyy-MM-dd HH:mm:ss格式的日期时间
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date formatStrToFullDateTime(String dateStr) throws ParseException {
		if (dateStr == null || ("").equals(dateStr)) {
			return new Date();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dateStr);
	}

	/**
	 * 获取当前日期所在月份最后一天的日期
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getActualMaximumDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 获取传入两个日期的时间天数差， 单位为天，始终返回正数。 只返回整数部分。
	 * 
	 * @param Date
	 *            date1 开始时间
	 * @param Date
	 *            date2 结束时间
	 * @return 时间差
	 */
	public static long diffDay(Date date1, Date date2) {
		long diff = date2.getTime() - date1.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		if (days < 0) {
			days = 0 - days;
		}
		return days;
	}

	/**
	 * 获取当前时间所在年的最大周数 例如：2015年 52周
	 * 
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		int week = c.getWeeksInWeekYear();
		return week;
	}

	/**
	 * 获取输入日期是第几个月 1月：1， 2月：2....12月：12
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 周数所在的年份 例如 2015-12-29 是2016年的第1周,返回2016
	 * 
	 * @param date
	 * @return
	 */
	public static int getYearOfWeekYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		c.setTime(date);
		int year = c.getWeekYear();
		return year;
	}

	/**
	 * 初始化时的时间设置为，服务器当天00：00：00
	 * 
	 * @return
	 */
	public static long getTodayMillis() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
}
