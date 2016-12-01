package com.android.common.baseui.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.utils.AppUtils;
import com.common.android.utilslibrary.R;

/**
 * @TiTle NoDataLoadingView.java
 * @Package com.android.common.baseui.views
 * @Description 加载框，包括无数据提示、点击重新加载等封装
 * @Date 2016年10月11日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class CustomLoadingView extends RelativeLayout{
    private Context mContext;
    private ImageView loadingImage; 
    private IconFontTextView noDataImage;
    private TextView mTextView, noDataText;
    private RelativeLayout noDataViewLayout, loadingView;
    
    private boolean isLoading = false;
    
	public CustomLoadingView(Context context) {
		super(context);
		mContext=context;
		initView();
	}
	
	public CustomLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initView();
	}
	
	private void initView(){
		LayoutInflater.from(mContext).inflate(R.layout.utilslib_layout_no_data_loading, this);
		//Loading
		loadingView = (RelativeLayout) findViewById(R.id.loading_view);
		loadingImage = (ImageView) findViewById(R.id.loading_image);
				
		mTextView=(TextView) findViewById(R.id.loading_textview);
		//无数据提示
		noDataViewLayout = (RelativeLayout) findViewById(R.id.no_data_view);
		noDataImage = (IconFontTextView) findViewById(R.id.no_data_image);
		noDataText = (TextView) findViewById(R.id.no_data_text);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(isLoading) {
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		if(isLoading) {
			return true;
		}
		return super.onGenericMotionEvent(event);
	}
	
	public void setImgOnClickListener(OnClickListener listener){
		loadingImage.setOnClickListener(listener);
	}
	
	public void setText(int txtRes){
		mTextView.setText(txtRes);
	}
	
	public void setText(String info){
		mTextView.setText(info);
	}

	public void startLoading() {
		loadingView.setVisibility(View.VISIBLE);
		noDataViewLayout.setVisibility(View.GONE);
		// 加载动画  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.utilslib_basetitle_loading_animation);  
        // 使用ImageView显示动画  
        loadingImage.startAnimation(hyperspaceJumpAnimation);  
        isLoading = true;
	}
	
	public void stopLoading() {
		isLoading = false;
		loadingView.setVisibility(View.GONE);
	}
	/**
	 * 加载数据正常,有数据,隐藏提示布局
	 */
	public void loadSuccess() {
		isLoading = false;
		stopLoading();
		noDataViewLayout.setVisibility(View.GONE);
	}
	
	public void onLoginLoading() {
		View emptyView = findViewById(R.id.loading_emptyview);
		RelativeLayout.LayoutParams emptyLayoutParams = (LayoutParams) emptyView.getLayoutParams();
		emptyLayoutParams.height = AppUtils.dip2px(mContext, 60);
		
		// 设置样式，开始加载
		setText("");
		startLoading();
	}
	
	/**
	 * 加载数据正常,无数据,显示提示布局
	 */
	public void loadFailed(int iconFontNOdataRes, String info) {
		isLoading = false;
		stopLoading();
		noDataViewLayout.setVisibility(View.VISIBLE);
		noDataImage.setText(iconFontNOdataRes);
		if(!TextUtils.isEmpty(info)) {			
			noDataText.setText(info);
		} else {
			noDataText.setText(R.string.utilslib_common_nodata);
		}
	}
	
	/**
	 * 加载数据正常,无数据,显示提示布局
	 */
	public void loadFailed(String info) {
		isLoading = false;
		stopLoading();
		noDataViewLayout.setVisibility(View.VISIBLE);
		noDataImage.setText("");
		if(TextUtils.isEmpty(info)) {
			noDataText.setText(info);
		} else {			
			noDataText.setText(R.string.utilslib_common_nodata);
		}
	}
	
	/**
	 * 加载数据失败，点击重新加载
	 */
	public void dataReload(final Context mContext, int iconFontNOdataRes, String info){
		stopLoading();
		isLoading = false;
		noDataViewLayout.setVisibility(View.VISIBLE);
		noDataImage.setText(iconFontNOdataRes);// 加载失败，（暂无数据）
		if(!TextUtils.isEmpty(info)) {			
			noDataText.setText(info);
		} else {
			noDataText.setText(R.string.utilslib_common_data_reload);			
		}
		noDataText.setTextColor(getResources().getColor(R.color.utilslib_deep_gray));
		noDataViewLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(onDataRefreshListener != null) {
					isLoading = true;
					noDataText.setText(R.string.utilslib_common_data_reloading);
					noDataText.setTextColor(getResources().getColor(R.color.utilslib_deep_gray));
					onDataRefreshListener.onDataRefresh();
				} 
			}
		});
	}
	/**
	 * 加载数据失败，点击重新加载
	 */
	public void dataReload(final Context mContext, int iconFontNOdataRes, int info){
		String infoStr = getResources().getString(info);
		dataReload(mContext, iconFontNOdataRes, infoStr);
	}
	
	/**
	 * 加载数据失败，点击重新加载
	 */
	public void dataReload(final Context mContext){
		stopLoading();
		isLoading = false;
		noDataViewLayout.setVisibility(View.VISIBLE);
		noDataImage.setText("");// 加载失败，（暂无数据）
		noDataText.setText(R.string.utilslib_common_data_reload);
		noDataText.setTextColor(getResources().getColor(R.color.utilslib_deep_gray));
		noDataViewLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(onDataRefreshListener != null) {
					isLoading = true;
					noDataText.setText(R.string.utilslib_common_data_reloading);
					noDataText.setTextColor(getResources().getColor(R.color.utilslib_deep_gray));
					onDataRefreshListener.onDataRefresh();
				} 
			}
		});
	}

	public interface OnDataRefreshListener{
		void onDataRefresh();
	}
	
	private OnDataRefreshListener onDataRefreshListener;
	public void setOnNoDataLoadingRefreshListener(OnDataRefreshListener listener) {
		onDataRefreshListener = listener;
	}
	
}
