package com.android.common.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
/**
 * @author ddwangs
 * @date 2016-6-11
 * @package com.android.common.utils
 * @Description 加载html工具类
 */
public class WebViewUtil {

	/**
	 * 判断sdcard中是否存在html ，若存在加载sdcard中的，若不存在加载asset中的
	 * @param mContext
	 * @param htmlName 当前html文件名
	 * @return  返回加载路径
	 */
	public static String getH5Path(Context mContext, String htmlName){
		String url =  "file:///mnt/sdcard/Android/data/com.iss.zhhblsnt/files/h5Pages/" + htmlName;
		String h5Path = mContext.getExternalFilesDir("h5Pages").getAbsolutePath() + File.separator;
		//先判断sdk的状态，
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			File file = new File(h5Path + htmlName);
			if(!file.exists() || file.length() < 10) {
				url = "file:///android_asset/lsnt/mviews/" + htmlName;
			}
		}
		return url;
	}
	
	
	/**
	 * 判断sdcard中是否存在html ，若存在加载sdcard中的，若不存在加载asset中的
	 * @param mContext
	 * @param htmlName 当前html文件名
	 * @return  返回加载路径
	 */
	public static String getZWH5Path(Context mContext, String htmlName){
		String url =  "file:///mnt/sdcard/Android/data/com.iss.zhhblsnt_zwb/files/zw_h5Pages/" + htmlName;
		String h5Path = mContext.getExternalFilesDir("h5Pages").getAbsolutePath() + File.separator;
		//先判断sdk的状态，
		if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
			File file = new File(h5Path + htmlName);
			if(!file.exists() || file.length() < 10) {
				url = "file:///android_asset/lsnt/mviews/" + htmlName;
			}
		}
		return url;
	}
	
	

}
