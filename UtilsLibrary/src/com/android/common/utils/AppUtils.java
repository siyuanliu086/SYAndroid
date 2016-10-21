package com.android.common.utils;

import java.io.File;
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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

/**
 * @TiTle AppUtils.java
 * @Package com.android.common.utils
 * @Description APP级别通用工具及方法（版本号、应用信息、SD卡路径、系统版本、服务运行、UUID、横竖屏、
 * 		是否系统APP、是否栈顶、根据设备cpu获取线程池大小建议）
 * @Date 2016年10月14日 
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-14
 * @Company ISoftStone ZHHB
 */
public class AppUtils {
	
	private static PackageManager pm;
	
	 /** recommend default thread pool size according to system available processors, {@link #getDefaultThreadPoolSize()} **/
    public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

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
	 * 
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
	 * 判断app 是否安装 
	 * @param context 	上下文
	 * @param pageName	包名
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
	@SuppressWarnings("deprecation")
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
	
	/**
	 * 判断Activity是否在栈顶（活动状态）
	 * @param context
	 * @param activityName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isActivityInTaskTop(Context context, String activityName){
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
		// 判断SD卡是否存在
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
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
		return UUID.randomUUID().toString();
	}

    /**
     * 查询屏幕是否竖屏，非竖屏则横屏
     * @param context
     * @return
     */
	public static boolean isScreenPortrait(Context context) {
		Configuration mConfiguration = context.getResources()
				.getConfiguration(); // 获取设置的配置信息
		int ori = mConfiguration.orientation; // 获取屏幕方向
		return ori == Configuration.ORIENTATION_PORTRAIT;
	}
	
	/**
     * whether context is system application
     * 
     * @param context
     * @return
     */
    public static boolean isSystemApplication(Context context) {
        if (context == null) {
            return false;
        }

        return isSystemApplication(context, context.getPackageName());
    }
    
    /**
     * whether packageName is system application
     * 
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isSystemApplication(Context context, String packageName) {
        if (context == null) {
            return false;
        }

        return isSystemApplication(context.getPackageManager(), packageName);
    }

    /**
     * whether packageName is system application
     * 
     * @param packageManager
     * @param packageName
     * @return <ul>
     *         <li>if packageManager is null, return false</li>
     *         <li>if package name is null or is empty, return false</li>
     *         <li>if package name not exit, return false</li>
     *         <li>if package name exit, but not system app, return false</li>
     *         <li>else return true</li>
     *         </ul>
     */
    public static boolean isSystemApplication(PackageManager packageManager, String packageName) {
        if (packageManager == null || packageName == null || packageName.length() == 0) {
            return false;
        }

        try {
            ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
            return (app != null && (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * whether the app whost package's name is packageName is on the top of the stack
     * <ul>
     * <strong>Attentions:</strong>
     * <li>You should add <strong>android.permission.GET_TASKS</strong> in manifest</li>
     * </ul>
     * 
     * @param context
     * @param packageName
     * @return if params error or task stack is null, return null, otherwise retun whether the app is on the top of
     *         stack
     */
    public static Boolean isTopActivity(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return null;
        }

        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo != null || tasksInfo.isEmpty()) {
            return null;
        }
        try {
            return packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
     * get recommend default thread pool size
     * 
     * @return if 2 * availableProcessors + 1 less than 8, return it, else return 8;
     * @see {@link #getDefaultThreadPoolSize(int)} max is 8
     */
    public static int getDefaultThreadPoolSize() {
        return getDefaultThreadPoolSize(8);
    }

    /**
     * get recommend default thread pool size
     * 
     * @param max
     * @return if 2 * availableProcessors + 1 less than max, return it, else return max;
     */
    public static int getDefaultThreadPoolSize(int max) {
        int availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
        return availableProcessors > max ? max : availableProcessors;
    }

}
