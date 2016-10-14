package com.android.common.utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

/**
 * 
 * @author zhang_xinyuan
 *
 */
public class AppUtils {
	
	private static PackageManager pm;

	/**
	 * 创建包管理器实例.
	 * 
	 * @param mctx
	 */
	private static void getPackageManager(Context mctx) {
		if (null == pm) {
			pm = mctx.getPackageManager();
		}
	}

	/**
	 * 获取当前应用版本信息.
	 * 
	 * @return 当前应用的版本号
	 */
	public static int getCurrentVersion(Context mctx) {
		getPackageManager(mctx);
		try {
			PackageInfo info = pm.getPackageInfo(mctx.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		return -1;
	}
	
	/**
	 * 获取版本名
	 * @param mContext
	 * @return
	 */
	public static String getCurrentVersionName(Activity mContext){
		PackageInfo pi;
		try {
			pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取应用版本号和版本名称.
	 * 
	 * @param mctx
	 * @param infos
	 * 存储版本信息的容器(只存储了版本名称及版本号)
	 */
	public static void getVersionInfo(Context mctx, Map<String, String> infos) {
		getPackageManager(mctx);
		try {
			PackageInfo pi = pm.getPackageInfo(mctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			LogUtils.e("an error occured when collect package info", e);
		}
	}

	/**
	 * 获取当前应用信息.
	 * 
	 * @param mctx
	 * @return 应用信息
	 */
	public static PackageInfo getPackageInfo(Context mctx) {
		getPackageManager(mctx);
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(mctx.getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e) {
			LogUtils.e("an error occured when collect package info", e);
		}
		return pi;
	}

	/**
	 * 获取当前系统API Level.
	 * 
	 * @return API Level
	 */
	public static int getAndroidSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 判断服务是否正在运行.
	 * 
	 * @param mctx
	 * @param service 指定要判断的Service类型
	 * @return true:正在运行 、 false:未运行
	 */
	public static boolean isServiceWorking(Context mctx, Class<? extends Service> service) {
		return isServiceWorking(mctx, service, false);
	}

	/**
	 * 判断服务是否正在运行.
	 * 
	 * @param mctx
	 * @param service 指定要判断的Service类型
	 * @param isShowLog 是否显示log
	 * @return true:正在运行 、 false:未运行
	 */
	public static boolean isServiceWorking(Context mctx, Class<? extends Service> service, boolean isShowLog) {
		ActivityManager myManager = (ActivityManager) mctx.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
		for (int i = 0; i < runningService.size(); i++) {
			if (isShowLog)
				LogUtils.e(i + "now service-->" + runningService.get(i).service.getClassName().toString() + "\nparam service-->" + service.getCanonicalName());
			if (runningService.get(i).service.getClassName().toString().equals(service.getCanonicalName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断app 是否安装 com.dynamixsoftware.printershare
	 * @param context 	上下文
	 * @param pageName	报名
	 * @return
	 */
	public static boolean existsApk(Context context, String pageName) {
		try {
			context.getPackageManager().getPackageInfo(pageName, 0);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * 判断应用是否在活动中状态
	 * @param context
	 * @return
	 */
	public static boolean isAction(final Context context) {
	       ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	       List<RunningTaskInfo> tasks = am.getRunningTasks(1);
	       if (!tasks.isEmpty()) {
	             ComponentName topActivity = tasks.get(0).topActivity;
	             if (topActivity.getPackageName().equals(context.getPackageName())) {
	                   return true;
	             }
	       }
	     return false;
	}
	
	public static boolean isActivityInTaskTop(Context context,String activityName){
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	       List<RunningTaskInfo> tasks = am.getRunningTasks(1);
	       if (!tasks.isEmpty()) {
	             ComponentName topActivity = tasks.get(0).topActivity;
	             if (topActivity.getClassName().equals(activityName)) {
	                   return true;
	             }
	       }
	     return false;
	}
	
	
	/**
	 * 获取Sd卡路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断SD卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取SD卡路径
			return sdDir.toString();
		} else {
			return null;
		}

	}

	/**
	 * 获取MimeType
	 * @param url 
	 * @return
	 */
	public static String getMimeType(String url) {
		String type = null;
		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
		if (extension != null) {
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			type = mime.getMimeTypeFromExtension(extension);
		}
		return type;
	}

	
	/**
	 * 获取UUID
	 * @param length
	 * @return
	 */
	public static String generateId(int length) {
		String uuid = UUID.randomUUID().toString();
	    return PadLeading(uuid, length, "");
	}
	
	public static String PadLeading(String rString, int rLength, String rPad) {
		String lTmpPad = "";
		String lTmpStr = rString;

		if (!StringUtil.isNull(lTmpStr)) {
			if (lTmpStr.length() >= rLength)
				return lTmpStr.substring(0, rLength);
			else {
				for (int gCnt = 1; gCnt <= rLength - lTmpStr.length(); gCnt++) {
					lTmpPad = lTmpPad + rPad;
				}
			}
		}
		return lTmpPad + lTmpStr;
	}
	
}
