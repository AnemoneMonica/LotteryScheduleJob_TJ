/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hb.lottery.conf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具�?, 继承org.apache.commons.lang.time.DateUtils�?
 * 
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM","yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss.S" };

	public static Date getCurDate() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date time = null;
		try {
			time = sdf.parse(sdf.format(new Date()));

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 得到当前日期字符�? 格式（yyyy-MM-dd�?
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符�? 格式（yyyy-MM-dd�? pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符�? 默认格式（yyyy-MM-dd�? pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	 public static String getNowLS() {
	        return formatDate(new Date(), "yyyyMMddHHmmss");
	    }

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss�?
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符�? 格式（HH:mm:ss�?
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss�?
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符�? 格式（yyyy�?
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符�? 格式（MM�?
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符�? 格式（dd�?
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符�? 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日�? 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天�?
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小�?
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分�?
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（�?,�?:�?:�?.毫秒�?
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天�?
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// System.out.println(formatDate(parseDate("2010/3/6")));
		// System.out.println(getDate("yyyy年MM月dd�? E"));
		// long time = new Date().getTime()-parseDate("2012-11-19").getTime();
		// System.out.println(time/(24*60*60*1000));
		
		System.out.println(getTimeBefore(new Date(),2));
	}
	//获取几分钟前的时�? min:分钟
	public static String getTimeBefore(Date date,int min)
	{
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)-min);
		return formatDateTime(calendar.getTime());
	}
	
	//获取几分钟前的时�? Hour:小时 
		public static String getTimeBeforeHour(Date date,int hour)
		{
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date);
			 calendar.add(calendar.HOUR, hour);
			return formatDateTime(calendar.getTime());
		}
	
	//当前时间加多少分钟后
	public static String getTimeAfter(Date date,int min)
	{
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)+min);
		return formatDateTime(calendar.getTime());
	}
	
	//当前时间加多少秒�?
		public static String getTimeAfterSec(Date date,int second)
		{
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date);
			 calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+second);
			return formatDateTime(calendar.getTime());
		}
	
	//当前时间加多少天�?
		public static String getDayAfter(Date date,int day)
		{
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(date);
			 calendar.add(calendar.DATE, day);
			return formatDateTime(calendar.getTime());
		}
}
