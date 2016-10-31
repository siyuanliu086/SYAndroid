package com.siyuan.utilslibrarydemos;

import com.android.common.baseui.views.SortListView;

import android.app.Activity;
import android.os.Bundle;

public class SortListViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_sortlistview);
		SortListView listView = new SortListView(this);
		setContentView(listView);
		listView.setCurrent("当前选择城市", R.string.utilslib_icon_location, "北京");
	}
	
	

}
