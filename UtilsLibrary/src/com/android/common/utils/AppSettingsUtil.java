package com.android.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSettingsUtil {
	private static SharedPreferences preferences;

	public static void putString(Context context, String key, String value) {
		if (context == null)
			return;
		preferences = context.getSharedPreferences("app_settings",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void putBoolean(Context context, String key, boolean value) {
		if (context == null)
			return;
		preferences = context.getSharedPreferences("app_settings",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static String getString(Context context, String key) {
		if (context == null)
			return null;
		preferences = context.getSharedPreferences("app_settings",
				Context.MODE_PRIVATE);
		return preferences.getString(key, "");
	}

	public static boolean getBoolean(Context context, String key) {
		if (context == null)
			return true;
		preferences = context.getSharedPreferences("app_settings",
				Context.MODE_PRIVATE);
		return preferences.getBoolean(key, true);
	}
}
