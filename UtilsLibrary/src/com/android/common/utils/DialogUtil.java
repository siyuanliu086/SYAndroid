package com.android.common.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.common.android.utilslibrary.R;

/**
 * @TiTle DialogUtil.java
 * @Package com.iss.zhhb.monitor.util
 * @Description 用以封装对话框，暂未实现
 * @Date 2015年11月6日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class DialogUtil {
	private Context mContext;
	private AlertDialog mAlertDialog;
	
	/**
	 * 提示对话框，点击确定后消失
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public DialogUtil(Context context, String title, String message) {
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		// 设置date布局
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mAlertDialog.dismiss();
			}
		});
		mAlertDialog = builder.create();
	}
	
	/**
	 * 提示对话框，点击确定后执行监听
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public DialogUtil(Context context, String title, String message, 
			DialogInterface.OnClickListener positiveClickListener) {
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 设置date布局
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(android.R.string.ok, positiveClickListener);
		mAlertDialog = builder.create();
	}
	
	/**
	 * 提示对话框，点击确定后执行监听，点击取消后消失
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public DialogUtil(Context context, String title, String message, 
			DialogInterface.OnClickListener positiveClickListener,
			DialogInterface.OnClickListener negativeClickListener) {
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 设置date布局
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(android.R.string.ok, positiveClickListener);
		builder.setNegativeButton(android.R.string.cancel, negativeClickListener);
		mAlertDialog = builder.create();
	}
	
	/**
	 * 提示对话框，点击确定后执行监听，点击取消后消失
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public DialogUtil(Context context, String title, String message, 
			String positiveButtonText,
			String negativeButtonText,
			DialogInterface.OnClickListener positiveClickListener,
			DialogInterface.OnClickListener negativeClickListener) {
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 设置date布局
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveButtonText, positiveClickListener);
		builder.setNegativeButton(negativeButtonText, negativeClickListener);
		mAlertDialog = builder.create();
	}
	
	/**
	 * 自定义dialog
	 * @param context
	 * @param title
	 * @param message
	 * @param view
	 */
	@SuppressLint("NewApi")
	public DialogUtil(Context context, String title, String message,OnClickListener onClickListener,OnDismissListener onDismissListener) {
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// 设置date布局
		View view = LayoutInflater.from(context).inflate(R.layout.utilslib_layout_dialog, null);
		TextView titleTv = (TextView) view.findViewById(R.id.dialog_title_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.dialog_content_tv);
		titleTv.setText(title);
		contentTv.setText(message);
		builder.setView(view);
		view.setOnClickListener(onClickListener);
		builder.setOnDismissListener(onDismissListener);
		mAlertDialog = builder.create();
	}
	
	public void showDiaglog() {
		if(mAlertDialog != null) {
			mAlertDialog.show();
		}
	}
	
	public void dismissDialog() {
		if(mAlertDialog != null) {
			mAlertDialog.dismiss();
		}
	}
	
}
