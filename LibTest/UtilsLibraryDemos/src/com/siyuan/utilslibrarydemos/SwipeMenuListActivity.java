package com.siyuan.utilslibrarydemos;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.common.baseui.swipemenulistview.SwipeMenu;
import com.android.common.baseui.swipemenulistview.SwipeMenuCreator;
import com.android.common.baseui.swipemenulistview.SwipeMenuItem;
import com.android.common.baseui.swipemenulistview.SwipeMenuListView;
import com.android.common.utils.toast.Toasty;
import com.siyuan.utilslibrarydemos.canlendar.CanlendarMainActivity;

public class SwipeMenuListActivity extends Activity {
	private Context mContext = this;
	private List<ApplicationInfo> mAppList;
	private AppAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		mAppList = getPackageManager().getInstalledApplications(0);

		SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.listView);
		mAdapter = new AppAdapter();
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new SwipeMenuListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				ToastUtil.showShortToast(mContext, position + "onClick");
				Toasty.Config.getInstance().setSuccessColor(Color.parseColor("#FF3300")).apply();
				Toasty.success(mContext, "Success!", Toast.LENGTH_SHORT, true).show();
			}
		});
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					createMenu1(menu);
					break;
				case 1:
					createMenu2(menu);
					break;
				case 2:
					createMenu3(menu);
					break;
				}
			}

			private void createMenu1(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18, 0x5E)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.demo_like);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.demo_good);
				menu.addMenuItem(item2);
			}

			private void createMenu2(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0, 0x3F)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.icon_demo_important);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.icon_demo_discuss);
				menu.addMenuItem(item2);
			}

			private void createMenu3(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.icon_demo_about);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.icon_demo_share);
				menu.addMenuItem(item2);
			}
		};
		// set creator
		listView.setMenuCreator(creator);

		// step 2. listener item click event
		listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					break;
				case 1:
					// delete
					// delete(item);
					mAppList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});
	}

	public void onClick(View view) {
		if (view.getId() == R.id.canlendar) {
			startActivity(new Intent(mContext, CanlendarMainActivity.class));
		}
	}

	class AppAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mAppList.size();
		}

		@Override
		public ApplicationInfo getItem(int position) {
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getViewTypeCount() {
			// menu type count
			return 3;
		}

		@Override
		public int getItemViewType(int position) {
			// current menu type
			return position % 3;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(), R.layout.item_list_app, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ApplicationInfo item = getItem(position);
			holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
			holder.tv_name.setText(item.loadLabel(getPackageManager()) + "\n" + item.packageName);
			return convertView;
		}

		class ViewHolder {
			ImageView iv_icon;
			TextView tv_name;

			public ViewHolder(View view) {
				iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				tv_name = (TextView) view.findViewById(R.id.tv_name);
				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}
}
