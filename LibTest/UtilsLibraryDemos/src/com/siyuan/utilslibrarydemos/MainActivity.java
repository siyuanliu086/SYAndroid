package com.siyuan.utilslibrarydemos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.common.baseui.alertview.AlertView;
import com.android.common.baseui.progressbar.CircleProgressBar;
import com.android.common.baseui.views.BadgeView;
import com.android.common.baseui.views.SearchView;
import com.android.common.utils.DialogUtil;
import com.android.common.utils.SoftKeyBoardListener;
import com.android.common.utils.toast.ToastUtil;
import com.siyuan.utilslibrarydemos.canlendar.CanlendarMainActivity;

public class MainActivity extends Activity {
	private Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Utilslib_ActivityTheme);// set theme used before setContentView()
		setContentView(R.layout.activity_main);

		CircleProgressBar circleProgressBar = (CircleProgressBar) findViewById(R.id.pw_spinner);
		circleProgressBar.setProgress(120);
		circleProgressBar.setText("AQI 120");
		
		SearchView searchView = (SearchView) findViewById(R.id.search_bar);
		BadgeView badge = new BadgeView(this, searchView);
		badge.setText("1");
		badge.show();
		
		// 监听键盘现实和隐藏
		SoftKeyBoardListener.setListener(MainActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener(){
//			DialogUtil dialogUtil = null;

			@Override
			public void keyBoardShow(int height) {
				DialogUtil dialogUtil = new DialogUtil(mContext);
				dialogUtil.setDialog("输入法提示", "KeyBoardShow");
				dialogUtil.showDiaglog();
				System.out.println("SoftKeyBoardListener keyBoardShow " + height);
			}

			@Override
			public void keyBoardHide(int height) {
				final DialogUtil dialogUtil = new DialogUtil(mContext);
				dialogUtil.setDialog("输入法提示", "KeyBoardShow", new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ToastUtil.showShortToast(mContext, "确定");
					}
				}, new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						ToastUtil.showShortToast(mContext, "取消");
					}
				});
				dialogUtil.showDiaglog();
				System.out.println("SoftKeyBoardListener keyBoardHide " + height);
			}});
	}
	
	public void onClick(View view) {
		if(view.getId() == R.id.canlendar) {
			startActivity(new Intent(mContext, CanlendarMainActivity.class));
		} else if(view.getId() == R.id.swipelist) {
			startActivity(new Intent(mContext, SwipeMenuListActivity.class));
		} else if(view.getId() == R.id.slideSwitch) {
			startActivity(new Intent(mContext, SlideSwitchActivity.class));
		} else if(view.getId() == R.id.sortlistview) {
			startActivity(new Intent(mContext, SortListViewActivity.class));
		} else if(view.getId() == R.id.alertview1) {
			//或者builder模式创建
			new AlertView.Builder().setContext(mContext)
				.setStyle(AlertView.Style.ActionSheet)
				.setTitle("选择操作")
				.setMessage(null)
				.setCancelText("取消")
				.setDestructive("拍照", "从相册中选择")
				.setOthers(null)
				.setOnItemClickListener(new AlertView.OnItemClickListener() {
					
					@Override
					public void onItemClick(Object o, int position) {
						
					}
				})
				.build()
				.show();
		} else if(view.getId() == R.id.alertview2) {
			new AlertView("生活小贴士", "天凉了，早上喝一杯热水", null, new String[]{"确定"}, null, mContext, 
	                AlertView.Style.Alert, null).show();
		} else if(view.getId() == R.id.alertview3) {
			new AlertView("上传头像", null, "取消", null,
	                new String[]{"拍照", "从相册中选择"},
	                this, AlertView.Style.ActionSheet, new AlertView.OnItemClickListener(){
	                    public void onItemClick(Object o,int position){
	                        ToastUtil.showShortToast(mContext, "点击了第" + position + "个");
	                    }
	                }).show();
		} else if(view.getId() == R.id.waterdroplistview) {
			startActivity(new Intent(mContext, WaterDropListViewActivity.class));
		} else if(view.getId() == R.id.pullToZoomScroll) {
			startActivity(new Intent(mContext, PullToZoomScrollActivity.class));
		}
	}

}
