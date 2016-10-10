package com.android.common.baseui.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.android.utilslibrary.R;

/**
 * @TiTle PickMediaPopupWindow.java
 * @Package com.iss.zhhblsnt.views
 * @Description 底部菜单
 * @Date 2016年4月26日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class BottomMenuPopu extends PopupWindow implements OnClickListener {
	private RelativeLayout outLayout;
	private TextView menu1Text, menu2Text, menu3Text, menu4Text, cancel;

	/**
	 * @param activity 发起的activity
	 * @param parent 父布局View，锚点
	 * @param filePath 拍照图片路径（如果要拍照）
	 */
	public BottomMenuPopu(Activity activity, String[] menuStr) {
		super(activity);
		View view = View.inflate(activity, R.layout.utils_layout_popup_menu, null);
		
		menu1Text = (TextView) view.findViewById(R.id.utils_popup_menu1);
		menu2Text = (TextView) view.findViewById(R.id.utils_popup_menu2);
		menu3Text = (TextView) view.findViewById(R.id.utils_popup_menu3);
		menu4Text = (TextView) view.findViewById(R.id.utils_popup_menu4);
		cancel = (TextView) view.findViewById(R.id.utils_popup_media_cancel);
		outLayout = (RelativeLayout)view.findViewById(R.id.utils_popup_media_layout);
		
		for(int i = 0; i < menuStr.length; i++) {
			String text = menuStr[i];
			if(i == 0) {
				menu1Text.setText(text);
			} else if(i == 1) {
				menu2Text.setText(text);
			} else if(i == 2) {
				menu3Text.setText(text);
			} else if(i == 3) {
				menu4Text.setText(text);
			} 
		}
		
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.utils_popuwindowstyle);
		// 实例化一个ColorDrawable颜色为半透明 
	    ColorDrawable dw = new ColorDrawable(activity.getResources().getColor(R.color.utils_transparent)); 
	    // 设置弹出窗体的背景 
	    setBackgroundDrawable(dw); 
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		update();
		setListener();
	}

	private void setListener() {
		menu1Text.setOnClickListener(this);
		menu2Text.setOnClickListener(this);
		menu3Text.setOnClickListener(this);
		menu4Text.setOnClickListener(this);
		cancel.setOnClickListener(this);
		outLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.utils_popup_menu1
				|| v.getId() == R.id.utils_popup_menu2
				|| v.getId() == R.id.utils_popup_menu3
				|| v.getId() == R.id.utils_popup_menu4) {
			if(listener != null) {
				listener.onMenuClick(v.getId());
			}
		} else {
			dismiss();
		}
	}
	
	public void dismissMenu() {
		dismiss();
	}

	public OnMenuClickListener listener;
	public void setOnMenuClickListener(OnMenuClickListener listener) {
		this.listener = listener;
	}
	public interface OnMenuClickListener {
		void onMenuClick(int vId);
	}
}
