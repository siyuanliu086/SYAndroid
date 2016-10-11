package com.android.common.utils;

import java.lang.reflect.Field;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author LuoJ
 * @date 2014-3-5
 * @package com.anhry.android.utils -- ViewUtil.java
 * @Description 组件工具类
 */
public class ViewUtil {

	public static void autoScrollBottom(TextView textView,final ScrollView scrollView){
		autoScroll(textView, scrollView, ScrollView.FOCUS_DOWN);
	}
	
	public static void autoScroll(TextView textView,final ScrollView scrollView,final int direction){
		textView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				scrollView.fullScroll(direction);
			}
		});
	}
	
	/**
	 * 设置dialog点击确定取消后是否关闭
	 * @param dialog
	 * @param isShow
	 * @return
	 */
	public static boolean setDialogCanCancel(DialogInterface dialog,boolean isShow){
		try {
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, isShow);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void enable(View... views){
		if (null==views||views.length==0) {
			return;
		}
		for (int i = 0; i < views.length; i++) {
			enable(views[i]);
		}
	}
	
	public static void disable(View... views){
		if (null==views||views.length==0) {
			return;
		}
		for (int i = 0; i < views.length; i++) {
			disable(views[i]);
		}
	}
	
	public static void enable(View view){
		setViewEnable(view, true);
	}
	
	public static void disable(View view){
		setViewEnable(view, false);
	}
	
	public static void setViewEnable(View view,boolean enable){
		if (null!=view) {
			view.setFocusable(enable);
			view.setEnabled(enable);
			view.setClickable(enable);
		}
	}
	
	//view 转bitmap  and by whj
	public static Bitmap convertViewToBitmap(View view) {
		 view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		 view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		 view.buildDrawingCache();
		 Bitmap bitmap = view.getDrawingCache();
		 return bitmap;
	}
	
	/***
	 * 给输入汉字的text加粗 and by whj
	 */
	public static void blodChineseText(TextView textView) {
		TextPaint tp = textView.getPaint();
		tp.setFakeBoldText(true);
	}
	
}


