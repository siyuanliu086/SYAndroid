package com.android.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

/**   
 *    
 *@项目名称：CsManageClient   
 *@类名称：TelPhoneUtil   
 *@类描述：   拨打电话工具类
 *@创建人：代蕾  
 *@创建时间：2014-5-14 下午5:41:52   
 *@修改人：lenovo   
 *@修改时间：2014-5-14 下午5:41:52   
 *@修改备注：   
 *@version    
 *    
 */
public class TelPhoneUtil {
	
	public static TelPhoneUtil telPhoneUtil;
	
	private TelPhoneUtil() {
	}
	
	
	public static TelPhoneUtil newInstance(){
		if (null == telPhoneUtil) {
			telPhoneUtil = new TelPhoneUtil();
		}
		return telPhoneUtil;
	}
	
	public void telPhone(Activity act,String telNum){
		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telNum));  
		act.startActivity(intent);
	}
	
	/**
	 * 调起系统发短信功能
	 * @param phoneNumber
	 * @param message
	 */
	public static void doSendSMSTo(Context context, String phoneNumber, String message){
		if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+phoneNumber));          
			intent.putExtra("sms_body", message);          
			context.startActivity(intent);
		}
	}
	
}
