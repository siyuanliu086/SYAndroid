package com.android.common.baseui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.android.common.baseui.views.NoDataLoadingView;
import com.android.common.baseui.views.NumberProgressBar;
import com.android.common.baseui.views.TitleBarView;
import com.android.common.utils.ActivityUtils;
import com.android.common.utils.NetUtil;
import com.common.android.utilslibrary.R;

/**
 * @TiTle BaseActivity.java
 * @Package com.iss.zhhb.monitor.activity
 * @Description 基本布局：提供： 1、显示Loading 2、显示顶部菜单 3、监测网络
 * @Date 2015年10月9日
 * @Author siyuan
 * @Refactor
 * @Company ISoftStone ZHHB
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
	/** 系统的标题view */
	public TitleBarView baseTitleBar;
	/** 系统框架，内容填充布局*/
	public LinearLayout mainContentLayout;
	/** 布局填充器*/
	public LayoutInflater mInflater;
	
	/**网络状态，子类使用*/
	protected boolean netWorkState;
	/**加载等待框*/
	public NoDataLoadingView baseLoading;
	
	/**加载进度条*/
	public NumberProgressBar progressBarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.utilslib_activity_common_base); 
		
		baseTitleBar = (TitleBarView) findViewById(R.id.base_title_bar);
		setTitleStatusPadding(baseTitleBar);
		
		baseLoading = (NoDataLoadingView) findViewById(R.id.base_main_nodata_loading);
		mainContentLayout = (LinearLayout) findViewById(R.id.base_main_content);
		
		mInflater = LayoutInflater.from(this);
		
		initView();
		setUpView();
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(wifi_Receiver, filter);
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	protected void setTitleStatusPadding(TitleBarView baseTitleBar) {
		// 如果版本高于4.4，支持标题栏沉浸效果
		int statusBarHeight = 0;
		boolean isStatusBarTransparent = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { 
			Rect outRect = new Rect();  
			getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect); 
			statusBarHeight = outRect.top;
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			isStatusBarTransparent = true;
			
			statusBarHeight = ActivityUtils.getStatusHeight(this);
		}
		if(isStatusBarTransparent) {
			baseTitleBar.setPaddingViewHeight((int)(statusBarHeight * 0.8));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(wifi_Receiver);
	}

	/**
	 * 网络监听
	 */
	android.content.BroadcastReceiver wifi_Receiver = new android.content.BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			netWorkState = NetUtil.isNetworkAvailable(context);
			onNetStateChanged(netWorkState);
		}
	};

	/**
	 * @param title 设置页面标题
	 */
	protected void setFunctionTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			baseTitleBar.setTitleVisibility(View.GONE);
		} else {
			baseTitleBar.setTitleVisibility(View.VISIBLE);
			baseTitleBar.setTitleText(title);
		}
	}
	
	protected void onLoading(int infoRes) {
		if(baseLoading != null) {
			if(infoRes != 0) {
				baseLoading.setText(infoRes);
			} else {
				baseLoading.setText(R.string.utils_common_loading);
			}
			baseLoading.startLoading();
		}
	}
	
	protected void onLoading() {
		if(baseLoading != null) {
			baseLoading.setText(R.string.utils_common_loading);
			baseLoading.startLoading();
		}
	}
	
	protected void onLoading(String info) {
		if(baseLoading != null) {
			baseLoading.setText(info);
			baseLoading.startLoading();
		}
	}

	/**
	 * 关闭系统等待框
	 */
	protected void onLoadingCompleted() {
		if(baseLoading != null){
			baseLoading.stopLoading();
		}
	}
	
	/** 初始化界面 */
	protected abstract void initView();

	/** 初始化界面展示的数据 */
	protected abstract void initData();
	
    /** 设置属性，监听等  */
    protected abstract void setUpView();

    /**
     * 网络状态回调
     * @param isOn
     */
    protected abstract void onNetStateChanged(boolean isOn);
}

