package com.android.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @TiTle PreferencesUtils.java
 * @Package com.android.common.utils
 * @Description Preferences 值存取、文件名为包名
 * @Date 2016年10月21日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-21 10:51:27
 * @Company ISoftStone ZHHB
 */
public class PreferencesUtils {
	// 保存string类型的value到whichSp中的field字段
	public static void putSharePreferences(Context mContext, String field, String value) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(mContext.getPackageName(), 0);
		sp.edit().putString(field, value).commit();
	}

	// 保存int类型的value到whichSp中的field字段
	public static void putSharePreferences(Context mContext, String field, int value) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(mContext.getPackageName(), 0);
		sp.edit().putInt(field, value).commit();
	}

	// 保存boolean类型的value到whichSp中的field字段
	public static void putSharePreferences(Context mContext, String field, boolean value) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(mContext.getPackageName(), 0);
		sp.edit().putBoolean(field, value).commit();
	}

	public static String getSharePreferencesString(Context mContext, String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(mContext.getPackageName(), 0);
		String s = sp.getString(field, "");// 如果该字段没对应值，则取出字符串0
		return s;
	}

	// 取出whichSp中field字段对应的int类型的值
	public static int getSharePreferencesInt(Context mContext, String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(mContext.getPackageName(), 0);
		int i = sp.getInt(field, 0);// 如果该字段没对应值，则取出0
		return i;
	}

	// 取出whichSp中field字段对应的boolean类型的值
	public static boolean getSharePreferencesBoolean(Context mContext, String field) {
		SharedPreferences sp = (SharedPreferences) mContext
				.getSharedPreferences(mContext.getPackageName(), 0);
		boolean i = sp.getBoolean(field, false);// 如果该字段没对应值，则取出0
		return i;
	}

	// 清空保存的数据
	public static void clearSharePreferences(Context mContext) {
		try {
			SharedPreferences sp = (SharedPreferences) mContext
					.getSharedPreferences(mContext.getPackageName(), 0);
			sp.edit().clear().commit();
		} catch (Exception e) {
		}
	}

}
