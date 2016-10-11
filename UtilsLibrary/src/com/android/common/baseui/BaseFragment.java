package com.android.common.baseui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.common.baseui.views.NoDataLoadingView;
import com.android.common.baseui.views.TitleBarView;
import com.android.common.utils.ActivityUtils;
import com.common.android.utilslibrary.R;

/**
 * fragemnt基类
 * 
 * @author HJWANGK
 *
 */
public abstract class BaseFragment extends Fragment {
	/** 上下文 */
	protected Context mContext;
	/** 内容布局 */
	protected LinearLayout content;
	
	/**网络状态，子类使用*/
	protected boolean netWorkState;
	
	protected TitleBarView mTitleBarView;
    protected NoDataLoadingView baseLoading;
    protected InputMethodManager inputMethodManager;

	/**
	 * 自定义获取string方法
	 * 
	 * @param id
	 * @return
	 */
	protected final String getStringSelf(int id) {
		return mContext.getString(id);
	}

	/**
	 * 自定义获取资源方法
	 * 
	 * @return
	 */
	protected final Resources getResourcesSelf() {
		return mContext.getResources();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.utilslib_fragment_common_base, null);
		content = (LinearLayout) rootView.findViewById(R.id.basefragment_content);
		mTitleBarView = (TitleBarView) rootView.findViewById(R.id.basefragment_title_bar);
	    baseLoading = (NoDataLoadingView) rootView.findViewById(R.id.basefragment_nodata_loading);
		content.addView(onCreateView(inflater, savedInstanceState));
		
		initView();
        setUpView();
        initData();
		return rootView;
	}
	
	protected abstract View onCreateView(LayoutInflater mInflater, Bundle savedInstanceState);

	/** 初始化界面 */
	protected abstract void initView();

	/** 初始化界面展示的数据 */
	protected abstract void initData();
	
    /**
     * 设置属性，监听等
     */
    protected abstract void setUpView();
    
    /**
     * 页面显示时回调
     */
    public abstract void onFragemntShow();

    /**
     * 网络状态回调
     * @param isOn
     */
	public abstract void setNetWorkState(boolean isOn);

	/**
	 * 隐藏键盘
	 */
	protected void hiddenInput() {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getView() != null) {
			imm.hideSoftInputFromWindow(getView().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	public void onLoading() {
		if(baseLoading != null) {
			baseLoading.setText(R.string.utils_common_loading);
			baseLoading.startLoading();
		}
	}
	
	public void onLoading(String info) {
		if(baseLoading != null) {
			baseLoading.setText("");
			baseLoading.startLoading();
		}
	}
	
	public void onLoading(int infoRes) {
		if(baseLoading != null) {
			if(infoRes != 0) {
				baseLoading.setText(infoRes);
			} else {
				baseLoading.setText(R.string.utils_common_loading);
			}
			baseLoading.startLoading();
		}
	}

	public void onLoadingCompleted() {
		if(baseLoading != null){
			baseLoading.stopLoading();
		}
	}
	
	/**
	 * @param title 设置页面标题
	 */
	protected void setFunctionTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			mTitleBarView.setTitleVisibility(View.GONE);
		} else {
			mTitleBarView.setTitleVisibility(View.VISIBLE);
			mTitleBarView.setTitleText(title);
		}
	}
	
	protected int getTitleBarColor(){
		return getActivity().getResources().getColor(R.color.utilslib_theme_color_green);
	}
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	protected void setTitleStatusPadding(TitleBarView baseTitleBar) {
		// 如果版本高于4.4，支持标题栏沉浸效果
		int statusBarHeight = 0;
		boolean isStatusBarTransparent = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { 
			Rect outRect = new Rect();  
			getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect); 
			statusBarHeight = outRect.top;
			getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			isStatusBarTransparent = true;
			
			statusBarHeight = ActivityUtils.getStatusHeight(getActivity());
		}
		if(isStatusBarTransparent) {
			baseTitleBar.setPaddingViewHeight((int)(statusBarHeight * 0.8));
		}
	}
	
}
