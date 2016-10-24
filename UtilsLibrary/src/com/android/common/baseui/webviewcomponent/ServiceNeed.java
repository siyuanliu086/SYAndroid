package com.android.common.baseui.webviewcomponent;

import android.webkit.JavascriptInterface;

/**
 * 服务器所需要回调手机端的一些基本内容
 * @author HJWANGK
 *
 */
public interface ServiceNeed {
	/**
	 * 获取服务器地址
	 * 都返回字符串类似：http://36.110.115.116:8082/lsnt
	 * @return
	 */
 @JavascriptInterface
 public String getServerPath();
 /**
  * 服务器需要回调的方法，无参数
  */
 @JavascriptInterface
 public void callBack();
}
