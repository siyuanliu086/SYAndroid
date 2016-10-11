package com.android.volley.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * @TiTle MultipartRequest.java
 * @Package com.iss.zhhb.monitor.util
 * @Description 封装Volley.Request 用于封装文件上传
 * @Date 2015年10月16日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class MultipartRequest extends Request<String> {
	private UploadMultipartEntity entity = new UploadMultipartEntity();
	private final Response.Listener<String> mListener;

	private File[] mFileParts;
	private String mFilePartName;
	private Map<String, String> mParams;
	
	private long totalFileLenght;
	
	/**
	 * 单个文件
	 * @param url
	 * @param errorListener
	 * @param listener
	 * @param filePartName
	 * @param file
	 * @param params
	 */
	public MultipartRequest(String url, Response.ErrorListener errorListener,
			Response.Listener<String> listener,
			String filePartName, File file,
			Map<String, String> params) {
		super(Method.POST, url, errorListener);
		
		mFileParts = new File[1];
		if (file != null) {
			mFileParts[0] = file;
		}
		mFilePartName = filePartName;
		mListener = listener;
		mParams = params;
		buildMultipartEntity();
		setRetryPolicy(new DefaultRetryPolicy(32 * 1000,//默认超时时间，应设置一个稍微大点儿的
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		entity.setListener(mEntityProgressListener);
		totalFileLenght = entity.getContentLength();
	}
	/**
	 * 多个文件，对应一个key
	 * @param url
	 * @param errorListener
	 * @param listener
	 * @param filePartName
	 * @param files
	 * @param params
	 */
	public MultipartRequest(String url, Response.ErrorListener errorListener,
			Response.Listener<String> listener, String filePartName,
			File[] files, Map<String, String> params) {
		super(Method.POST, url, errorListener);
		mFilePartName = filePartName;
		mListener = listener;
		mFileParts = files;
		mParams = params;
		buildMultipartEntity();
		setRetryPolicy(new DefaultRetryPolicy(18 * 1000,//默认超时时间，应设置一个稍微大点儿的
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		entity.setListener(mEntityProgressListener);
		totalFileLenght = entity.getContentLength();
	}

	private void buildMultipartEntity() {
		if (mFileParts != null && mFileParts.length > 0) {
			for (File file : mFileParts) {
				entity.addPart(mFilePartName, new FileBody(file));
			}
		}

		try {
			if (mParams != null && mParams.size() > 0) {
				for (Map.Entry<String, String> entry : mParams.entrySet()) {entity.addPart(entry.getKey(),
					new StringBody(entry.getValue(), Charset.forName("UTF-8")));
				}
			}
		} catch (Exception e) {
			VolleyLog.e("UnsupportedEncodingException");
		}
	}  

	@Override
	public String getBodyContentType() {
		return entity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			entity.writeTo(bos);
		} catch (IOException e) {
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		if (VolleyLog.DEBUG) {
			if (response.headers != null) {
				for (Map.Entry<String, String> entry : response.headers
						.entrySet()) {
					VolleyLog.d(entry.getKey() + "=" + entry.getValue());
				}
			}
		}

		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.android.volley.Request#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		VolleyLog.d("getHeaders");
		Map<String, String> headers = super.getHeaders();

		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}


		return headers;
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}
	
	private UploadMultipartEntity.ProgressListener mEntityProgressListener = new UploadMultipartEntity.ProgressListener() {
		
		@Override
		public void transferred(long num) {
			if(mProgressListener != null) {
				int progress = (int) (num * 100 / totalFileLenght);
				mProgressListener.onProgressed(progress);
			}
		}
	};
	
	public interface ProgressListener {
		void onProgressed(int progress);
	}
	private ProgressListener mProgressListener;
	public void setProgressListener(ProgressListener listener) {
		this.mProgressListener = listener;
	}

}
