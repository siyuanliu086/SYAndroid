package com.android.common.baseui.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.utils.NetUtil;
import com.common.android.utilslibrary.R;

/**
 * @TiTle TitleBarView.java
 * @Package com.android.common.baseui.baseview
 * @Description 标题栏基础类
 * @Date 2016年6月12日
 * @Author siyuan 
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class TitleBarView extends RelativeLayout {
	private Context mContext;
	private View paddingView;
	private IconFontTextView leftImg;
	private IconFontTextView rightImg;
	private TextView tv_center;
	private RelativeLayout netWorkLayout;
	public RadioButton radioleft,radioright;
	private RadioGroup radioGroup ;
	private Button rightButton;
	public TitleBarView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.utilslib_layout_common_titlebar, this);
		paddingView = findViewById(R.id.title_padding_view);
		leftImg = (IconFontTextView) findViewById(R.id.title_img_left);
		rightImg = (IconFontTextView) findViewById(R.id.title_img_right);
		tv_center = (TextView) findViewById(R.id.title_name);
		netWorkLayout = (RelativeLayout) findViewById(R.id.main_network_layout);
		radioGroup=(RadioGroup) findViewById(R.id.titlebar_radiogroup);
		radioleft=(RadioButton) findViewById(R.id.radiobutton_left);
		radioright=(RadioButton) findViewById(R.id.radiobutton_right);
		rightButton =(Button) findViewById(R.id.titile_right_button);
		
		
		netWorkLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				NetUtil.checkNetwork((Activity) mContext);
			}
		});
	}

	public void setCommonTitle(int LeftVisibility, int centerVisibility,int rightVisibility) {
		radioGroup.setVisibility(GONE);
		leftImg.setVisibility(LeftVisibility);
		rightImg.setVisibility(rightVisibility);
		tv_center.setVisibility(centerVisibility);
	}

	public void setCommonTitle(int radioGroupVisibility,int LeftVisibility, int centerVisibility,int rightVisibility) {
		radioGroup.setVisibility(radioGroupVisibility);
		leftImg.setVisibility(LeftVisibility);
		rightImg.setVisibility(rightVisibility);
		tv_center.setVisibility(centerVisibility);

	}
	public void setCommonTitleButton(int LeftVisibility, int centerVisibility,int buttonVisibility) {
		radioGroup.setVisibility(GONE);
		leftImg.setVisibility(LeftVisibility);
		rightImg.setVisibility(GONE);
		tv_center.setVisibility(centerVisibility);
		rightButton.setVisibility(buttonVisibility);
	}

	public void setPaddingViewHeight(int heightPX) {
		RelativeLayout.LayoutParams padLayoutParams = (LayoutParams) paddingView.getLayoutParams();
		padLayoutParams.height = heightPX;
		paddingView.invalidate();
	}

	/**
	 * 设置顶部选项卡的文字描述
	 * @param left
	 * @param right
	 */
	public void setRadioButtonText(int left,int right){
		radioleft.setText(left);
		radioright.setText(right);
	}

	/**
	 * 设置右侧选项卡选中状态
	 * @param checked
	 */
	public void setRadioButtonChecked(boolean checked){
		radioright.setChecked(checked);
	}


	/**
	 * 设置左边按钮图片
	 * @param imageResId
	 */
	public void setLeftIconFontText(int textResId){
		leftImg.setText(textResId);
	}

	/**
	 * 设置右边按钮图片
	 * @param imageResId
	 */
	public void setRightIconFontText(int textResId){
		rightImg.setText(textResId);
	}

	/**
	 * 给点选按钮设置选择事件
	 * @param listener
	 */
	public void setSeclectListenser(android.widget.RadioGroup.OnCheckedChangeListener listener){
		radioGroup.setOnCheckedChangeListener(listener);
	}

	public void setNetWorkLayoutOnclickListener(OnClickListener listener) {
		netWorkLayout.setOnClickListener(listener);
	}

	/**
	 * 设置标题是否可见
	 * @param visibility
	 */
	public void setTitleVisibility(int visibility){
		tv_center.setVisibility(visibility);
	}

	/**
	 * 设置标题内容
	 * @param txtRes
	 */
	public void setTitleText(String txtRes) {
		tv_center.setText(txtRes);
	}

	/**
	 * 设置标题内容
	 * @param txtRes
	 */
	public void setTitleText(int txtRes) {
		tv_center.setText(txtRes);
	}

	/**
	 * 设置标题内容
	 * @param txtRes
	 */
	public TextView getTitleText() {
		return tv_center;
	}

	public void setTitleBackgroundColor(int colorValue) {
		findViewById(R.id.title_bar).setBackgroundColor(colorValue);
	}

	public void setTitleBackgroundDrawable(int resId) {
		findViewById(R.id.title_bar).setBackgroundResource(resId);
	}

	/**
	 * 给左边按钮设置监听事件
	 * @param listener
	 */
	public void setLeftOnClickListener(OnClickListener listener) {
		leftImg.setOnClickListener(listener);
	}

	/**
	 * 给右边按钮设置监听事件
	 * @param listener
	 */
	public void setRightImgOnclickListener(OnClickListener listener) {
		rightImg.setOnClickListener(listener);
	}


	public void setNetWorkState(boolean isNetON) {
		netWorkLayout.setVisibility(isNetON ? View.GONE : View.VISIBLE);
	}
	public void setRightButtonTextColor(int color){
		rightButton.setTextColor(color);
		
	}
	
	public void setRightButtonIsClick(boolean clickable){
		rightButton.setClickable(clickable);
	}
	
	public void setRightButtonOnclickListener(OnClickListener listener) {
		rightButton.setOnClickListener(listener);
	}
}
