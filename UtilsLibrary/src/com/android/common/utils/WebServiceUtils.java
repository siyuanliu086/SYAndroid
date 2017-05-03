package com.android.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpResponseException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Handler;
import android.os.Message;

/**
 * @TiTle WebServiceUtils.java
 * @Package com.android.common.utils
 * @Description 访问WebService的工具类(支持库：ksoap2-android-assembly-3.0.0-jar-with-dependencies.jar)
 * @Date 2017年3月31日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 * 
//		WebServiceUtils.callWebService(
//				"http://www.webxml.com.cn/WebServices/WeatherWebService.asmx", 
//				"http://WebXml.com.cn/", 
//				"getSupportProvince", 
//				properties , new WebServiceUtils.WebServiceCallBack() {
//			@Override
//			public void callBack(SoapObject result) {
//				if(result != null) {
//					String resultStr = result.getProperty(0).toString();
//					System.out.println(resultStr);
//				}
//			}
//		});
 * 
 */
public class WebServiceUtils {
	// 含有3个线程的线程池
	private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

	/**
	 * WebService接口调用
	 * @param url  					WebService服务器地址
	 * @param methodName 			WebService的调用方法名
	 * @param properties 			WebService的参数
	 * @param webServiceCallBack	回调接口
	 */
	public static void callWebService(String url, final String nameSpace, final String methodName, HashMap<String, String> properties,
			final WebServiceCallBack webServiceCallBack) {
		// 创建HttpTransportSE对象，传递WebService服务器地址
		final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
		// 创建SoapObject对象
		SoapObject soapObject = new SoapObject(nameSpace, methodName);

		// SoapObject添加参数
		if (properties != null) {
			for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				soapObject.addProperty(entry.getKey(), entry.getValue());
			}
		}

		// 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
		final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 设置是否调用的是.Net开发的WebService
		soapEnvelope.setOutputSoapObject(soapObject);
		soapEnvelope.dotNet = true;
		httpTransportSE.debug = true;

		// 用于子线程与主线程通信的Handler
		final Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				// 将返回值回调到callBack的参数中
				webServiceCallBack.callBack((SoapObject) msg.obj);
			}

		};

		// 开启线程去访问WebService
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				SoapObject resultSoapObject = null;
				try {
					httpTransportSE.call(nameSpace + methodName, soapEnvelope);
					if (soapEnvelope.getResponse() != null) {
						// 获取服务器响应返回的SoapObject
						resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
					}
				} catch (HttpResponseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					e.printStackTrace();
				} finally {
					// 将获取的消息利用Handler发送到主线程
					mHandler.sendMessage(mHandler.obtainMessage(0, resultSoapObject));
				}
			}
		});
	}

	public interface WebServiceCallBack {
		public void callBack(SoapObject result);
	}

}
