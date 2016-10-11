package com.android.common.baseui.videotookit;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.common.android.utilslibrary.R;

/**
 * @TiTle VideoRecorderView.java
 * @Package com.android.common.baseui.videotookit
 * @Description 录制视频的View
 * @Date 2016年10月11日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class VideoRecorderView extends LinearLayout implements MediaRecorder.OnErrorListener {
	//视频展示
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHoler;

	private SurfaceView videoSurfaceView;
	private ImageView playVideo;

	//进度条
	private ProgressBar progressBar_left;
	private ProgressBar progressBar_right;

	//录制视频
	private MediaRecorder mediaRecorder;
	//摄像头
	private Camera camera;
	private Timer timer;

	//视频播放
	private MediaPlayer mediaPlayer;

	//时间限制
	private static final int recordMaxTime = 15;
	private int timeCount;
	//生成的文件
	private File vecordFile;

	private Context mContext;

	//正在录制
	private boolean isRecording = false;
	//录制成功
	private boolean isSuccess = false;
	//活动id
	private String activityId;
	private RecorderListener recorderListener;

	public VideoRecorderView(Context context) {
		super(context, null);
		this.mContext = context;
	}

	public VideoRecorderView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.mContext = context;
		init();
	}

	public VideoRecorderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
	}

	private void init() {

		LayoutInflater.from(mContext).inflate(R.layout.utilslib_layout_ui_recorder, this);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		videoSurfaceView = (SurfaceView) findViewById(R.id.playView);
		playVideo = (ImageView) findViewById(R.id.playVideo);

		progressBar_left = (ProgressBar) findViewById(R.id.progressBar_left);
		progressBar_right = (ProgressBar) findViewById(R.id.progressBar_right);

		progressBar_left.setMax(recordMaxTime * 20);
		progressBar_right.setMax(recordMaxTime * 20);

		progressBar_left.setProgress(recordMaxTime * 20);

		surfaceHoler = surfaceView.getHolder();
		surfaceHoler.addCallback(new CustomCallBack());
		surfaceHoler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		initCamera();

		playVideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				playVideo();
			}
		});
	}

	private class CustomCallBack implements SurfaceHolder.Callback {

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			initCamera();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			freeCameraResource();
		}
	}


	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		try {
			if (mr != null)
				mr.reset();
		} catch (Exception e) {

		}
	}

	public void setActivityId(String activityId){
		this.activityId = activityId;
	}

	/**
	 * 初始化摄像头
	 */
	private void initCamera() {
		if (camera != null)
			freeCameraResource();
		try{
			camera = Camera.open();
		} catch(RuntimeException e){
			camera = Camera.open(Camera.getNumberOfCameras()-1);
		}
		if (camera == null)
			return;
		camera.setDisplayOrientation(90);
		try {
			camera.autoFocus(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	
		try {
			Camera.Parameters params = camera.getParameters();
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			camera.setParameters(params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			camera.setPreviewDisplay(surfaceHoler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.startPreview();
		camera.unlock();
	}

	/**
	 * 初始化摄像头配置
	 */
	private void initRecord() {
		mediaRecorder = new MediaRecorder();
		mediaRecorder.reset();
		if (camera != null)
			mediaRecorder.setCamera(camera);

		mediaRecorder.setOnErrorListener(this);
//		mediaRecorder.setPreviewDisplay(surfaceHoler.getSurface());
//		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//		mediaRecorder.setOrientationHint(90);
//
//		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//		mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);

		// 视频的帧率和视频大小是需要硬件支持的，如果设置的帧率和视频大小,如果硬件不支持就会出现错误
//		mediaRecorder.setVideoSize(352, 288);
		//        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
//		mediaRecorder.setVideoFrameRate(16);
//		mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
//		mediaRecorder.setOrientationHint(90);
//		mediaRecorder.setVideoFrameRate(32);
//		mediaRecorder.setMaxDuration(recordMaxTime * 1000);
//		mediaRecorder.setOutputFile(vecordFile.getAbsolutePath());
		
		//1st. Initial state  
		CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
		
        //2st. Initialized state  
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);  
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);  
          
        //3st. config  
        mediaRecorder.setOutputFormat(mProfile.fileFormat);  
        mediaRecorder.setAudioEncoder(mProfile.audioCodec);  
        mediaRecorder.setVideoEncoder(mProfile.videoCodec);  
        mediaRecorder.setOrientationHint(90);
        mediaRecorder.setOutputFile(vecordFile.getAbsolutePath());  
        mediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);  
        mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);  
        mediaRecorder.setVideoEncodingBitRate(mProfile.videoBitRate);  
//        mediaRecorder.setVideoEncodingBitRate(4 * 1024 * 1024);
        mediaRecorder.setAudioEncodingBitRate(mProfile.audioBitRate);  
        mediaRecorder.setAudioChannels(mProfile.audioChannels);  
        mediaRecorder.setAudioSamplingRate(mProfile.audioSampleRate);  
        
        mediaRecorder.setPreviewDisplay(surfaceHoler.getSurface());
	}

	private void prepareRecord() {
		try {
			mediaRecorder.prepare();
			mediaRecorder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始录制
	 */
	public void startRecord(String filePath) {

		//录制中
		if (isRecording)
			return;
		//创建文件
		createRecordDir(filePath);

		initCamera();

		videoSurfaceView.setVisibility(View.GONE);
		playVideo.setVisibility(View.GONE);
		surfaceView.setVisibility(View.VISIBLE);

		//初始化控件
		initRecord();
		prepareRecord();
		isRecording = true;
		if (recorderListener != null)
			recorderListener.recordStart();
		//15秒自动化结束
		timeCount = 0;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timeCount++;
				progressBar_left.setProgress(timeCount);
				progressBar_right.setProgress(recordMaxTime * 20 - timeCount);
				if (recorderListener != null)
					recorderListener.recording(recordMaxTime * 1000, timeCount * 50);
				if (timeCount == recordMaxTime * 20) {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				}
			}
		}, 0, 50);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				endRecord();
			}
		}
	};

	/**
	 * 停止录制
	 */
	public void endRecord() {
		if (!isRecording)
			return;
		isRecording = false;
		if (recorderListener != null) {
			recorderListener.recordStop();
			recorderListener.recordSuccess(vecordFile, timeCount * recordMaxTime / 100 / 3);
		}
		stopRecord();
		releaseRecord();
		freeCameraResource();
		videoSurfaceView.setVisibility(View.VISIBLE);
		playVideo.setVisibility(View.VISIBLE);
	}

	/**
	 * 取消录制
	 */
	public void cancelRecord() {
		videoSurfaceView.setVisibility(View.GONE);
		playVideo.setVisibility(View.GONE);
		surfaceView.setVisibility(View.VISIBLE);
		if (!isRecording)
			return;
		isRecording = false;
		stopRecord();
		releaseRecord();
		freeCameraResource();
		isRecording = false;
		if (vecordFile.exists())
			vecordFile.delete();
		if (recorderListener != null)
			recorderListener.recordCancel();
		initCamera();
	}

	/**
	 * 停止录制
	 */
	private void stopRecord() {
		progressBar_left.setProgress(recordMaxTime * 20);
		progressBar_right.setProgress(0);

		if (timer != null)
			timer.cancel();
		if (mediaRecorder != null) {
			// 设置后不会崩
			mediaRecorder.setOnErrorListener(null);
			mediaRecorder.setPreviewDisplay(null);
			try {
				mediaRecorder.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void destoryMediaPlayer() {
		if (mediaPlayer == null)
			return;
		mediaPlayer.setDisplay(null);
		mediaPlayer.reset();
		mediaPlayer.release();
		mediaPlayer = null;
	}

	/**
	 * 播放视频
	 */
	public void playVideo() {
		surfaceView.setVisibility(View.GONE);
		videoSurfaceView.setVisibility(View.VISIBLE);
		playVideo.setVisibility(View.GONE);
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(vecordFile.getAbsolutePath());
			mediaPlayer.setDisplay(videoSurfaceView.getHolder());
			mediaPlayer.prepare();//缓冲
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (recorderListener != null)
			recorderListener.videoStart();
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				if (recorderListener != null)
					recorderListener.videoStop();
				playVideo.setVisibility(View.VISIBLE);
			}
		});
	}

	public RecorderListener getRecorderListener() {
		return recorderListener;
	}

	public void setRecorderListener(RecorderListener recorderListener) {
		this.recorderListener = recorderListener;
	}

	public SurfaceView getSurfaceView() {

		return surfaceView;
	}

	public void setSurfaceView(SurfaceView surfaceView) {
		this.surfaceView = surfaceView;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public interface RecorderListener {

		public void recording(int maxtime, int nowtime);

		public void recordSuccess(File videoFile, int timelength);

		public void recordStop();

		public void recordCancel();

		public void recordStart();

		public void videoStop();

		public void videoStart();
	}


	/**
	 * 创建视频文件
	 */
	private void createRecordDir(String filePath) {
		File sampleDir = new File(filePath);
		if (!sampleDir.exists()) {
			sampleDir.mkdirs();
		}
		File vecordDir = sampleDir;
		// 创建文件
		try {
			vecordFile = File.createTempFile("recording", ".mp4", vecordDir);//mp4格式
		} catch (IOException e) {
		}
	}

	/**
	 * 释放资源
	 */
	private void releaseRecord() {
		if (mediaRecorder != null) {
			mediaRecorder.setOnErrorListener(null);
			try {
				mediaRecorder.release();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mediaRecorder = null;
	}

	/**
	 * 释放摄像头资源
	 */
	private void freeCameraResource() {
		if (null != camera) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.lock();
			camera.release();
			camera = null;
		}
	}
}
