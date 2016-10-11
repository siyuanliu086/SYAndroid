package com.android.common.baseui.webviewtookit;

import java.io.Serializable;

/**
 * 环保卫士--我要举报-gridview的model
 * 
 * @author HJWANGK
 *
 */
public class AttachModel implements Serializable{
	private static final long serialVersionUID = 704973812225356616L;
	/** 文件后缀 mp3,mp4 */
	private String attSuffix;
	/** 附件路径 */
	private String attPath;
	/** 附件名称 */
	private String attFilename;
	/** 缩略图路径 */
	private String thumbnailParh;
	/** 文件大小  277.05KB*/
	private String attFilesize;
	private long fileSize;
	/** 文件类型  application/octet-stream*/
	private String attType;
	/** 文件大小  277.05KB*/
	private String id;
	/** 文件大小  277.05KB*/
	private String masterId;
	/**附件时长*/
	private int timelength;
	
	/**计算拼接下载地址*/
	private String setUpDownloadUrl;
	/**计算拼接图片地址*/
	private String setUpImageUrl;
	
	public String getAttSuffix() {
		return attSuffix;
	}
	public void setAttSuffix(String attSuffix) {
		this.attSuffix = attSuffix;
	}
	public String getAttPath() {
		return attPath;
	}
	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	public String getAttFilename() {
		return attFilename;
	}
	public void setAttFilename(String attFilename) {
		this.attFilename = attFilename;
	}
	public String getThumbnailParh() {
		return thumbnailParh;
	}
	public void setThumbnailParh(String thumbnailParh) {
		this.thumbnailParh = thumbnailParh;
	}
	public String getAttFilesize() {
		return attFilesize;
	}
	public void setAttFilesize(String attFilesize) {
		this.attFilesize = attFilesize;
	}
	public String getAttType() {
		return attType;
	}
	public void setAttType(String attType) {
		this.attType = attType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getSetUpDownloadUrl() {
		return setUpDownloadUrl;
	}
	public void setSetUpDownloadUrl(String setUpDownloadUrl) {
		this.setUpDownloadUrl = setUpDownloadUrl;
	}
	public String getSetUpImageUrl() {
		return setUpImageUrl;
	}
	public void setSetUpImageUrl(String setUpImageUrl) {
		this.setUpImageUrl = setUpImageUrl;
	}
	public int getTimelength() {
		return timelength;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public void setTimelength(int timelength) {
		if(timelength < 1) {
			timelength = 1;
		}
		this.timelength = timelength;
	}
}
