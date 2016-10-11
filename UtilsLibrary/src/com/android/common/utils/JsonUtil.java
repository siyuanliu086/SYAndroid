package com.android.common.utils;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
/**
 * @TiTle JsonUtil.java
 * @Package com.android.common.utils
 * @Description Json工具类
 * @Date 2016年10月11日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class JsonUtil {
	/**
	 * 根据key名称获取value
	 * @param jsonString
	 * @param keyName
	 * @return
	 */
	public static String getValueWithKey(String jsonString, String keyName){
		String result="";
		try {
			if (null!=jsonString&&!"".equals(jsonString)) {
				JSONObject jObj = new JSONObject(jsonString);
				if (null!=jObj) {
					result=jObj.optString(keyName);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据key名称获取Json中List<Bean>
	 * @param jsonString
	 * @param keyName
	 * @param clz
	 * @return
	 */
	public static <T> List<T> getList(String jsonString,String keyName,Class<T> clz){
		List<T> parseArray =null;
		String json=getValueWithKey(jsonString,keyName);
		if (!"".equals(json)) {
			try {
				parseArray=JSON.parseArray(json, clz);
			} catch (Exception e) {
				LogUtils.e("解析"+clz+"时发生异常,请检查实体中是否嵌套了其他实体");
				e.printStackTrace();
			}
		}
		return parseArray;
	}
	
	/**
	 * 没有key值，直接转换成List<Bean>
	 * @param jsonString
	 * @param clz
	 * @return
	 */
	public static <T> List<T> getArrayList(String jsonString, Class<T> clz){
		List<T> parseArray =null;
		if (!"".equals(jsonString)) {
			parseArray=JSON.parseArray(jsonString, clz);
		}
		return parseArray;
	}
	
}
