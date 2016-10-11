package com.android.common.utils;

import java.lang.reflect.Field;
import java.util.Map;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 
 * @author LuoJ
 * @date 2013-9-23
 * @package com.lidroid.xutils.util -- PhoneUtils.java
 * @Description 手机相关信息获取的工具类
 */
public class PhoneUtils {
	private static TelephonyManager mTelephoneManage;//
	/**
	 * 获取电话管理类实例
	 * @param mctx
	 * @return
	 */
	private static TelephonyManager getTelephoneyManager(Context mctx){
		if (null==mTelephoneManage) {
			mTelephoneManage=(TelephonyManager) mctx.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return mTelephoneManage;
	}
	
	/**
	 * 获取运营商名称
	 * @param tm
	 * @return
	 */
	public static String getProvidersName(Context mctx) {
		getTelephoneyManager(mctx);
		String ProvidersName = null;
		String IMSI = mTelephoneManage.getSubscriberId();
		LogUtils.d("IMSI-->"+IMSI);
		if (null==IMSI) {
			return ProvidersName;
		}
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}
	
	/**
	 * 获取手机的DeviceId（设备号）
	 * @param mctx
	 * @return
	 */
	public static String getPhoneIMEI(Context mctx){
		getTelephoneyManager(mctx);
		return mTelephoneManage.getDeviceId();
	}
	
	/**
	 * 获取设备相关信息
	 * @param infos
	 */
	public static void getDeviceInfo(Map<String, String> infos) {
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
//				LogUtils.e(field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				LogUtils.e("an error occured when collect crash info", e);
			}
		}
	}
	
	/**
	 * 获取当前手机sim卡的手机号码
	 * @param mcontext
	 * @return
	 */
	public static String getPhoneNumber(Context mcontext){
		TelephonyManager mTelephonyManager = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		String num = mTelephonyManager.getLine1Number();
		if(!TextUtils.isEmpty(num)){
			return num;
		}else{
			return "";
		}
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
}
