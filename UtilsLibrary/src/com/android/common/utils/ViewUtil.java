package com.android.common.utils;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @TiTle ViewUtil.java
 * @Package com.android.common.utils
 * @Description View工具 <br>
 * 视图工具三层级 ：应用级{@link AppUtil}、活动级{@link ActivityUtil}、视图级{@link ViewUtil}
 * @Date 2016年12月5日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class ViewUtil {

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
}


