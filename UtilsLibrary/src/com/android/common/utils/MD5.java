package com.android.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description MD5加密
 * @author Jenson
 * 
 */
public class MD5 {
	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public MD5() {
	}

	/**
	 * @description 返回形式为数字跟字符串
	 * @date 2014-4-3
	 * @author Jenson
	 * @param bByte
	 * @return
	 * @return String
	 */
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	/**
	 * @description 返回形式只为数字.
	 * @date 2014-4-3
	 * @author Jenson
	 * @param bByte
	 * @return
	 * @return String
	 */
	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	/**
	 * @description 转换字节数组为16进制字串.
	 * @date 2014-4-3
	 * @author Jenson
	 * @param bByte
	 * @return
	 * @return String
	 */
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	/**
	 * @description 返回大写的MD5码
	 * @date 2014-4-3
	 * @author Jenson
	 * @param strObj
	 * @return
	 * @return String
	 */
	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString.toUpperCase();// 返回大写
	}

	public static void main(String[] args) {
		MD5 getMD5 = new MD5();
		System.out.println(getMD5.GetMD5Code("123456"));
	}
}
