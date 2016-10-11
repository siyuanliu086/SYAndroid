package com.android.common.baseui.webviewtookit;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.android.common.baseui.views.NumberProgressBar;
import com.android.common.utils.AttachTool;
import com.android.common.utils.MD5;
import com.android.common.utils.ToastUtil;
import com.common.android.utilslibrary.R;

/**
 * @TiTle JSWebView.java
 * @Package com.android.common.baseui.webviewtookit
 * @Description JS的HTML页面展示
 * @Date 2016年6月13日
 * @Author hjwangk
 * @Refactor
 * @Company ISoftStone ZHHB
 */
public class JSWebView extends LinearLayout implements
		AdvancedWebView.Listener {
	public Context mContext;
	/** web_html参数名称 **/
	public final static String WEBVIEW_HTML = "webview_html";
	
	protected DownloadUtil downloadUtil;
    protected String baseUrl;
	public AdvancedWebView mWebView;
	protected com.android.common.baseui.views.NumberProgressBar progressbar;
	protected View rootView;
	private String mHtml;
	private String currentTitle = null;
	public Handler mHandler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				int progress = (Integer) msg.obj;
				if (progressbar.getVisibility() == View.GONE)
					progressbar.setVisibility(View.VISIBLE);
				progressbar.setProgress(progress);
				System.out.println("===========" + progress);
			} else if(msg.what == 1) {
				if (progressbar.getVisibility() == View.VISIBLE)
					progressbar.setVisibility(View.GONE);
				AttachModel attachModel = (AttachModel) msg.obj;
				// 是否子类打开，子类允许拦截
				AttachTool.getInstance().openAttach(mContext, attachModel);
			} else if(msg.what == 2) {
				ToastUtil.showShortToast(mContext, R.string.utils_common_error_downloadfile);
			}
		}
	};
	
	public JSWebView(Context context) {
        super(context);
        initView(context);
    }

    public JSWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public JSWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }
    
    public void setCurrentTitle(String currentTitle) {
		this.currentTitle = currentTitle;
	}

	/**
     * 初始化布局内容
     * @param context
     */
    protected void initView(Context context) {
    	this.mContext = context;
		rootView = LayoutInflater.from(mContext).inflate(R.layout.utilslib_activity_advancedwebview, this);

		mWebView = (AdvancedWebView) rootView.findViewById(R.id.fragment_wv);
		progressbar = (NumberProgressBar) rootView.findViewById(R.id.progressbar_dialog_web);
		
		setUpView();
	}
    
    /**
     * 给页面添加js回调函数，必须调用这个方法
     */
    public void setJavaScriptInterface(){
    	mWebView.addJavascriptInterface(webViewTitle.getServerNeedClass(), "JsCallAndroid");
    }

    /**
     * 页面的onresum时调用
     */
	public void onResume() {
		mWebView.onResume();
		String originalUrl = mWebView.getOriginalUrl();// 最开始加载
		if (!TextUtils.isEmpty(originalUrl)
				&& originalUrl.equals(mWebView.getUrl())) {
			//如果该url地址是下载附件则自动后退
            if(mHtml != null && (mHtml.endsWith(".pdf") || mHtml.endsWith(".doc") || mHtml.endsWith("xls"))) {
            	mWebView.goBack(); // 后退
			}
		}
		mHtml = originalUrl;
	}

	/**
	 * 页面onPause时调用
	 */
	public void onPause() {
		mWebView.onPause();
	}

	/**
	 * 页面onDestroy时调用
	 */
	public void onDestroy() {
		((ViewGroup) rootView).removeView(mWebView);
		mWebView.onDestroy();
		invokeMethod(invokeMethod(mWebView, "getWebViewProvider"), "dismissZoomControl");
		if(downloadUtil != null) {			
			downloadUtil.stopDownload();
		}
		mWebView.destroy();
	}

	private Object invokeMethod(Object obj, String method) {
		if (obj == null) {
			return null;
		}
		try {
			Method method2 = obj.getClass().getDeclaredMethod(method);
			if (method2 != null) {
				method2.setAccessible(true);
				return method2.invoke(obj);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	private HashMap<String, String> parseUrlParams(String url) {
		String[] paramStr = new String[]{};
		if(url.contains("?")) {
			paramStr = url.substring(url.indexOf("?") + 1).split("&");
		}
		HashMap<String, String> paramMap = new HashMap<String, String>();
		for(int i = 0; i < paramStr.length; i ++) {
			String teamParam = paramStr[i];
			paramMap.put(teamParam.split("=")[0], teamParam.split("=")[1]);
		}
		return paramMap;
	}

	/**
	 * 浏览器客户端处理程序　
	 */
	private final class MyCilent extends WebViewClient {
		boolean isChecked = false;
		
		private boolean checkUrl(WebView view, String url) {
			isChecked = true;
			if(url.equals(mHtml)){
				return false;
			}
			mHtml = url;
			
			// 跳转第三方页面
			if(url.endsWith(".jsp") || url.endsWith(".html") || url.endsWith(".htm")) {
				Intent intent = new Intent();
    			Bundle bundle = new Bundle();
    			bundle.putString(WebViewActivity.WEBVIEW_URL, url);
    			intent.putExtras(bundle);
    			webViewTitle.gotoActivity(intent);
    			return true;
			}
			
			// 设置标题
			HashMap<String, String> params = parseUrlParams(url);
			if(params.containsKey("title_")) {
				// 解析标题	
				currentTitle = params.get("title_");
				try {
					currentTitle = URLDecoder.decode(currentTitle, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				webViewTitle.setCuttentTitle(currentTitle);
			}  
			
			// 跳转新页面
			if(params.containsKey("target") && "new".equals(params.get("target"))) {
				Intent intent = new Intent();
    			Bundle bundle = new Bundle();
    			bundle.putString(WebViewActivity.WEBVIEW_URL, url);
    			bundle.putString(WebViewActivity.WEBVIEW_TITLE, currentTitle);
    			intent.putExtras(bundle);
    			webViewTitle.gotoActivity(intent);
    			return true;
			}  
			return false;
		}

		// 处理新加载的链接，跳转到新的页面
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return checkUrl(view, url);
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			// 加载资源时的请求，非中断（）
			super.onLoadResource(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	protected void setUpView() {
		mWebView.setWebViewClient(new MyCilent());
		mWebView.setListener((Activity)mContext, this);
		mWebView.setGeolocationEnabled(false);
		mWebView.setMixedContentAllowed(true);
		mWebView.setCookiesEnabled(true);
		mWebView.setThirdPartyCookiesEnabled(true);
		mWebView.setDesktopMode(false);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		String cacheDirPath = mContext.getFilesDir().getAbsolutePath() + "/webview_cache/"; //缓存路径  
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //缓存模式  
		webSettings.setAppCachePath(cacheDirPath); //设置缓存路径  
		webSettings.setAppCacheEnabled(true); //开启缓存功能 
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				// 进度条
//				if (newProgress == 100) {
//					progressbar.setVisibility(View.GONE);
//				} else {
//					if (progressbar.getVisibility() == View.GONE)
//						progressbar.setVisibility(View.VISIBLE);
//					progressbar.setProgress(newProgress);
//				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) { // 获取到Title
				super.onReceivedTitle(view, title);
				if(webViewTitle != null && currentTitle == null){
					webViewTitle.setCuttentTitle(title);
				}
				
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) { // 获取到图标
				super.onReceivedIcon(view, icon);
			}
//			@Override
//		    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//			        ToastUtil.showShortToast(mContext, message);
//			        result.confirm();
//			        return true;
//			}
		});
		// 屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件
		mWebView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		
		mWebView.setDownloadListener(new MyWebViewDownLoadListener());
	}
	
	/**
	 * @TiTle WebViewActivity.java
	 * @Package com.iss.zhhblsnt.activity.common
	 * @Description 下载链接
	 * @Date 2016年6月1日
	 * @Author siyuan
	 * @Refactor 
	 * @Company ISoftStone ZHHB
	 */
	private class MyWebViewDownLoadListener implements DownloadListener {
		AttachModel attachModel;
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        	if(TextUtils.isEmpty(url)) {
        		return;
        	}
        	attachModel = new AttachModel();
        	if(url.endsWith("pdf")) {
        		attachModel.setAttSuffix("pdf");
        	} else if(url.endsWith("doc")) {
        		attachModel.setAttSuffix("doc");
        	} else if(url.endsWith("xls")) {
        		attachModel.setAttSuffix("xls");
//        	} else if(url.endsWith("rar")) {
//        		attachModel.setAttSuffix("rar");
//        		return;
        	} else {
        		ToastUtil.showShortToast(mContext, "文件不支持");
        		return;
        	}
        	String filePath = getBasePath() + MD5.GetMD5Code(url) + "." + attachModel.getAttSuffix();
        	attachModel.setAttPath(filePath);
        	downloadUtil = new DownloadUtil(url, filePath, new DownloadUtil.DownLoadProgressListener() {
				
				@Override
				public void onDownloadProgress(int progress) {
					sendMessage(progress);
				}
				
				private void sendMessage(int progress) {
					Message msg = mHandler.obtainMessage();
					msg.what = 0;
					msg.obj = progress;
					mHandler.sendMessage(msg);
				}

				@Override
				public void onDownloadCompleted(int state) {
					if(DownloadUtil.STATE_FAILED == state) {
						mHandler.sendEmptyMessage(2);
					} else if(DownloadUtil.STATE_SUCCESS == state){
						// 成功
						sendMessage(100);
						
						Message msg = mHandler.obtainMessage();
						msg.what = 1;
						msg.obj = attachModel;
						mHandler.sendMessage(msg);
					}
				}
			});
        	downloadUtil.startDownload();
        }
    }
	
	private String getBasePath() {
		File baseFile = mContext.getExternalFilesDir("files");
		if(baseFile == null ||!baseFile.exists()){
			baseFile.mkdirs();
        }
		return baseFile.getAbsolutePath()+ File.separator;
	}

	@Override
	public void onPageStarted(String url, Bitmap favicon) {

	}

	@Override
	public void onPageFinished(String url) {

	}

	@Override
	public void onPageError(int errorCode, String description, String failingUrl) {

	}

	@Override
	public void onDownloadRequested(String url, String userAgent,
			String contentDisposition, String mimetype, long contentLength) {

	}

	@Override
	public void onExternalPageRequest(String url) {

	}
	
	public void loadHtml(String htmlUrl) {
		if(htmlUrl == null){
			return;
		}
		mHtml = htmlUrl;
		mWebView.loadUrl(mHtml);
	}

	public AdvancedWebView getmWebView() {
		return mWebView;
	}
	private WebViewTitle webViewTitle;
	public void setWebViewTitle(WebViewTitle webViewTitle){
		this.webViewTitle = webViewTitle;
	}
	
	public interface WebViewTitle{
		/**
		 * 设置页面标题
		 * @param webViewTitle
		 */
		public void setCuttentTitle(String webViewTitle);
		/**
		 * 页面跳转到新的页面
		 * @param intent
		 */
		public void gotoActivity(Intent intent);
		/**
		 * 获取回调实体类
		 * @return
		 */
		public ServiceNeed getServerNeedClass();
	}
}
