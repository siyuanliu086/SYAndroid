package com.android.common.baseui.popupwindow;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.baseui.views.WheelView;
import com.common.android.utilslibrary.R;

/**
 * @TiTle SingleWheelPopu.java
 * @Package com.iss.zhhblsnt.views
 * @Description 单个Wheel 的PopupWindow
 * @Date 2016年4月25日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class SingleWheelPopu extends PopupWindow {
    private Context mContext;
    private View rootView;
    private int position;
    /**
     * 关闭按钮
     */
    private TextView closeTv,sureTv;
    /**
     * 齿轮 父类，子类
     */
    private WheelView mWheelView;
    
    private List<String> dataList;

	public SingleWheelPopu(Context context, OnItemSelectedListener listener, List<String> dataList) {
		super(context);
		this.mContext = context;
		this.listener = listener;
		this.dataList = dataList;
		
		initView();
		initData();
		
		if(listener != null)
    		listener.onItemSelectedListener(0);
	}
	
	@Override
	public void setBackgroundDrawable(Drawable background) {
		background.setAlpha(20);
		super.setBackgroundDrawable(background);
	}
	
	private View initView(){
		int[] screen = getScreenSize(mContext);
		rootView = LayoutInflater.from(mContext).inflate(R.layout.utils_layout_popup_singleselect, null);
		closeTv = (TextView) rootView.findViewById(R.id.utils_popup_close);
		sureTv = (TextView) rootView.findViewById(R.id.utils_popup_sure);
		mWheelView = (WheelView) rootView.findViewById(R.id.utils_popup_single_wheelview);
		/* 设置弹出窗口特征 */
	    // 设置视图 
	    this.setContentView(rootView); 
	    // 设置弹出窗体的宽和高 
	    this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);//screen[1]); 
	    this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT); 
	  
	    // 设置弹出窗体可点击 
	    this.setFocusable(true); 
	  
	    // 实例化一个ColorDrawable颜色为半透明 
	    ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.utils_half_transparent)); 
	    // 设置弹出窗体的背景 
	    this.setBackgroundDrawable(dw); 
	  
	    // 设置弹出窗体显示时的动画，从底部向上弹出 
	    this.setAnimationStyle(R.style.utils_popuwindowstyle); 
	    setListener();
		return rootView;
	}
	
	private int[] getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return new int[]{wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight()};
	}
	
	private void initData() {
		mWheelView.setOffset(2);
		mWheelView.setItems(dataList);
		mWheelView.setSeletion(0);
	}
	
    private void setListener(){
    	rootView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
    	closeTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				
			}
		});
    	sureTv.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			if(listener != null)
    				if (position > 0) {
    					listener.onItemSelectedListener(position - 2);	
					}else{
						listener.onItemSelectedListener(position);	
					}
            		
    			    dismiss();
    		}
    	});
    	mWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
            	position = selectedIndex;
            	
            }
        });
    }
    
    private OnItemSelectedListener listener;
    public interface OnItemSelectedListener{
    	void onItemSelectedListener(int selectedIndex);
    }
}
