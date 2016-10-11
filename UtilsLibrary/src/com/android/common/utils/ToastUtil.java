package com.android.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.android.utilslibrary.R;

/**
 * @author zhangxy
 * @date 2014-10-10
 * @Description 显示对话框
 */
public class ToastUtil {

	/**
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToast(Context context, String msg) {
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
	public static void showToast(Context context, int resId) {
		if (context == null)
			return;
		String msg = context.getResources().getString(resId);
		showToast(context, msg);
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
