package com.siyuan.utilslibrarydemos;

import com.android.common.baseui.progressbar.CircleProgressBar;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Utilslib_ActivityTheme);// set theme used before setContentView()
		setContentView(R.layout.activity_main);

		CircleProgressBar circleProgressBar = (CircleProgressBar) findViewById(R.id.pw_spinner);
		circleProgressBar.setProgress(120);
		circleProgressBar.setText("AQI 120");
	}

}
