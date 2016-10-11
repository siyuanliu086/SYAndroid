package com.android.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数据格式转换工具
 * @author HJWANGK
 *
 */
public class NumberUtil {
	
	private final static DecimalFormat format_0 = new DecimalFormat(
			"###,##0.0");
	
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
			String result = format_0.format(bigDecimal);
			return result;
		} catch (NumberFormatException exp) {
			return currentNum;
		}catch (IllegalArgumentException exp) {
			return currentNum;
		}

	}
	
	public static void main(String[] args) {
		System.out.println(transNumFormat("1234"));
	}
}
