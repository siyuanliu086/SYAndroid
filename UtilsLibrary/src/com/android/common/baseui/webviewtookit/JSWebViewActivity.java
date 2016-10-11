package com.android.common.baseui.webviewtookit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.common.baseui.BaseActivity;
import com.android.common.baseui.webviewtookit.JSWebView.WebViewTitle;
import com.common.android.utilslibrary.R;

/**
 * @TiTle JSWebViewActivity.java
 * @Package com.android.common.baseui.webviewtookit
 * @Description JS的HTML页面展示
 * @Date 2016年6月13日
 * @Author hjwangk
 * @Refactor
 * @Company ISoftStone ZHHB
 */
public abstract class JSWebViewActivity extends BaseActivity implements WebViewTitle{
	public Context mContext = this;
	/** web_url参数名称 **/
	public final static String WEBVIEW_URL = "webview_url";
	/** web_title参数名称 **/
	public final static String WEBVIEW_TITLE = "webview_title";
	/** 界面横竖屏 **/
	public final static String WEBVIEW_SCREEN = "webview_screen";
	
	protected JSWebView mWebView;
	protected View rootView;
	private String mHtml;

	@Override
	protected void initView() {
		rootView = LayoutInflater.from(this).inflate(R.layout.utilslib_activity_jswebview, null);
		mainContentLayout.addView(rootView);

		baseTitleBar.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE);
		mWebView = (JSWebView) rootView.findViewById(R.id.jsview_jv);
		String mTitle = null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mTitle = bundle.getString(WEBVIEW_TITLE);
			mHtml = bundle.getString(WEBVIEW_URL);
			int screen = bundle.getInt(WEBVIEW_SCREEN);
			if (screen == 0) {
				// 竖屏
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		}

		if (null != mTitle) {
			setFunctionTitle(mTitle);
			mWebView.setCurrentTitle(mTitle);
		} else {
			setFunctionTitle("");
		}
		
		mWebView.loadHtml(mHtml);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWebView.onResume();
	}

	@Override
	protected void onPause() {
		mWebView.onPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		mWebView.onDestroy();
		super.onDestroy();
	}

	@Override
	protected void initData() {
		mWebView.setWebViewTitle(this);
		mWebView.setJavaScriptInterface();
	}

	@Override
	protected void onNetStateChanged(boolean isOn) {
		baseTitleBar.setNetWorkState(isOn);
	}

	@Override
	protected void setUpView() {
		baseTitleBar.setLeftOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void setCuttentTitle(String webViewTitle) {
		if (null != webViewTitle) {
			setFunctionTitle(webViewTitle);
		} else {
			setFunctionTitle("");
		}
	}

	@Override
	public void gotoActivity(Intent intent) {
		
	}

	@Override
	public ServiceNeed getServerNeedClass() {
		return getMyServerNeedClass();
	}
	
	public abstract ServiceNeed getMyServerNeedClass();

}
