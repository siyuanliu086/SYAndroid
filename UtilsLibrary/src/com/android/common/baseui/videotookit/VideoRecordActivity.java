package com.android.common.baseui.videotookit;

import java.io.File;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.baseui.BaseActivity;
import com.common.android.utilslibrary.R;

/**
 * @TiTle VideoRecordActivity.java
 * @Package com.android.common.baseui.videotookit
 * @Description 录制视频页面
 * @Date 2016年10月11日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class VideoRecordActivity extends BaseActivity {
	protected Context mContext = this;
	protected RelativeLayout layout;
	protected VideoRecorderView recoderView;
	protected Button videoController;
	protected TextView message , tvCancal;
	protected boolean isCancel = false;

	@Override
	protected void initView() {
		baseTitleBar.setVisibility(View.GONE);
		layout = (RelativeLayout) mInflater.inflate(R.layout.utilslib_activity_vedio_record, null);
		mainContentLayout.addView(layout); 
		recoderView = (VideoRecorderView) layout.findViewById(R.id.recoder);
		videoController = (Button) layout.findViewById(R.id.videoController);
		message = (TextView) layout.findViewById(R.id.message);
		tvCancal = (TextView) layout.findViewById(R.id.tv_video_cancal);
		ViewGroup.LayoutParams params = recoderView.getLayoutParams();
		int[] screenSize = getScreenSize(mContext);
		params.width =  screenSize[0];
		params.height =  screenSize[0];
		recoderView.setLayoutParams(params);
	}

	@Override
	protected void initData() {
		
	}
	
	private int[] getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return new int[]{wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight()};
	}
	
	/**
	 * 移动取消弹出动画
	 */
	public void cancelAnimations() {
		message.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
		message.setTextColor(getResources().getColor(android.R.color.white));
		message.setText("松手取消");
	}

	/**
	 * 显示提示信息
	 */
	public void showPressMessage() {
		message.setVisibility(View.VISIBLE);
		message.setBackgroundColor(getResources().getColor(android.R.color.transparent));
		message.setTextColor(getResources().getColor(android.R.color.holo_green_light));
		message.setText("上移取消");
	}


	/**
	 * 按下时候动画效果
	 */
	public void pressAnimations() {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.5f,
				1, 1.5f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(200);

		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(200);

		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		animationSet.setFillAfter(true);

		videoController.startAnimation(animationSet);
	}

	/**
	 * 释放时候动画效果
	 */
	public void releaseAnimations() {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.5f, 1f,
				1.5f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(200);

		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(200);

		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		animationSet.setFillAfter(true);

		message.setVisibility(View.GONE);
		videoController.startAnimation(animationSet);
	}


	@Override
	protected void setUpView() {
		tvCancal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		videoController.setOnTouchListener(new VideoTouchListener());

		recoderView.setRecorderListener(new VideoRecorderView.RecorderListener() {

			@Override
			public void recording(int maxtime, int nowtime) {
				
			}

			@Override
			public void recordSuccess(File videoFile, int timeLength) {
				System.out.println("recordSuccess");
				if (videoFile != null) {
					System.out.println(" Record Video Saved : " + videoFile.getAbsolutePath());
					recordCompleted(videoFile, timeLength);
				}
				releaseAnimations();
			}

			@Override
			public void recordStop() {
				System.out.println("recordStop");
			}

			@Override
			public void recordCancel() {
				System.out.println("recordCancel");
				releaseAnimations();
			}

			@Override
			public void recordStart() {
				System.out.println("recordStart");
			}

			@Override
			public void videoStop() {
				System.out.println("videoStop");
			}

			@Override
			public void videoStart() {
				System.out.println("videoStart");
			}
		});

	}
	
	public class VideoTouchListener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				recoderView.startRecord(getBasePath());
				isCancel = false;
				pressAnimations();
				break;
			case MotionEvent.ACTION_MOVE:
				if (event.getX() > 0
						&& event.getX() < videoController.getWidth()
						&& event.getY() > 0
						&& event.getY() < videoController.getHeight()) {
					showPressMessage();
					isCancel = false;
				} else {
					cancelAnimations();
					isCancel = true;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (isCancel) {
					recoderView.cancelRecord();
				}else{
					recoderView.endRecord();
				}
				message.setVisibility(View.GONE);
				releaseAnimations();
				break;
			default:
				break;
			}
			return false;
		}
	}
	
	@Override
	protected void onNetStateChanged(boolean isOn) {
		
	}
	
	/**
	 * 录制完成
	 * @param videoFile
	 */
	protected void recordCompleted(File videoFile, int timelength) {
		
	}
	
	/**
	 * 获取文件路径(默认sdcard/data/包名/file/files/...)，如需修改则重写
	 * @return
	 */
	protected String getBasePath() {
		File baseFile = mContext.getExternalFilesDir("files");
		if(baseFile == null ||!baseFile.exists()){
			baseFile.mkdirs();
        }
		return baseFile.getAbsolutePath()+ File.separator;
	}

}
