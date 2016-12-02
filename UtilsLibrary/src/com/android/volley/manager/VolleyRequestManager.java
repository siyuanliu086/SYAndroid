package com.android.volley.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * @TiTle VolleyRequestManager.java
 * @Package com.iss.zhhb.monitor.util
 * @Description Volley请求封装
 * @Date 2015年10月27日
 * @Author siyuan
 * @Refactor siyuan 2015-11-30
 * @Company ISoftStone ZHHB
 */
public class VolleyRequestManager {
	public static final String TAG = "VolleyRequest";
	
	/**
	 * 原始Get提交方法
	 * @param mContext
	 * @param url
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyGetRequest(Context mContext, 
			String url, ResponseListener mResponseListener, ErrorListener errorListener) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);

		// 封装登录请求参数
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.GET, url, null, mResponseListener, errorListener) {

			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}
		};
		requestQueue.add(jsonRequest);
	}
	
	/**
	 * 原始Get提交方法
	 * @param mContext
	 * @param url
	 * @param token
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyGetRequest(Context mContext, 
			String url, final String token, ResponseListener mResponseListener, ErrorListener errorListener) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		
		// 封装登录请求参数
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.GET, url, null, mResponseListener, errorListener) {
			
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("token", token);
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}
		};
		requestQueue.add(jsonRequest);
	}
	
	/**
	 * 原始Get提交方法
	 * @param mContext
	 * @param url
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyGetStringRequest(Context mContext, 
			String url, ResponseListenerString mResponseListener, ErrorListener errorListener) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		StringRequest stringRequest = new StringRequest(url, mResponseListener, errorListener){
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}
		};
		requestQueue.add(stringRequest);
	}
	
	/**
	 * volleyPostRequest 原始POST提交方法
	 * @param mContext
	 * @param url
	 * @param reuqestParams
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyPostRequest(Context mContext,final String url, JSONObject reuqestParams, 
			ResponseListener mResponseListener, ErrorListener errorListener) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		
		// 封装登录请求参数
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, reuqestParams, mResponseListener, errorListener) {
			
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}
		};
		requestQueue.add(jsonRequest);
	}
	
	/**
	 * volleyPostRequest 原始POST提交方法
	 * @param mContext
	 * @param url
	 * @param token
	 * @param reuqestParams
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyPostRequest(Context mContext, final String url, final String token, JSONObject reuqestParams, 
			ResponseListener mResponseListener, ErrorListener errorListener) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		
		// 封装登录请求参数
		JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
				Method.POST, url, reuqestParams, mResponseListener, errorListener) {
			
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("token", token);
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}
		};
		requestQueue.add(jsonRequest);
	}
	
	/**
	 * volleyPostRequest 原始POST提交方法
	 * @param mContext
	 * @param url
	 * @param reuqestParams
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyPostRequest(Context mContext,final String url, JSONArray reuqestParams, 
			ResponseListenerArray mResponseListener, ErrorListener errorListener) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		// 封装登录请求参数
		JsonArrayRequest arrayRequest = new JsonArrayRequest(Method.POST, url, reuqestParams, mResponseListener, errorListener) {
			
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return headers;
			}
		};
		requestQueue.add(arrayRequest);
	}
	
	/**
	 * 封装后POST一个HashMap
	 * @param mContext
	 * @param url
	 * @param params
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyPostRequest(Context mContext, String url, Map<String, ? extends Object> params, 
			ResponseListener mResponseListener, ErrorListener errorListener) {
		JSONObject reuqestParams = new JSONObject(params);
		volleyPostRequest(mContext, url, reuqestParams, mResponseListener, errorListener);
	}
	
	/**
	 * 封装后POST一个HashMap
	 * @param mContext
	 * @param url
	 * @param token
	 * @param params
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyPostRequest(Context mContext, String url, String token, Map<String, ? extends Object> params, 
			ResponseListener mResponseListener, ErrorListener errorListener) {
		JSONObject reuqestParams = new JSONObject(params);
		volleyPostRequest(mContext, url, token, reuqestParams, mResponseListener, errorListener);
	}

	/**
	 * Volley 提交文件(多个)
	 * @param mContext
	 * @param url 地址
	 * @param errorListener 错误监听
	 * @param responseListener 返回监听 
	 * @param filePartName 文件部分名
	 * @param file 文件
	 * @param params 附带参数
	 */
	public static final void volleyPostFiles(Context mContext, String url, Response.ErrorListener errorListener,
			Response.Listener<String> responseListener, 
			MultipartRequest.ProgressListener progressListener,
			String filePartName, File[] file,
			Map<String, String> params) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		MultipartRequest multipartRequest = new MultipartRequest(url, errorListener, responseListener, filePartName, file, params);
		multipartRequest.setProgressListener(progressListener);
		requestQueue.add(multipartRequest);
	}
	
	/**
	 * Volley 提交单个文件
	 * @param mContext
	 * @param url 地址
	 * @param errorListener 错误监听
	 * @param responseListener 返回监听 
	 * @param filePartName 文件部分名
	 * @param file 文件
	 * @param params 附带参数
	 */
	public static final void volleyPostFile(Context mContext, String url, Response.ErrorListener errorListener,
			Response.Listener<String> responseListener, 
			MultipartRequest.ProgressListener progressListener,
			String filePartName, File file,
			Map<String, String> params) {
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		MultipartRequest multipartRequest = new MultipartRequest(url, errorListener, responseListener, filePartName, file, params);
		multipartRequest.setProgressListener(progressListener);
		requestQueue.add(multipartRequest);
	}
	
	/**
	 * Volley 下载文件
	 * @param mContext
	 * @param url
	 * @param mResponseListener
	 * @param errorListener
	 */
	public static final void volleyRequestFile(Context mContext, final String url, 
			ResponseListenerBitmap mResponseListener, ErrorListener errorListener) {
		Log.i(TAG, "download image " + url);
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		@SuppressWarnings("deprecation")
		ImageRequest imageRequest = new ImageRequest(url, mResponseListener, 0, 0, Config.RGB_565, errorListener);
		requestQueue.add(imageRequest);
	}
