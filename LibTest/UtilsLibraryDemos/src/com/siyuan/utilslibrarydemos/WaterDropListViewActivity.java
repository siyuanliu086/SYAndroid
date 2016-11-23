package com.siyuan.utilslibrarydemos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.android.common.baseui.waterdroplistview.WaterDropListView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

public class WaterDropListViewActivity extends Activity {
	private int page = 1;
	private ArrayAdapter<String> waterDropAdapter;
	private WaterDropListView waterDropListView;
	private List<String> data = new ArrayList<String>();
	
	private Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
			case 1:
				getData();
				waterDropAdapter.notifyDataSetChanged();
				waterDropListView.stopRefresh();
				break;
			case 2:
				getData();
				waterDropAdapter.notifyDataSetChanged();
				waterDropListView.stopLoadMore();
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waterdroplistview);
		
		waterDropListView = (WaterDropListView) findViewById(R.id.waterdrop_listview);
		waterDropAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
		waterDropListView.setAdapter(waterDropAdapter);
		waterDropListView.setWaterDropListViewListener(new WaterDropListView.IWaterDropListViewListener() {
			
			@Override
			public void onRefresh() {
				// 刷新
				page = 1;
				onListRefresh();
			}
			
			@Override
			public void onLoadMore() {
				// 加载更多
				page ++;
				onListLoadMore();
			}
		});
		waterDropListView.setPullLoadEnable(true);
		getData();
	}
	
	private void onListRefresh() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mHandler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	private void onListLoadMore() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					mHandler.sendEmptyMessage(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private List<String> getData(){
		data.clear();
		for(int i = 0; i < page; i++) {
			data.add("To see a world in a grain of sand,");
			data.add("And a heaven in a wild flower,");
			data.add("Hold infinity in the palm of your hand,");
			data.add("And eternity in an hour.");
			data.add("To see a world in a grain of sand,");
			data.add("And a heaven in a wild flower,");
			data.add("Hold infinity in the palm of your hand,");
			data.add("And eternity in an hour.");
		}
		return data;
	}
}
