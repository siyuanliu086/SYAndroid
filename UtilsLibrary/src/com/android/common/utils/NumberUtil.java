package com.android.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @TiTle NumberUtil.java
 * @Package com.android.common.utils
 * @Description 数据格式转换工具
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-19
 * @Company ISoftStone ZHHB
 */
public class NumberUtil {
	
	private final static DecimalFormat format_1 = new DecimalFormat(
			"###,##0.0");
	private final static DecimalFormat format_2 = new DecimalFormat(
			"###,##0.00");
	
	/**
	 * 将传入的数据转换成小数点后一位
	 * @param currentNum
	 * @return
	 */
	public static String transNumFormat(String currentNum) {
		if ("null".equals(currentNum) || StringUtil.isEmpty(currentNum)) {
			return "";
		}
		BigDecimal bigDecimal = null;
		try {
			bigDecimal = new BigDecimal(currentNum);
			String result = format_1.format(bigDecimal);
			return result;
		} catch (NumberFormatException exp) {
			return currentNum;
		}catch (IllegalArgumentException exp) {
			return currentNum;
		}
	}
	
	/**
	 * 将传入的数据转换成小数点后一位
	 * @param currentNum
	 * @return
	 */
	public static String transNumFormat1(float currentNum) {
		return format_1.format(currentNum);
	}
	
	/**
	 * 将传入的数据转换成小数点后两位
	 * @param currentNum
	 * @return
	 */
	public static String transNumFormat2(float currentNum) {
		return format_1.format(currentNum);
	}
	
	public static void main(String[] args) {
		System.out.println(transNumFormat("1234"));
	}
}
