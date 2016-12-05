package com.android.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.android.utilslibrary.R;

/**
 * @TiTle ToastUtil.java
 * @Package com.android.common.utils
 * @Description Toast 工具
 * @Date 2016年12月5日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-12-05
 * @Company ISoftStone ZHHB
 */
public class ToastUtil {

	/**
	 * 自定义布局Toast
	 * @param context
	 * @param msg
	 */
	public static void showCustomToast(Context context, String msg) {
		Context appContext = context.getApplicationContext();
		Toast toast = new Toast(appContext);
		LayoutInflater inflater = LayoutInflater.from(appContext);
		View view = inflater.inflate(R.layout.utilslib_layout_toastview, null);
		TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
		tv_message.setText(msg);
		// 设置Toast的位置
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		// 显示自定义的View
		toast.setView(view);
		toast.show();
	}


	/**
	 * 根据资源ID进行吐司提示
	 * @param context
	 * @param id
	 */
	public static void showCustomToast(Context context, int resId) {
		if (context == null)
			return;
		String msg = context.getResources().getString(resId);
		showCustomToast(context, msg);
	}

	public static void showShortToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(Context context, int message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, int message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}
}
