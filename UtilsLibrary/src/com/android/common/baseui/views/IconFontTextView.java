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
	
	/**
	 * 设置iconfont文件
	 * @param iconfontFileName iconfont files in assets
	 */
	public void setIconfontName(String iconfontFileName) {
		Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), iconfontFileName);
		setTypeface(iconfont);
	}
	
	private void initView() {
		Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), "utilslib_iconfont.ttf");
		setTypeface(iconfont);
	}

}
