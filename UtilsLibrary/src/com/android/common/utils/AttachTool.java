package com.android.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.common.baseui.videotookit.VideoActivity;
import com.android.common.baseui.webviewtookit.AttachModel;
import com.android.common.baseui.webviewtookit.WebViewActivity;
import com.android.common.imagescan.ImagePagerActivity;
import com.common.android.utilslibrary.R;

/**
 * @TiTle AttachUtils.java
 * @Package com.android.common.utils
 * @Description Attach 工具，打开Attach附件
 * @Date 2016年6月16日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class AttachTool {
	private static AttachTool instance = null;
	public static AttachTool getInstance() {
		if(instance == null) {
			instance = new AttachTool();
		}
		return instance;
	}
	
	private AttachTool() {}
	/**
	 * 跳转加载多图
	 * @param mContext
	 * @param imageUrl
	 * @param position
	 */
	public void goToMoreImage(Context mContext, List<String> imageUrl, int position) {
		if(imageUrl == null || imageUrl.isEmpty()) {
			return;
		}
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		String[] urls = new String[imageUrl.size()];
		urls = imageUrl.toArray(urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}
	
	/**
	 * 跳转显示图片（重载加载多图）
	 * @param mContext
	 * @param imageUrl
	 * @param position
	 */
	public void goToMoreImage(Context mContext, String imageUrl) {
		if(imageUrl == null || imageUrl.isEmpty()) {
			return;
		}
		List<String> urlList = new ArrayList<String>();
		urlList.add(imageUrl);
		goToMoreImage(mContext, urlList, 0);
	}
	
	/**
	 * 跳转下载视频
	 * @param mContext
	 * @param attachModel
	 */
	public void goToVideo(Context mContext, AttachModel attachModel) {
		if(attachModel == null) {
			return;
		}
		Intent intent2 = new Intent(mContext, VideoActivity.class);
		intent2.putExtra("video_url", attachModel.getSetUpDownloadUrl());
		intent2.putExtra("model", attachModel);
		mContext.startActivity(intent2);
	}
	
	/**
	 *  跳转下载视频
	 * @param mContext
	 * @param videoPath
	 */
	public void goToVideo(Context mContext, String videoPath) {
		if(TextUtils.isEmpty(videoPath)) {
			return;
		}
		Intent intent2 = new Intent(mContext, VideoActivity.class);
		intent2.putExtra("video_path", videoPath);
		mContext.startActivity(intent2);
	}
	
	/**
	 * 跳转打开文档( xml,doc,pdf )
	 * @param mContext
	 * @param videoPath
	 */
	public void goToFile(Context mContext, AttachModel attachModel) {
		if(attachModel == null) {
			return;
		}
		String fileType = FileUtils.checkAttachType(attachModel.getAttSuffix());
		FileUtils.OpenFile(mContext, fileType, "file://" + attachModel.getAttPath());
	}
	
	/**
	 * 跳转打开文档( html )
	 * @param mContext
	 * @param videoPath
	 */
	public void goToHTML(Context mContext, AttachModel attachModel) {
		if(attachModel == null) {
			return;
		}
		File htmlFile = new File(attachModel.getAttPath());
		Intent intent = new Intent(mContext, WebViewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(WebViewActivity.WEBVIEW_TITLE, attachModel.getAttFilename());
		if(htmlFile.exists()) {
			bundle.putString(WebViewActivity.WEBVIEW_URL, "file://" + attachModel.getAttPath());
		} else {
			bundle.putString(WebViewActivity.WEBVIEW_URL, attachModel.getSetUpDownloadUrl());
		}
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}
	
	/**
	 * 打开附件
	 * @param attachModel
	 */
	public void openAttach(Context mContext, AttachModel attachModel) {
		String fileType = FileUtils.checkAttachType(attachModel.getAttSuffix());
		// 网络文件打开
		if(FileUtils.FileType.IMAGE.getType().equals(fileType)) {
			File imageFile = new File(attachModel.getAttPath());
			if(imageFile.exists()) {
				// 本地创建文件，则打开
				goToMoreImage(mContext, "file://" + attachModel.getAttPath());
			} else {
				goToMoreImage(mContext, attachModel.getSetUpDownloadUrl());
			}
		} else if(FileUtils.FileType.VIDEO.getType().equals(fileType)) {
			File videoFile = new File(attachModel.getAttPath());
			if(videoFile.exists()) {
				// 本地创建文件，则打开
				goToVideo(mContext, videoFile.getAbsolutePath());
			} else {
				// 文件不存在，则下载
				goToVideo(mContext, attachModel);
			}
		} else if(FileUtils.FileType.AUDIO.getType().equals(fileType)) {
			File imageFile = new File(attachModel.getAttPath());
			if(imageFile.exists()) {
				FileUtils.OpenFile(mContext, fileType, "file://" + attachModel.getAttPath());
			} else {
				FileUtils.OpenFile(mContext, fileType, attachModel.getSetUpDownloadUrl());
			}
		} else if(FileUtils.FileType.HTML.getType().equals(fileType)) {
			goToHTML(mContext, attachModel);
		} else if(FileUtils.FileType.PDF.getType().equals(fileType)
				|| FileUtils.FileType.WORD.getType().equals(fileType)
				|| FileUtils.FileType.EXCEL.getType().equals(fileType)
				|| FileUtils.FileType.PPT.getType().equals(fileType)
				|| FileUtils.FileType.TXT.getType().equals(fileType)) {
			File imageFile = new File(attachModel.getAttPath());
			if(imageFile.exists()) {
				// 加载本地文件（下载完成）
				FileUtils.OpenFile(mContext, fileType, attachModel.getAttPath());
			}
		} else {
			ToastUtil.showShortToast(mContext, R.string.utils_common_error_openfile);
		}
	}
}
