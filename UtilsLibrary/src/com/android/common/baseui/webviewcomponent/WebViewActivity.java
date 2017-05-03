package com.android.common.baseui.webviewcomponent;

import java.io.File;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.common.baseui.BaseActivity;
import com.android.common.baseui.views.NumberProgressBar;
import com.android.common.utils.AttachTool;
import com.android.common.utils.toast.ToastUtil;
import com.common.android.utilslibrary.R;
import com.java.utils.MD5;

/**
 * @TiTle WebViewActivity.java
 * @Package com.iss.zhhblsnt.activity.common
 * @Description HTML页面展示：支持下载并展示附件
 * @Date 2016年6月13日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class WebViewActivity extends BaseActivity implements AdvancedWebView.Listener{
	private Context mContext = this;
	/** web_url参数名称 **/
	public final static String WEBVIEW_URL = "webview_url";
	/** web_url参数名称 **/
	public final static String WEBVIEW_CONTENT = "webview_content";
	/** web_title参数名称 **/
	public final static String WEBVIEW_TITLE = "webview_title";
	/** 界面横竖屏 **/
	public final static String WEBVIEW_SCREEN = "webview_screen";

	public AdvancedWebView mWebView;
	protected String mUrl;
	protected String mHtmlContent;
	protected String mTitle = null;
	protected NumberProgressBar progressbar;
	protected LinearLayout btnLL;
	protected Button nextBtn;
	protected View rootView;
	
	protected DownloadUtil downloadUtil;
	
	private Handler mHandler = new Handler(Looper.getMainLooper()){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0) {
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
				if(!openAttach(mContext, attachModel)) {
					AttachTool.getInstance().openAttach(mContext, attachModel);
				}
			} else if(msg.what == 2) {
				ToastUtil.showShortToast(mContext, "附件下载失败！");
			}
		}
	};

	@Override
	protected void initView() {
		rootView = LayoutInflater.from(this).inflate(R.layout.utilslib_activity_advancedwebview, null);
    	mainContentLayout.addView(rootView);
    	
		baseTitleBar.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE);
		mWebView = (AdvancedWebView) rootView.findViewById(R.id.fragment_wv);
		nextBtn = (Button) rootView.findViewById(R.id.webview_next);
		btnLL = (LinearLayout) rootView.findViewById(R.id.webview_line);
		progressbar = (NumberProgressBar) rootView.findViewById(R.id.progressbar_dialog_web);
		
		// webView.setInitialScale(100);
		//mWebView.setDrawingCacheEnabled(false);
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			mUrl = bundle.getString(WEBVIEW_URL);
			mHtmlContent = bundle.getString(WEBVIEW_CONTENT);
			mTitle = bundle.getString(WEBVIEW_TITLE);
			int screen = bundle.getInt(WEBVIEW_SCREEN);
			if(screen == 0) {
				// 竖屏
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		}

		if (null != mTitle) {
			setFunctionTitle(mTitle);
		} else {
			setFunctionTitle("");
		}
		
		// 优先加载本地文本
		if(!TextUtils.isEmpty(mHtmlContent)) {
//			mWebView.loadData(mHtmlContent, "text/html", "UTF-8");// 乱码
			mWebView.loadDataWithBaseURL(null, mHtmlContent, "text/html", "utf-8",null);// 正常
		} else {
			mWebView.loadUrl(mUrl);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mWebView.onResume();
		String originalUrl = mWebView.getOriginalUrl();// 最开始加载
		if(!TextUtils.isEmpty(originalUrl) && originalUrl.equals(mWebView.getUrl())) {
			mWebView.goBack();   //后退 
		}
	}

	@Override
	protected void onPause() {
		mWebView.onPause();
		super.onPause();
	}
	
	@Override
	public void onDestroy() {
		if(downloadUtil != null) {			
			downloadUtil.stopDownload();
		}
		((ViewGroup) rootView).removeView(mWebView);  
		mWebView.onDestroy();
		invokeMethod(invokeMethod(mWebView, "getWebViewProvider"), "dismissZoomControl");
		mWebView.destroy();
		super.onDestroy();
	}
	
	private Object invokeMethod(Object obj,String method){
		if(obj==null){
			return null;
		}
		try {
			Method method2 = obj.getClass().getDeclaredMethod(method);
			if(method2 != null){
				method2.setAccessible(true);
				return method2.invoke(obj);
			}
		} catch (Exception e) {
		}
		return null;
	}


	/**
	 * 浏览器客户端处理程序　
	 */
	private final class MyCilent extends WebViewClient {
		int i = 0;
		// 处理新加载的链接
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// 拦截处理URL跳转，返回true代表当前应用来处理url（拦截并做一些其他响应），返回false则代表当前webview来处理
//			if (url.indexOf("http://www.example.com") != -1) { 
//				// 调用系统默认浏览器处理url 
//				view.stopLoading(); 
//				view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); 
//				return true; 
//			} 
//			return false; 
			    
			if (i == 0) {
				if (!mUrl.equals(url)) {
					mUrl = url;
				}
				i++;
			}
			view.loadUrl(url);
			return true;
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
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		}
		
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//			super.onReceivedSslError(view, handler, error);
			handler.proceed();  // 接受所有网站的证书
		}
	}


	@Override
	protected void initData() {

	}

	@Override
	protected void onNetStateChanged(boolean isOn) {
		baseTitleBar.setNetWorkState(isOn);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void setUpView() {
		mWebView.setWebViewClient(new MyCilent());
		mWebView.setListener(this, this);
		mWebView.setGeolocationEnabled(false);
		mWebView.setMixedContentAllowed(true);
		mWebView.setCookiesEnabled(true);
		mWebView.setThirdPartyCookiesEnabled(true);
		mWebView.setDesktopMode(false);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
//		webSettings.setLayoutAlgorithm(LayoutAlgorithm.TEXT_AUTOSIZING);
//		webSettings.setTextZoom(200);//字号放大
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true); 
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				// 进度条
				if (newProgress == 100) {
					progressbar.setVisibility(View.GONE);
				} else {
					if (progressbar.getVisibility() == View.GONE)
						progressbar.setVisibility(View.VISIBLE);
					progressbar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) { // 获取到Title
				super.onReceivedTitle(view, title);
				if(TextUtils.isEmpty(mTitle)) {
					setFunctionTitle(title);
				}
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) { // 获取到图标
				super.onReceivedIcon(view, icon);
			}
		});
		mWebView.setDownloadListener(new MyWebViewDownLoadListener());
		// 屏蔽掉长按事件 因为webview长按时将会调用系统的复制控件
		mWebView.setOnLongClickListener(new View.OnLongClickListener() {  
            
	          @Override  
	          public boolean onLongClick(View v) {  
	              return true;  
	          }  
	      });
		baseTitleBar.setLeftOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				nextBtnOnClick.NextBtnOnClickListener(v);
				
			}
		});
	}
	private OnNextBtnOnClick nextBtnOnClick;
	
	public void setNextBtnOnClick(OnNextBtnOnClick nextBtnOnClick) {
		this.nextBtnOnClick = nextBtnOnClick;
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

	/**
	 * 设置下方底部按钮文字
	 * @param resId
	 */
	public void setNextBtnText(int resId){
		btnLL.setVisibility(View.VISIBLE);
		nextBtn.setText(resId);
	}
	public interface OnNextBtnOnClick{
		void NextBtnOnClickListener(View v);
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
	
	/**
	 * WebView 打开附件，子类若拦截打开，重写此方法并返回true即可
	 * @param context
	 * @param attachModel
	 * @return
	 */
	public boolean openAttach(Context context, AttachModel attachModel) {
		return false;
	}
}
