package com.android.common.baseui.webviewcomponent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @TiTle DownloadUtil.java
 * @Package com.iss.zhhblsnt.tools
 * @Description 下载器
 * @Date 2016年5月17日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class DownloadUtil {
	public static final int STATE_SUCCESS = 0x10; 
	public static final int STATE_FAILED = 0x11; 
	
	public DownLoadProgressListener downloadLinstener;
	private long fileSize;
	private long downFileSize;
	
	private boolean mStop = false;
	private String downloadUrl;
	private String targetPath;
	private AttachModel model;
	
	private MonitorThread monitorThread;
	private DownloadThread downloadThread;
	
	public DownloadUtil(String downloadUrl, String targetPath, DownLoadProgressListener loadLinstener) {
		this.downloadLinstener = loadLinstener;
		this.downloadUrl = downloadUrl;
		this.targetPath = targetPath;
	}
	
	public DownloadUtil(String downloadUrl, String targetPath, AttachModel model, DownLoadProgressListener loadLinstener) {
		this.downloadLinstener = loadLinstener;
		this.downloadUrl = downloadUrl;
		this.targetPath = targetPath;
		this.model = model;
	}
	
	public void startDownload() {
		if(downloadThread != null) {
			mStop = true;
			downloadThread = null;
		}
		mStop = false;
		downloadThread = new DownloadThread();
		downloadThread.start();
	}
	
	public void stopDownload() {
		mStop = true;
	}
	
	class DownloadThread extends Thread {
		@Override
		public void run() {
			super.run();
			// 开始下载
			long sizeValueByte = 0;
			if(model != null) {
				if(model.getFileSize() == 0) {
					String size = model.getAttFilesize();
					try {
						if(size.endsWith("MB")) {
							float valuef = Float.valueOf(size.subSequence(0, size.length() - 2).toString());
							sizeValueByte = (long) (valuef * 1024 * 1024);
						} else if(size.endsWith("KB")) {
							float valuef = Float.valueOf(size.subSequence(0, size.length() - 2).toString());
							sizeValueByte = (long) (valuef * 1024);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				} else {
					sizeValueByte = model.getFileSize();
				}
			}
			downloadFile(downloadUrl, targetPath, sizeValueByte);
		}
	}
	
	class MonitorThread extends Thread {
		@Override
		public void run() {
			super.run();
			while(!mStop) {
				// 计算进度
				int progress = (int) (downFileSize * 100.0 / fileSize);
				progressListener(progress);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param downloadUrl
	 * @param saveFilePath
	 * @param expectFileSize 预期文件大小
	 * @return ture:succeed false:fail
	 */
	private boolean downloadFile(String downloadUrl, String targetPath, long expectFileSize) {
		File saveFilePath = createNewFile(targetPath);
		boolean result = false;
		try {
			URL url = new URL(downloadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (null == conn) {
				return false;
			}
			// 读取超时时间 毫秒级
			conn.setReadTimeout(48 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept-Encoding", "identity"); 
			conn.setDoInput(true);
			conn.connect();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				fileSize = conn.getContentLength();
				
				boolean sizeFix = saveFilePath.length() == fileSize;
				if(fileSize <= 0) {
					fileSize = expectFileSize;
					// 判断文件大小，此处获取的文件大小不精确 ，做100KB误差，临时使用
					if(saveFilePath.length() > expectFileSize - 100 * 1024
							&& saveFilePath.length() < expectFileSize + 100 * 1024) {
						sizeFix = true;
					}
				}
				
				if(sizeFix) {
					result = true;
					mStop = true;
				} else {
					// 开启下载监视进程
					monitorThread = new MonitorThread();
					monitorThread.start();
					
					InputStream is = conn.getInputStream();
					FileOutputStream fos = new FileOutputStream(saveFilePath);
					byte[] buffer = new byte[1024 * 128];
					int i = 0;
					while (!mStop && (i = is.read(buffer)) != -1) {
						downFileSize += i;
						fos.write(buffer, 0, i);
					}
					fos.flush();
					fos.close();
					is.close();
					
					if(mStop) {
						// 手动停止下载，则删掉文件（因为暂时不支持断点）
						new File(targetPath).delete();
					}
					result = true;
					mStop = true;
				}
				// 下载完成回调
				completedListener(STATE_SUCCESS);
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
			mStop = true;
			completedListener(STATE_FAILED);
		}
		return result;
	}

	/**
	 * 创建新文件
	 * @param path
	 * @return
	 */
	private File createNewFile(String path) {
		File newFile = new File(path);
		if(newFile.exists()) {
			return newFile;
		}
		if(!newFile.getParentFile().exists()) {
			newFile.getParentFile().mkdirs();
		}
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile;
	}

	
	private void progressListener(int progress) {
		if(downloadLinstener != null) {
			downloadLinstener.onDownloadProgress(progress);
		}
	}
	
	private void completedListener(int state) {
		if(downloadLinstener != null) {
			downloadLinstener.onDownloadCompleted(state);
		}
	}

	public void setLoadLinstener(DownLoadProgressListener downLoadListener){
		this.downloadLinstener = downLoadListener;
	}
	
	public interface DownLoadProgressListener{
		void onDownloadProgress(int progress);
		void onDownloadCompleted(int state);
	}

}
