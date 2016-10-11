package com.android.common.baseui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @TiTle IconFontTextView.java
 * @Package com.android.common.baseui.views
 * @Description 使用字体图片
 * @Date 2016年10月11日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class IconFontTextView extends TextView{

	private Context mContext;
	
	public IconFontTextView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public IconFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}
	
	private void initView() {
		Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), "iconfont.ttf");
		setTypeface(iconfont);
	}

}
