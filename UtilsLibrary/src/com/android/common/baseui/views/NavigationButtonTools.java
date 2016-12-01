package com.android.common.baseui.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.utils.AppUtils;

/**
 * @TiTle NavigationButtonTools.java
 * @Package com.android.common.baseui.views
 * @Description 底部导航栏生成器
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 * 
 FG.)
NavigationButtonTools navigationButtons = new NavigationButtonTools(mContext);
navigationButtons.setNavigationButtonImages(menuButtonLayout, new int[][]{
		{R.string.text_home, R.drawable.main_bottom_home, R.drawable.main_bottom_home_selected},
		{R.string.text_map, R.drawable.main_bottom_map, R.drawable.main_bottom_map_selected},
		{R.string.text_rank, R.drawable.main_bottom_rank, R.drawable.main_bottom_rank_selected},
		{R.string.text_statistic, R.drawable.main_bottom_duibi, R.drawable.main_bottom_duibi_selected},
		{R.string.text_more, R.drawable.main_bottom_more, R.drawable.main_bottom_more_selected}
});
navigationButtons.setOnMenuSelecedListener(new NavigationButtonTools.OnMenuSelecedListener() {
	
	@Override
	public void onMenuSeleced(int index) {
		onMenuClick(index);
	}
});
 */
public class NavigationButtonTools implements OnClickListener{
	private final int SELECTEDCOLOR = 0xff00842f;
	private final int NORMALCOLOR = 0xff999999;
	private Context mContext;
	private int ID_OFFSET = 0x12;
	
	public NavigationButtonTools(Context context) {
		mContext = context;
	}
	
	private int[][] drawableResId;
	
	/**
	 * 效果图[0] 常态 [1] 按下（选中）
	 * @param drawableResId
	 */
	public void setNavigationButtonImages(LinearLayout menuLayout, int[][] drawableResId) {
		this.drawableResId = drawableResId;
		menuLayout.removeAllViews();
		
		int height24 = AppUtils.dip2px(mContext, 24);
		int marginValue = AppUtils.dip2px(mContext, 10);
		for (int i = 0; i < drawableResId.length; i++) {
			LinearLayout itemLayout = new LinearLayout(mContext);
			itemLayout.setWeightSum(1);
			itemLayout.setOrientation(LinearLayout.VERTICAL);
			itemLayout.setGravity(Gravity.CENTER);
			
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(drawableResId[i][1]);
			imageView.setScaleType(ScaleType.FIT_XY);
			itemLayout.addView(imageView, new LinearLayout.LayoutParams(height24, height24));
			
			TextView menuText = new TextView(mContext);
			menuText.setTextColor(0xff333333);
			menuText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			menuText.setText(drawableResId[i][0]);
			menuText.setTextColor(0xff888888);
			menuText.setGravity(Gravity.CENTER);
			itemLayout.addView(menuText);
			
			menuLayout.addView(itemLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
			
			LinearLayout.LayoutParams menuLayoutParams = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
			menuLayoutParams.leftMargin = marginValue;
			menuLayoutParams.rightMargin = marginValue;
			menuLayoutParams.topMargin = marginValue / 2;
			menuLayoutParams.bottomMargin = marginValue / 2;
			
			itemLayout.setId(ID_OFFSET + i);
			itemLayout.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		LinearLayout menuLayout = (LinearLayout) v.getParent();
		for(int i = 0; i < menuLayout.getChildCount(); i ++) {
			LinearLayout menuItem = (LinearLayout) menuLayout.getChildAt(i);
			if(v.getId() - ID_OFFSET == i) {
				((ImageView)menuItem.getChildAt(0)).setImageResource(drawableResId[i][2]);
				((TextView)menuItem.getChildAt(1)).setTextColor(SELECTEDCOLOR);
			} else {
				((ImageView)menuItem.getChildAt(0)).setImageResource(drawableResId[i][1]);
				((TextView)menuItem.getChildAt(1)).setTextColor(NORMALCOLOR);
			}
		}
		
		if(menuSelecedListener != null) {
			menuSelecedListener.onMenuSeleced(v.getId() - ID_OFFSET);
		}
	}
		
	private OnMenuSelecedListener menuSelecedListener;

	
	public void setOnMenuSelecedListener(OnMenuSelecedListener listener) {
		this.menuSelecedListener = listener;
	}
	
	public interface OnMenuSelecedListener {
		void onMenuSeleced(int index);
	}
		
	
}
