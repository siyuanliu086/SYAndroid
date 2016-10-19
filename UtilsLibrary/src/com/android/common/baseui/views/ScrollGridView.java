package com.android.common.baseui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @TiTle ScrollGridView.java
 * @Package com.android.common.baseui.views
 * @Description 解决与scrollview嵌套GridView不显示的问题
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class ScrollGridView extends GridView {
	public ScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollGridView(Context context) {
		super(context);
	}

	public ScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
