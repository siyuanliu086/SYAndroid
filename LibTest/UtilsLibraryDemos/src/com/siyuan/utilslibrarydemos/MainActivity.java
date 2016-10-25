package com.siyuan.utilslibrarydemos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.common.baseui.progressbar.CircleProgressBar;
import com.siyuan.utilslibrarydemos.canlendar.CanlendarMainActivity;

public class MainActivity extends Activity {
	private Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Utilslib_ActivityTheme);// set theme used before setContentView()
		setContentView(R.layout.activity_main);

		CircleProgressBar circleProgressBar = (CircleProgressBar) findViewById(R.id.pw_spinner);
		circleProgressBar.setProgress(120);
		circleProgressBar.setText("AQI 120");
	}
	
	public void onClick(View view) {
		if(view.getId() == R.id.canlendar) {
			startActivity(new Intent(mContext, CanlendarMainActivity.class));
		} else if(view.getId() == R.id.swipelist) {
			startActivity(new Intent(mContext, SwipeMenuListActivity.class));
		}
	}

}
