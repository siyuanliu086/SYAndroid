package com.android.common.baseui.audiorecodercomponent;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;

public class MediaRecordFunc {
	// 音频输入-麦克风
	public final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;

	private boolean isRecord = false;

	private MediaRecorder mMediaRecorder;

	private MediaRecordFunc() {
	}

	private static MediaRecordFunc mInstance;

	public synchronized static MediaRecordFunc getInstance() {
		if (mInstance == null)
			mInstance = new MediaRecordFunc();
		return mInstance;
	}

	public int startRecordAndFile() {
		// 判断是否有外部存储设备sdcard
		if (isSdcardExit()) {
			if (isRecord) {
				return ErrorCode.E_STATE_RECODING;
			} else {
				if (mMediaRecorder == null)
					createMediaRecord();

				try {
					mMediaRecorder.prepare();
					mMediaRecorder.start();
					// 让录制状态为true
					isRecord = true;
					return ErrorCode.SUCCESS;
				} catch (IOException ex) {
					ex.printStackTrace();
					return ErrorCode.E_UNKOWN;
				}
			}

		} else {
			return ErrorCode.E_NOSDCARD;
		}
	}

	public void stopRecordAndFile() {
		close();
	}

	public long getRecordFileSize() {
		return getFileSize(getAMRFilePath());
	}

	private void createMediaRecord() {
		/* ①Initial：实例化MediaRecorder对象 */
		mMediaRecorder = new MediaRecorder();

		/* setAudioSource/setVedioSource */
		mMediaRecorder.setAudioSource(AUDIO_INPUT);// 设置麦克风

		/*
		 * 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
		 * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
		 */
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);

		/* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

		/* 设置输出文件的路径 */
		File file = new File(getAMRFilePath());
		if (file.exists()) {
			file.delete();
		}
		mMediaRecorder.setOutputFile(getAMRFilePath());
	}

	private String amrFilePath;

	public void setAMRFilePath(String filePath) {
		this.amrFilePath = filePath;
	}

	public String getAMRFilePath() {
		File amrFile = new File(amrFilePath);
		if (!amrFile.exists()) {
			createImagePath(amrFilePath);
		}
		return amrFilePath;
	}

	private void createImagePath(String amrPath) {
		File file = new File(amrPath);
		if (file.exists()) {
			return;
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (mMediaRecorder != null) {
			System.out.println("stopRecord");
			isRecord = false;
			try {
				mMediaRecorder.stop();
				mMediaRecorder.release();
				mMediaRecorder = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getMaxAmplitude() {
		if(mMediaRecorder == null) {
			return 0;
		} else {			
			return mMediaRecorder.getMaxAmplitude();
		}
	}

	/**
	 * 获取文件大小
	 * 
	 * @param path
	 *            ,文件的绝对路径
	 * @return
	 */
	public long getFileSize(String path) {
		File mFile = new File(path);
		if (!mFile.exists())
			return -1;
		return mFile.length();
	}

	/**
	 * 判断是否有外部存储设备sdcard
	 * 
	 * @return true | false
	 */
	public static boolean isSdcardExit() {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
}
