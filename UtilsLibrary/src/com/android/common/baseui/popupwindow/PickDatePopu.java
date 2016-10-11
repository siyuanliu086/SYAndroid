package com.android.common.baseui.popupwindow;

import java.util.Date;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.android.utilslibrary.R;

/**
 * @TiTle PickDateTimePopu.java
 * @Package com.iss.zhhblsnt.views
 * @Description 选择日期时间
 * @Date 2016年4月25日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class PickDatePopu extends PopupWindow {
    private Context mContext;
    private View rootView;
    /**
     * 关闭按钮
     */
    private TextView closeTv,sureTv;
    private DatePicker datePicker;
    
	public PickDatePopu(Context context, OnDatePickedListener listener) {
		super(context);
		this.mContext = context;
		this.listener = listener;
		
		initView();
	}
	
	public PickDatePopu(Context context, OnDatePickedListener listener, Date initDate) {
		super(context);
		this.mContext = context;
		this.listener = listener;
		initView();
	}
	
	@Override
	public void setBackgroundDrawable(Drawable background) {
		background.setAlpha(20);
		super.setBackgroundDrawable(background);
	}
	
	private View initView() {
		int[] screen = getScreenSize(mContext);
		rootView = LayoutInflater.from(mContext).inflate(R.layout.utilslib_layout_popup_date, null);
		closeTv = (TextView) rootView.findViewById(R.id.utils_popup_close);
		sureTv = (TextView) rootView.findViewById(R.id.utils_popup_sure);
		
		// 设置日期简略显示 否则详细显示 包括:星期周
		datePicker = (DatePicker) rootView.findViewById(R.id.utils_popup_date_picker);
		datePicker.setCameraDistance(0.5f);
		
		LinearLayout dpContainer = (LinearLayout)datePicker.getChildAt(0);
		LinearLayout dpSpinner = (LinearLayout)dpContainer.getChildAt(0); // mChildCount == 3;
		
		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(screen[0]/8, LinearLayout.LayoutParams.WRAP_CONTENT); 
		params1.leftMargin = 10;  
        params1.rightMargin = 10;  
        params1.topMargin = 10;
        params1.bottomMargin = 10;
		for(int i = 0; i < dpSpinner.getChildCount(); i ++) {  
			if(dpSpinner.getChildAt(i) instanceof NumberPicker) {
				dpSpinner.getChildAt(i).setLayoutParams(params1);  
			}
		}
		
		/* 设置弹出窗口特征 */
	    // 设置视图 
	    this.setContentView(rootView); 
	    // 设置弹出窗体的宽和高 
	    this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);//screen[1]); 
	    this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT); 
	    // 设置弹出窗体可点击 
	    this.setFocusable(true); 
	    // 实例化一个ColorDrawable颜色为半透明 
	    ColorDrawable dw = new ColorDrawable(mContext.getResources().getColor(R.color.utilslib_transparent)); 
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
	
//	private Date translateDateTime(String dateTime) {
//		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Date time = null;
//		try {
//			time = formatDate.parse(dateTime);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return time;
//	}
	
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
				StringBuffer sb = new StringBuffer();
				sb.append(String.format("%d-%02d-%02d",
						datePicker.getYear(),
						datePicker.getMonth() + 1,
						datePicker.getDayOfMonth()));
				
//				if(translateDateTime(sb.toString()).compareTo(new Date()) < 0) {
//					Toast.makeText(mContext, "请选择正确的时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
    			if(listener != null) {
    				listener.onDateTimePicked(sb.toString());
				}
    			
    			dismiss();
    		}
    	});
    }
    
    private String formateTimeStr(int hour, int minute) {
		StringBuffer sb = new StringBuffer("");
		return sb.append(hour < 10 ? "0" + hour : hour).append(":")
				.append(minute < 10 ? "0" + minute : minute).toString();
	}
    
    private OnDatePickedListener listener;
    public interface OnDatePickedListener{
    	void onDateTimePicked(String datetime);
    }
}
