package com.android.common.baseui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.common.baseui.views.SideBar.OnTouchingLetterChangedListener;
import com.common.android.utilslibrary.R;

/**
 * @TiTle SortListView.java
 * @Package com.iss.zhhb.pm25.view
 * @Description 组合控件，组合搜索框、ListView、侧边拼音栏，内部封装各方法
 * @Date 2016年10月27日
 * @Author siyuan
 * @Refactor
 * @Company ISoftStone ZHHB
 */
public class SortListView extends LinearLayout {
	private static final String TAG = "sort_listview";
	private Context mContext;
	
	private TextView currentName;
	private TextView currentCity;
	private IconFontTextView currentShowIcon;
	private ListView showListView;
	private ListView resultListView;
	
	private LinearLayout searchBarLayout;// 搜索框
	private LinearLayout currentLayout;// 已选择信息显示
	/** A-Z listview */
	private SideBar pinyinSideBar;
	/** 存放存在的汉语拼音首字母和与之对应的列表位置 */
	public SearchView mClearEditText;
	/** 对话框首字母textview */
	public TextView pinyinTextDialog;
	/**搜索无结果*/
	public TextView listviewNodataText;
	
	public SortListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		this.mContext = context;
		initView();
	}

	public SortListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		initView();
	}

	public SortListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public SortListView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	protected void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.utilslib_layout_sort_listview, this);
		// 搜索框
		searchBarLayout = (LinearLayout) findViewById(R.id.utilslib_sortlist_serachbar);
		mClearEditText = (SearchView) findViewById(R.id.utilslib_sortlist_serach_name);

		// 当前提示
		currentLayout = (LinearLayout) findViewById(R.id.utilslib_sortlist_current_layout);
		currentName = (TextView) findViewById(R.id.utilslib_sortlist_current_name);
		currentShowIcon = (IconFontTextView) findViewById(R.id.utilslib_sortlist_current_icon);
		currentCity = (TextView) findViewById(R.id.utilslib_sortlist_current_text);

		// 主显示列表
		showListView = (ListView) findViewById(R.id.utilslib_sortlist_listview);
		// 搜索结果列表
		resultListView = (ListView) findViewById(R.id.utilslib_sortlist_listview_result);

		// 侧边栏
		pinyinTextDialog = (TextView) findViewById(R.id.utilslib_sortlist_sidebar_dialog);
		pinyinSideBar = (SideBar) findViewById(R.id.utilslib_sortlist_sidrbar);
		pinyinSideBar.setTextView(pinyinTextDialog);

		listviewNodataText = (TextView) findViewById(R.id.utilslib_sortlist_tv_noresult);
		setListener();
	}

	/**
	 * 设置当前提示
	 * 
	 * @param tip
	 * @param value
	 */
	public void setCurrent(String tip, int cionFontResId, String value) {
		currentLayout.setVisibility(View.VISIBLE);
		currentName.setText(tip);
		if(cionFontResId == 0) {
			currentShowIcon.setVisibility(View.GONE);
		} else {			
			currentShowIcon.setText(cionFontResId);
		}
		currentCity.setText(value);
	}
	
	/**
	 * 搜索无结果提示，允许设置，默认（抱歉，没有找到相关结果）
	 * @param textResId
	 */
	public void setSearchNoResult(int textResId) {
		listviewNodataText.setText(textResId);
	}
	
	/**
	 * 设置列表适配器
	 * @param adapter
	 */
	public void setListAdapter(BaseAdapter adapter) {
		showListView.setAdapter(adapter);
	}
	
	/**
	 * 搜索框是否可见
	 * @param visibility
	 */
	public void setSearbarVisiable(int visibility) {
		searchBarLayout.setVisibility(visibility);
	}
	
	/**
	 * 搜索框是否可见
	 * @param visibility
	 */
	public void setSearbarHint(int hintResId) {
		mClearEditText.setHint(hintResId);
	}
	
	/**
	 * 当前选择是否可见
	 * @param visibility
	 */
	public void setCurrentVisiable(int visibility) {
		currentLayout.setVisibility(visibility);
	}
	
	/**
	 * 侧边导航栏是否可见
	 * @param visibility
	 */
	public void setSortbarVisible(int visibility) {
		pinyinSideBar.setVisibility(visibility);
	}
	
	/**
	 * 设置搜索结果适配器
	 * @param adapter
	 */
	public void setSearchResultListAdapter(BaseAdapter adapter) {
		resultListView.setAdapter(adapter);
	}
	
	/**
	 * 当前页面类型：0 正常模式 1 搜索结果模式
	 * @return
	 */
	public int getShowType() {
		return showListView.getVisibility() == 0 ? 0 : 1;
	}
	
	private boolean autoClearFlag = false;
	public void searchReset() {
		autoClearFlag = true;
		mClearEditText.setText("");
		mClearEditText.clearFocus();
		
		showListView.setVisibility(View.VISIBLE);
		resultListView.setVisibility(View.GONE);
	}
	
	public void setShowNoData(boolean isNoData) {
		if(isNoData) {
			resultListView.setVisibility(View.GONE);
			showListView.setVisibility(View.GONE);
			listviewNodataText.setVisibility(View.VISIBLE);
		} else {
			resultListView.setVisibility(View.VISIBLE);
			showListView.setVisibility(View.GONE);
			listviewNodataText.setVisibility(View.GONE);
		}
	}
	
	public void setListViewSelection(int position) {
		if(showListView.getVisibility() == View.VISIBLE && showListView.getAdapter().getCount() > position) {			
			showListView.setSelection(position);
		} else if(resultListView.getVisibility() == View.VISIBLE && resultListView.getAdapter().getCount() > position) {	
			resultListView.setSelection(position);
		}
	}
	
	public ListView getShowListView() {
		return showListView;
	}
	
	public ListView getResultListView() {
		return resultListView;
	}

	/**
	 * 设置监听
	 */
	protected void setListener() {
		/** sideBar的滑动改变事件 */
		pinyinSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				Log.i(TAG, "onTouchingLetterChanged : " + s);
				if(myListener != null)
					myListener.onTouchingLetterChanged(s);
			}
		});
		/** 列表每一项的点击事件 */
		showListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(myListener != null)
					myListener.onListItemClickListener(parent, view, position, id);
			}
		});
		/** 搜索列表每一项的点击事件 */
		resultListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(myListener != null)
					myListener.onResultListItemClickListener(parent, view, position, id);
			}
		});

		/** 搜索栏内容改变响应事件 */
		mClearEditText.setOnOnSearchListener(new SearchView.OnSearchListener() {
			
			@Override
			public void afterTextChanged(String key) {
				// 自动清除时不回调
				if(!autoClearFlag && myListener != null) {	
					Log.i(TAG, "onTextChanged : " + key);
					myListener.onTextChanged(key);
				}
				autoClearFlag = false;
			}
			
			@Override
			public void onClearClick() {
				mClearEditText.clearFocus();
				
				showListView.setVisibility(View.VISIBLE);
				resultListView.setVisibility(View.GONE);
			}
		});
	}

	public SortListViewListener myListener;

	public void setSortListViewListener(SortListViewListener myListener) {
		this.myListener = myListener;
	}

	public interface SortListViewListener {
		void onTextChanged(String key);

		void onListItemClickListener(AdapterView<?> parent, View view, int position, long id);

		void onResultListItemClickListener(AdapterView<?> parent, View view, int position, long id);

		void onTouchingLetterChanged(String s);
	}

}
