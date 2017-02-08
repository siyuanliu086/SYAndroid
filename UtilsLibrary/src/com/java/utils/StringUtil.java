package com.java.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * @TiTle StringUtil.java
 * @Package com.java.utils
 * @Description
 * @Date 2016年12月5日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-12-05
 * @Company ISoftStone ZHHB
 */
public class StringUtil {
	/**
	 * 设置变量非空（非NULL）
	 * @param str
	 * @return
	 */
	public static String val(String str) {
		if (null == str || "".equals(str) || "NULL".equalsIgnoreCase(str)) {
			return "";
		} else {
			return str;
		}
	}
	
	/**
	 * 判断变量是否为空（包含：NULL、""、"NULL"）
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (null == str || "".equals(str) || "NULL".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 格式化时间字符串
	 * @param timestamp
	 * @return 返回格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String formatTime(Timestamp timestamp) {
		if (timestamp == null) return null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(timestamp);
	}
	
	/**
	 * 格式化时间字符串
	 * @param d
	 * @return 返回格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String formatTime(long d) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(d));
	}

	/**
	 * 自定义格式 格式化时间字符串
	 * @param d
	 * @param fmt 要格式化的时间格式，如（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String formatDateTime(long d, String fmt) {
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(new Date(d));
	}
	/**
	 * 自定义格式 格式化时间字符串
	 * @param timestamp
	 * @param fmt 要格式化的时间格式，如（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String formatDateTime(Timestamp timestamp, String fmt) {
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(timestamp);
	}
	
	/**
	 * 解析时间字符串
	 * @param dateTimeString
	 * @return Timestamp
	 */
	public static Timestamp parseTime(String dateTimeString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (isNull(dateTimeString)) return null;
		Timestamp ret = null;
		try {
			ret = new Timestamp(format.parse(dateTimeString).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 从字符串中截取指定长度的字符串
	 * @param str
	 * @param len
	 * @return
	 */
	public static String subString(String str, int len) {
		if (isNull(str)) return "";
		if (str.length() <=len) return str;
		return str.substring(0,len);
	}
	
	/**
	 * 生成MD5字符串
	 * @param s
	 * @return
	 */
	public static String md5StringFor(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(s.getBytes());
			StringBuilder builder = new StringBuilder();
			for (byte b : hash) {
				builder.append(Integer.toString(b & 0xFF, 16));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatTime(int time) {
		int sec = time % 60;
		int min = time / 60 % 60;
		int hou = time / 60 / 60 % 60;
		return (hou >= 10 ? hou : "0" + hou) + ":"
				+ (min >= 10 ? min : "0" + min) + ":"
				+ (sec >= 10 ? sec : "0" + sec);
	}
	
	
	
	/**
	 * 判断不为空
	 * @param str 字符串
	 * @return true 不为空 false 空
	 */
	public static boolean isNotEmpty(CharSequence str) {
		return !isEmpty(str);
	}

	/**
	 * 判断为空
	 * @param str 字符串
	 * @return true 空 false 不为空
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	public static <T> String join(Collection<T> coll, String separator) {
		return join(coll, separator, null);
	}

	public static String join(Object[] arr, String separator) {
		return join(arr, separator, null);
	}

	public static <T> String join(Collection<T> coll, String separator,
			String terminator) {
		return join(coll.toArray(new Object[coll.size()]), separator,
				terminator);
	}

	public static String join(Object[] arr, String separator, String terminator) {
		StringBuilder sb = new StringBuilder(arr.length * 2);
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length - 1) {
				sb.append(separator);
			} else if (terminator != null && arr.length > 0) {
				sb.append(terminator);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 从字符串转化为整形
	 * @param str	
	 * @return
	 */
	public static int string2Int(String str) {
		try {
			int value = Integer.valueOf(str);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
}	
