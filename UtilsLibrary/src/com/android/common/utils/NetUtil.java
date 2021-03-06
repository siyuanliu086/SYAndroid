package com.android.common.utils;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * @TiTle NetUtil.java
 * @Package com.android.common.utils
 * @Description 网络工具类(判断网络、设置网络、IP地址、Mac地址)
 * @Date 2016年10月19日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-19
 * @Company ISoftStone ZHHB
 */
public class NetUtil {

	/**
	 * 判断有无网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetwork(final Activity context) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		} else {
			// 提示对话框
			AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("网络设置提示")
			.setMessage("网络连接不可用,是否进行设置?")
			.setPositiveButton("2G/3G设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
					context.startActivityForResult(intent, 0);
				}
			})
			.setNeutralButton("wifi设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
					context.startActivityForResult(intent, 0);
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			})
			.show();
		}
		return netSataus;
	}
	
	public static boolean checkNetwork(final Context context, final DialogInterface.OnClickListener onClickListener, 
			final DialogInterface.OnCancelListener listener) {
		boolean netSataus = false;
		ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		} else {
			// 提示对话框
			AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("网络设置提示")
			.setMessage("网络连接不可用,是否进行设置?")
			.setPositiveButton("2G/3G设置", onClickListener)
			.setNeutralButton("wifi设置", onClickListener)
			.setNegativeButton("取消", onClickListener)
			.setOnCancelListener(listener)
			.show();
		}
		return netSataus;
	}

	public static boolean NetState(Context context) {
		return NetState(context, false);
	}

	/**
	 * 检查网络状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean NetState(Context context, boolean isShowToast) {
		boolean flag = false;
		if (null == context) {
			return flag;
		}
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		// 获取当前的网络连接是否可用
		if (null == networkInfo) {
			if (isShowToast)Toast.makeText(context, "当前的网络连接不可用", Toast.LENGTH_SHORT).show();
			flag = false;
		} else {
			boolean available = networkInfo.isAvailable();
			if (available) {
				flag = true;
			} else {
				Log.i("通知", "当前的网络连接不可用");
				if (isShowToast)Toast.makeText(context, "当前的网络连接不可用", Toast.LENGTH_SHORT).show();
			}
		}

		NetworkInfo mobileNetWorkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
		State state = null;
		if(null!=mobileNetWorkInfo){
			state=mobileNetWorkInfo.getState();
		}
		if (State.CONNECTED == state) {
			LogUtils.d("GPRS网络已连接");
		}

		
		NetworkInfo wifiNetWorkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		state = null;
		if (null!=wifiNetWorkInfo) {
			state=wifiNetWorkInfo.getState();
		}
		if (State.CONNECTED == state) {
			LogUtils.d("WIFI网络已连接");
		}
		return flag;

		// // 跳转到无线网络设置界面
		// startActivity(new
		// Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		// // 跳转到无限wifi网络设置界面
		// startActivity(new
		// Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));

	}

	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取设备IP地址
	 * @return
	 */
	public static String getDeviceIP() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
						if (!inetAddress.getHostAddress().toString().equals("null")
								&& inetAddress.getHostAddress() != null) {
							return inetAddress.getHostAddress().toString().trim();
						}
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return "";
	}
	
	/**
	 * 返回[IP,MAC]
	 * @param mContext
	 * @return
	 */
	public String[] getLocalMacAddress(Context mContext) {  
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return new String[]{intToIp(info.getIpAddress()), info.getMacAddress()};
    }   
	private String intToIp(int i) {       
		return (i & 0xFF ) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) + "." + ( i >> 24 & 0xFF) ;  
	}

}


