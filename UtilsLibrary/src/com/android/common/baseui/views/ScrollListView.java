package com.android.common.baseui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @TiTle ScrollListView.java
 * @Package com.android.common.baseui.views
 * @Description 解决ScrollView嵌套listview不显示的问题
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class ScrollListView extends ListView {

	public ScrollListView(Context context) {
		super(context);
	}

	public ScrollListView( Context context,AttributeSet attrs){
		super(context, attrs);
	}
	public ScrollListView( Context context,AttributeSet attrs, int defStyle){
		super(context, attrs,defStyle);
	}
	 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	        MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	    }
}
