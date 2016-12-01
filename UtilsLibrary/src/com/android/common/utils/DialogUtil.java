package com.android.common.utils;

import android.app.AlertDialog;
import android.content.Context;
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
	private View view;
	private AlertDialog mAlertDialog;
	
	public DialogUtil(Context context) {
		this.mContext = context;
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
		// 设置布局
		view = LayoutInflater.from(context).inflate(R.layout.utilslib_layout_dialog, null);
		builder.setView(view);
		mAlertDialog = builder.create();
	}
	
	/**
	 * 提示对话框，点击确定后消失
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public void setDialog(String title, String message) {
		TextView titleTv = (TextView) view.findViewById(R.id.dialog_title_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.dialog_content_tv);
		TextView okTv = (TextView) view.findViewById(R.id.dialog_ok_tv);
		titleTv.setText(title);
		contentTv.setText(message);
		okTv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAlertDialog.dismiss();
			}
		});
	}
	
	/**
	 * 提示对话框，点击确定后执行监听
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public void setDialog(String title, String message, View.OnClickListener onClickListener) {
		TextView titleTv = (TextView) view.findViewById(R.id.dialog_title_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.dialog_content_tv);
		TextView okTv = (TextView) view.findViewById(R.id.dialog_ok_tv);
		titleTv.setText(title);
		contentTv.setText(message);
		okTv.setOnClickListener(onClickListener);
	}
	
	/**
	 * 提示对话框，点击确定后执行监听，点击取消后消失
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public void setDialog(String title, String message, View.OnClickListener onOkListener, 
			View.OnClickListener onCancelListener) {
		// 设置布局
		TextView titleTv = (TextView) view.findViewById(R.id.dialog_title_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.dialog_content_tv);
		TextView okTv = (TextView) view.findViewById(R.id.dialog_ok_tv);
		TextView cancelTv = (TextView) view.findViewById(R.id.dialog_cancel_tv);
		view.findViewById(R.id.dialog_cancel_layout).setVisibility(View.VISIBLE);
		titleTv.setText(title);
		contentTv.setText(message);
		okTv.setOnClickListener(onOkListener);
		cancelTv.setOnClickListener(onCancelListener);
	}
	
	/**
	 * 提示对话框，点击确定后执行监听，点击取消后消失
	 * @param context
	 * @param title
	 * @param message
	 * @param positiveListener
	 */
	public void setDialog(String title, String message, 
			String positiveButtonText,
			String negativeButtonText,
			View.OnClickListener onOkListener,
			View.OnClickListener onCancelListener) {
		TextView titleTv = (TextView) view.findViewById(R.id.dialog_title_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.dialog_content_tv);
		TextView okTv = (TextView) view.findViewById(R.id.dialog_ok_tv);
		TextView cancelTv = (TextView) view.findViewById(R.id.dialog_cancel_tv);
		view.findViewById(R.id.dialog_cancel_layout).setVisibility(View.VISIBLE);
		titleTv.setText(title);
		contentTv.setText(message);
		okTv.setText(positiveButtonText);
		cancelTv.setText(negativeButtonText);
		okTv.setOnClickListener(onOkListener);
		cancelTv.setOnClickListener(onCancelListener);
	}
	
	/**
	 * 自定义dialog
	 * @param context
	 * @param title
	 * @param message
	 * @param view
	 */
	public void setDialog(String title, String message, OnClickListener onClickListener,OnDismissListener onDismissListener) {
		TextView titleTv = (TextView) view.findViewById(R.id.dialog_title_tv);
		TextView contentTv = (TextView) view.findViewById(R.id.dialog_content_tv);
		titleTv.setText(title);
		contentTv.setText(message);
		view.setOnClickListener(onClickListener);
		mAlertDialog.setOnDismissListener(onDismissListener);
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
