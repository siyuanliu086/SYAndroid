package com.android.common.baseui.videotookit;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.android.common.baseui.BaseActivity;
import com.android.common.baseui.views.NumberProgressBar;
import com.android.common.baseui.views.TitleBarView;
import com.android.common.baseui.webviewtookit.AttachModel;
import com.android.common.baseui.webviewtookit.DownloadUtil;
import com.android.common.utils.MD5;
import com.android.common.utils.ToastUtil;
import com.common.android.utilslibrary.R;

/**
 * @TiTle VideoActivity.java
 * @Package com.iss.zhhblsnt.activity
 * @Description 播放视频，通用页面
 * @Date 2016年5月18日
 * @Author siyuan
 * @Refactor
 * @Company ISoftStone ZHHB
 */
public class VideoActivity extends BaseActivity {
	private Context mContext = this;
	private View mBaseView;
	private FullscreenVideoLayout videoLayout;
	private Uri videoUri;
	private String filePath;
	private DownloadUtil downloadUtil;
	private NumberProgressBar progressbar;
	
	AttachModel model = null;
	
	private Handler mHandler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0) {
				int progress = (Integer) msg.obj;
				progressbar.setProgress(progress);
			} else if(msg.what == 1) {
				progressbar.setVisibility(View.GONE);
				try {
					videoLayout.setVideoURI(videoUri);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			} else if(msg.what == 2) {
				ToastUtil.showShortToast(mContext, R.string.utils_common_video_download_failed);
			}
		}
	};
	
	@Override
	protected void initView() {
		mBaseView = mInflater.inflate(R.layout.utilslib_activity_video, null);
		mainContentLayout.addView(mBaseView);
		// 准备标题栏
		baseTitleBar.setVisibility(View.GONE);
		// 准备标题栏
		TitleBarView titleBarView = (TitleBarView) mBaseView.findViewById(R.id.video_title_bar);
		titleBarView.setCommonTitle(View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleBackgroundColor(Color.argb(0x00, 0x00, 0x00, 0x00));
		titleBarView.setLeftOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		progressbar = (NumberProgressBar) mBaseView.findViewById(R.id.top_progressbar);
		
		videoLayout = (FullscreenVideoLayout) mBaseView.findViewById(R.id.videoview);
		videoLayout.hideControls();
        videoLayout.setActivity(this);
        videoLayout.setShouldAutoplay(true);
	}

	@Override
	protected void setUpView() {
		
	}

	@Override
	protected void initData() {
		String url = getIntent().getStringExtra("video_url");// 网址
		String path = getIntent().getStringExtra("video_path");// 本地
		model = (AttachModel) getIntent().getSerializableExtra("model");// 网址
		if (!TextUtils.isEmpty(url)) {
			filePath = getBasePath() + MD5.GetMD5Code(url) + ".mp4";
			startDownload(url);
		}
		if (!TextUtils.isEmpty(path)) {
			videoUri = Uri.fromFile(new File(path));
			//通知播放
			mHandler.sendEmptyMessage(1);
		}
	}
	
	private String getBasePath() {
		File baseFile = mContext.getExternalFilesDir("files");
		if(baseFile == null ||!baseFile.exists()){
			baseFile.mkdirs();
        }
		return baseFile.getAbsolutePath()+ File.separator;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			// 按下事件
			if(videoLayout != null && videoLayout.isPlaying()) {
				videoLayout.pause();
			} else if(videoLayout != null && !videoLayout.isPlaying()) {
				videoLayout.start();
			}
		}
		return super.onTouchEvent(event);
	}

	private void startDownload(String url) {
		progressbar.setVisibility(View.VISIBLE);
		downloadUtil = new DownloadUtil(url, filePath, model, new DownloadUtil.DownLoadProgressListener() {
			
			@Override
			public void onDownloadProgress(int progress) {
				sendMessage(progress);
			}
			
			private void sendMessage(int progress) {
				Message msg = mHandler.obtainMessage();
				msg.what = 0;
				msg.obj = progress;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onDownloadCompleted(int state) {
				if(DownloadUtil.STATE_FAILED == state) {
					mHandler.sendEmptyMessage(2);
				} else if(DownloadUtil.STATE_SUCCESS == state){
					// 成功
					sendMessage(100);
					
					videoUri = Uri.fromFile(new File(filePath));
					// 下载完成，通知播放
					mHandler.sendEmptyMessage(1);
				}
			}
		});
    	downloadUtil.startDownload();
	}

	@Override
	protected void onNetStateChanged(boolean isOn) {
		
	}

	// 暂停
	public void onPause() {
		videoLayout.pause();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		videoLayout.stop();
		if(downloadUtil != null)
			downloadUtil.stopDownload();
		super.onDestroy();
	}
}