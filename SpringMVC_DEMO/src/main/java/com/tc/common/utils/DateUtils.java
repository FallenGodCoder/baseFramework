package com.tc.common.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat();

	/**
	 * Gets date difference in miliseconds.
	 *
	 * @param fromDate
	 *            First date object containing the date.
	 * @param toDate
	 *            Second date object containing the date.
	 * @return Date difference in miliseconds.
	 */
	public static long getDateDifferenceInMiliseconds(Date fromDate, Date toDate) {
		return Math.abs(fromDate.getTime() - toDate.getTime());
	}

	/**
	 * Gets current date.
	 *
	 * @return Current date.
	 */
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTime().getTime());
	}

	/**
	 * Gets current date time.
	 *
	 * @return Current date time.
	 */
	public static Date getCurrentDateTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Gets short month strings.
	 *
	 * @return Short month strings.
	 */
	public static String[] getShortMonths() {
		return new DateFormatSymbols().getShortMonths();
	}

	/**
	 * Gets short month strings for specific locale.
	 *
	 * @param locale
	 *            Locale.
	 * @return Short month strings.
	 */
	public static String[] getShortMonths(Locale locale) {
		if (locale == null) {
			logger.error("Method getShortMonths: Locale is missing!");
			throw new RuntimeException("Method getShortMonths: Locale is missing!");
		}
		return new DateFormatSymbols(locale).getShortMonths();
	}

	/**
	 * Converts date compartments into an array of strings..
	 *
	 * @param date
	 *            Date.
	 * @return Array of strings (year, month, date).
	 */
	public static String[] convertDateToStrings(Date date) {
		return convertDateToStrings(date, Locale.getDefault());
	}

	/**
	 * Converts date compartments into an array of strings..
	 *
	 * @param date
	 *            Date.
	 * @param locale
	 *            Locale.
	 * @return Array of strings (year, month, date).
	 */
	public static String[] convertDateToStrings(Date date, Locale locale) {
		if (locale == null) {
			logger.error("Method convertDateToStrings: Locale is missing!");
		}

		if (date == null) {
			return null;
		}

		String[] values = new String[3];
		int yearValue, monthValue, dateValue;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(date);
		yearValue = calendar.get(Calendar.YEAR);
		monthValue = calendar.get(Calendar.MONTH) + 1;
		dateValue = calendar.get(Calendar.DATE);

		values[0] = "" + yearValue;
		values[1] = monthValue >= 10 ? "" + monthValue : "0" + monthValue;
		values[2] = dateValue >= 10 ? "" + dateValue : "0" + dateValue;

		return values;
	}

	/**
	 * Converts date time compartments into an array of strings..
	 *
	 * @param dateTime
	 *            Date time.
	 * @return Array of strings (year, month, date, hour, minute, second).
	 */
	public static String[] convertDateTimeToStrings(Date dateTime) {
		return convertDateTimeToStrings(dateTime, Locale.getDefault());
	}

	/**
	 * Converts date time compartments into an array of strings..
	 *
	 * @param dateTime
	 *            Date time.
	 * @param locale
	 *            Locale.
	 * @return Array of strings (year, month, date, hour, minute, second).
	 */
	public static String[] convertDateTimeToStrings(Date dateTime, Locale locale) {
		if (locale == null) {
			logger.error("Method convertDateTimeToString: Locale is missing!");
		}

		if (dateTime == null) {
			return null;
		}

		String[] values = new String[6];
		int yearValue, monthValue, dateValue, hourValue, minuteValue, secondValue;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(dateTime);
		yearValue = calendar.get(Calendar.YEAR);
		monthValue = calendar.get(Calendar.MONTH) + 1;
		dateValue = calendar.get(Calendar.DATE);
		hourValue = calendar.get(Calendar.HOUR_OF_DAY);
		minuteValue = calendar.get(Calendar.MINUTE);
		secondValue = calendar.get(Calendar.SECOND);

		values[0] = "" + yearValue;
		values[1] = monthValue >= 10 ? "" + monthValue : "0" + monthValue;
		values[2] = dateValue >= 10 ? "" + dateValue : "0" + dateValue;
		values[3] = hourValue >= 10 ? "" + hourValue : "0" + hourValue;
		values[4] = minuteValue >= 10 ? "" + minuteValue : "0" + minuteValue;
		values[5] = secondValue >= 10 ? "" + secondValue : "0" + secondValue;

		return values;
	}
	
	/**
	 * 获取当前系统时间
	 * @param pattern 日期格式 如:yyyyMMdd
	 * @return
	 */
	public static String getNowDate(String pattern){
		SDF.applyPattern(pattern);
		return SDF.format(Calendar.getInstance(Locale.CHINA).getTime());
	}
	
	/**
	 * 获取当前系统时间,日期格式 为:yyyyMMdd
	 * @return
	 */
	public static String getNowDateDefault(){
		return getNowDate("yyyyMMdd");
	}
	
	/**
	 * string日期转java.util.Date
	 * @param sDate
	 * @param pattern eg:yyyyMMdd
	 * @return
	 * @throws java.text.ParseException
	 */
	public static java.util.Date parseStr2Date(String sDate, String pattern) throws ParseException {
		SDF.applyPattern(pattern);
		return SDF.parse(sDate);
	}

	/**
	 * 日期转字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2Str(java.util.Date date, String pattern) {
		SDF.applyPattern(pattern);
		return SDF.format(date);
	}

	/**
	 *
	 * @param dateStr
	 * @return
	 */
	public static java.sql.Date convertSQLDate(String dateStr){
		if( Strings.isNullOrEmpty(dateStr) ) return null;
		if( dateStr.indexOf("年") > 0 ) dateStr = dateStr.replace("年", "-").replace("月", "-").replace("日", "");
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new java.sql.Date(f.parse(dateStr).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static java.sql.Date convertSQLDate(String dateStr, String format){
		if( Strings.isNullOrEmpty(dateStr) ) return null;
		Preconditions.checkNotNull(format, "日期格式不能为空");

		if( format.indexOf("年") > 0 ) dateStr = dateStr.replace("年", "-").replace("月", "-").replace("日", "");
		DateFormat f = new SimpleDateFormat(Strings.isNullOrEmpty(format) || format.indexOf("年") > 0 ? "yyyy-MM-dd" : ( replaceFormat(format)));
		try {
			return new java.sql.Date(f.parse(dateStr).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String replaceFormat( String format ) {
		String _format = format.toLowerCase();
		if(format.indexOf("m") < 0 || ( _format.indexOf("h") >= 0 && _format.indexOf("m") > _format.indexOf("h") ) )
			return format;
		return format.replace("ymm", "yMM").replace("y-mm", "y-MM").replace("y/mm", "y/MM").replace("mmd", "MMd").replace("mm/d", "MM/d").replace("mm-d", "MM-d");
	}
	
	public static String getCurrDate(String format) {
		SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (format != null && !"".equals(format.trim())) {
			DF.applyPattern(format);
		}
		return DF.format(new java.util.Date());
	}
}

// end of DateUtils.java