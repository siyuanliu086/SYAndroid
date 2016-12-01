package com.android.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @TiTle DateUtil.java
 * @Package com.android.common.utils
 * @Description 格式化
 * @Date 2016年12月1日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-12-01
 * @Company ISoftStone ZHHB
 */
public class DateUtil {
	public static String[] weekName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	public static String[] monthName = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

	public static String formatDateTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String formatDateTime2(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(date);
	}

	public static String getCurrentTime() {
		Date date = new Date(Long.valueOf(System.currentTimeMillis()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String getCurrentDate() {
		Date date = new Date(Long.valueOf(System.currentTimeMillis()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	/**
	 * 获取当前时间 格式为yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDate2() {
		Date date = new Date(Long.valueOf(System.currentTimeMillis()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static String transTimeToDate(String ms) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(translateDateTime(ms));
	}

	public static String transTimeToDateYM(String ms) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		return format.format(translateDateTime(ms));
	}

	public static String transTimeToDateYM(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
		return format.format(date);
	}

	public static String transDateToDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}

	public static String transTimeToms(String ms) {
		Date date = new Date(Long.valueOf(ms));
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		return format.format(date);
	}

	public static String transTimeTom(String m) {
		SimpleDateFormat format = new SimpleDateFormat("mm");
		return format.format(translateDateTime(m));
	}

	public static String transTimeToh(Date m) {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return format.format(m);
	}

	public static String transTimeToh(String h) {
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return format.format(translateDateTime(h));
	}

	public static String transTimeTohm(String hm) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(translateDateTime(hm));
	}

	public static String transTimeTod(String h) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return format.format(translateDateTime(h));
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String ms2Timestamp(String ms) {
		Date date = new Date(Long.valueOf(ms));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String ms2Timestamp(long ms) {
		Date date = new Date(ms);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static Date translate(String date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatDate.format(date);
		Date time = null;
		try {
			time = formatDate.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;

	}

	public static String transDate(String date) {
		if ("".equals(date) || date == null) {
			return "";
		}

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		Date time = null;
		try {
			time = formatDate.parse(date);
			str = formatDate.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String transTimeTomdhm(String date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = "";
		Date time = null;
		try {
			time = formatDate.parse(date);
			str = formatDate.format(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static Date translateDateTime(String dateTime) {
		SimpleDateFormat formatDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = formatDate.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;

	}

	/**
	 * 转换日期格式为yyyy-MM-dd
	 */
	public static String getChangeDateFormat(Date date) {
		String str = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			str = sd.format(date);
		}
		return str;
	}

	/**
	 * 转换日期格式为HH:mm
	 */
	public static String getChangeTimeFormat(Date date) {
		String str = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
			str = sd.format(date);
		}
		return str;
	}

	/**
	 * 转换日期格式为yyyy-MM
	 */
	public static String getChangeMonthFormat(Date date) {
		String str = null;
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
			str = sd.format(date);
		}
		return str;
	}

	/**
	 * 转换日期格式为毫秒字符串
	 */
	public static String getChangeDateToMilliscond(Date date) {
		long time = 0;
		if (date != null && !"".equals(date)) {
			try {
				SimpleDateFormat sd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String format = sd.format(date);
				time = sd.parse(format).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return time + "";
	}

	/**
	 * 获取标准格式时间：yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDate(Date date) {
		String format = "";
		if (date != null && !"".equals(date)) {
			try {
				SimpleDateFormat sd = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				format = sd.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return format;
	}

	/**
	 * 字符串转日期格式
	 */
	public static Date getChangeStringToDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getCurrentTimeMillis() {
		return System.currentTimeMillis() + "";
	}

	/**
	 * 取得当前日期是多少周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到自查年
	 * 
	 * @return
	 */
	public static int getZcYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到自查月
	 * 
	 * @return
	 */
	public static int getZcMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到自查季度
	 * 
	 * @return
	 */
	public static int getZcQuarter() {
		Calendar cal = Calendar.getInstance();
		return getQuarter(cal);
	}

	/**
	 * 获得当前季度
	 * 
	 * @param month
	 * @return
	 */
	public static int getQuarter(Calendar ca) {
		if (ca != null) {
			int month = ca.get(Calendar.MONTH);
			if (month >= 1 && month <= 3) {
				return 1;
			} else if (month >= 4 && month <= 6) {
				return 2;
			} else if (month >= 7 && month <= 9) {
				return 3;
			} else if (month >= 10 || month <= 12) {
				return 4;
			}
		}
		return 0;
	}

	/**
	 * @方法名: getSimpleDateStr
	 * @说 明: oracle时间字符串字段转化
	 * @参 数: @param str
	 * @参 数: @return
	 * @return String 返回类型
	 * @throws
	 */
	public static String getSimpleDateStr(String str) {
		if (null != str && str.length() > 10) {
			str = str.substring(0, 10);
		}
		return str;
	}

	public static String removeMs(String time) {
		String temp = time;
		if (null != time && time.contains(".")) {
			// time=time.replaceAll(" ", "");
			try {
				time = time.substring(0, time.lastIndexOf("."));
				time = time.trim();
				String[] split = time.split("\\.");
				time = split[0] + " " + split[1] + ":" + split[2] + ":"
						+ split[3];
			} catch (Exception e) {
				e.printStackTrace();
				return temp;
			}
		}
		return time;
	}

	/**
	 * @方法名: getCurrentYear
	 * @说 明: 获取当前年
	 * @参 数: @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 
	 * @方法名: getCurrentMonth
	 * @说 明:获取当前月
	 * @参 数: @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public static Integer getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * @方法名: getCurrentSeason
	 * @说 明: 获取当前季度
	 * @参 数: @return
	 * @return int 返回类型
	 * @throws
	 */
	public static int getCurrentSeason() {
		int month = getCurrentMonth();
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int season = 0;
		if (day < 8) {// 如果当前日小于8号
			if (month == 1) {// 如果是一月
				season = 4;
			} else if (month >= 2 && month < 5) {
				season = 1;
			} else if (month >= 5 && month < 8) {
				season = 2;
			} else if (month >= 8 && month < 11) {
				season = 3;
			}
		} else {
			if (month >= 1 && month < 4) {
				season = 1;
			} else if (month >= 4 && month < 7) {
				season = 2;
			} else if (month >= 7 && month < 10) {
				season = 3;
			} else if (month >= 10 && month < 12) {
				season = 4;
			}
		}
		return season;
	}

	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static int getCurrentMonthDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public static int getWeekDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	public static int getHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}
    
    /**
     * 通过年份和月份 得到当月的日子
     * 
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
		month++;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: 
		    return 31;
		case 4:
		case 6:
		case 9:
		case 11: 
		    return 30;
		case 2:
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
				return 29;
			}else{
				return 28;
			}
		default:
			return  -1;
		}
    }
    
    /**
     * 返回当前月份1号位于周几
     * @param year 年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(year, month, 1);
    	return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 根据列明获取周
     * @param column
     * @return
     */
    public static String getWeekName(int column){
    	switch(column){
    	case 0:
    		return "周日";
    	case 1:
    		return "周一";
    	case 2:
    		return "周二";
    	case 3:
    		return "周三";
    	case 4:
    		return "周四";
    	case 5:
    		return "周五";
    	case 6:
    		return "周六";
    	default :
    			return "";
    	}
    }
    
    /**
     * 根据列明获取周
     * @param column
     * @return
     */
    public static String getMonthName(int month){
    	return monthName[month];
    }
    

}