//	
//	private static BitmapCache bitmapCache = new BitmapCache();
//	
//	public static final void deleteTargetCache(String url) {
//		if(bitmapCache != null) {			
//			bitmapCache.deleteTargetCache(url);
//		}
//	}
//	
//	/**
//	 * Volley ImageView设置网络图片，实现缓存
//	 * <b>(记得还有NetworkImageView)</b>
//	 * <hr>
//	 * @param mContext
//	 * @param url
//	 * @param mResponseListener
//	 * @param errorListener
//	 */
//	public static final void volleyRequestImage(Context mContext, ImageView imageView, String url, 
//			int loadingImgRes, int failedImgRes) {
//		Log.i(TAG, "volleyRequestImage : " + url);
//		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//	    ImageListener listener = ImageLoader.getImageListener(imageView, loadingImgRes, failedImgRes);  
//		ImageLoader imageLoader = new ImageLoader(requestQueue, bitmapCache);
//	    imageLoader.get(url, listener);  
//	}
//	/**
//	 * Volley ImageView设置网络图片，实现缓存，优化控制大小
//	 * <b>(记得还有NetworkImageView)</b>
//	 * <hr>
//	 * @param mContext
//	 * @param url
//	 * @param mResponseListener
//	 * @param errorListener
//	 */
//	public static final void volleyRequestImage(Context mContext, ImageView imageView, String url, 
//			int loadingImgRes, int failedImgRes, int imgWidth, int imgHeight) {
//		Log.i(TAG, "volleyRequestImage : " + url);
//		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
//		ImageListener listener = ImageLoader.getImageListener(imageView, loadingImgRes, failedImgRes);  
//		ImageLoader imageLoader = new ImageLoader(requestQueue, bitmapCache);
//		imageLoader.get(url, listener, imgWidth, imgHeight);
//	}
//	
//	/**
//	 * @TiTle VolleyRequestManager.java
//	 * @Package com.android.volley.manager
//	 * @Description 图片缓存设置，本地缓存大小设置30M
//	 * @Date 2015年12月23日
//	 * @Author siyuan
//	 * @Refactor 
//	 * @Company ISoftStone ZHHB
//	 */
//    private static class BitmapCache implements ImageCache {  
//      
//        private LruCache<String, Bitmap> mCache;  
//      
//        public BitmapCache() {  
//            int maxSize = 30 * 1024 * 1024;  
//            mCache = new LruCache<String, Bitmap>(maxSize) {  
//                @Override  
//                protected int sizeOf(String key, Bitmap bitmap) {  
//                    return bitmap.getRowBytes() * bitmap.getHeight();  
//                }  
//            };  
//        }  
//      
//        @Override  
//        public Bitmap getBitmap(String url) {  
//            return mCache.get(url);  
//        }  
//      
//        @Override  
//        public void putBitmap(String url, Bitmap bitmap) {  
//            mCache.put(url, bitmap);  
//        }  
//        
//        public void deleteTargetCache(String url) {
//        	// siyuan 2016年2月26日
//        	// 根据Volley ImageLoader的Cache实现原理，Cache的Key = 
//        	// StringBuilder(url.length() + 12).append("#W").append(maxWidth)
//            // .append("#H").append(maxHeight).append("#S").append(scaleType.ordinal()).append(url).toString()
//        	Map<String, Bitmap> cacheCopy = mCache.snapshot();
//        	for(String key : cacheCopy.keySet()) {
//        		if(key.endsWith(url)) {
//        			mCache.remove(key);
//        		}
//        	}
//        }
//    }  
	
	public interface ResponseListenerBitmap extends Response.Listener<Bitmap> {}
	public interface ResponseListener extends Response.Listener<JSONObject> {}
	public interface ResponseListenerArray extends Response.Listener<JSONArray> {}
	public interface ResponseListenerString extends Response.Listener<String> {}
	public interface ErrorListener extends Response.ErrorListener {}
	
	
	public interface UploadProgressListener extends MultipartRequest.ProgressListener{}
	

}
