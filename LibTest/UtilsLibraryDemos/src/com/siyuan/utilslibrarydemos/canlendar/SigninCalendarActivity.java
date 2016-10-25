package com.siyuan.utilslibrarydemos.canlendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.android.common.baseui.calendarcomponent.theme.SinginWeekTheme;
import com.android.common.baseui.calendarcomponent.views.MonthView;
import com.android.common.baseui.calendarcomponent.views.SinginCircleCalendarView;
import com.siyuan.utilslibrarydemos.R;

public class SigninCalendarActivity extends Activity {
	private SinginCircleCalendarView circleCalendarView;
	private Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signincalendar_view);
//		Calendar calendar = Calendar.getInstance();
//		int currYear = calendar.get(Calendar.YEAR);
//		int currMonth = calendar.get(Calendar.MONTH) + 1;
//		circleCalendarView.setCalendarInfos(null);
		List<String> dateList = new ArrayList<String>();
		dateList.add("2016-10-24");
		dateList.add("2016-10-23");
		dateList.add("2016-10-22");
		dateList.add("2016-10-19");
		dateList.add("2016-10-18");
		
		List<String> dateList2 = new ArrayList<String>();
		dateList2.add("2016-10-21");
		dateList2.add("2016-10-20");
		
		circleCalendarView = (SinginCircleCalendarView) findViewById(R.id.circleMonthView);
		circleCalendarView.setWeekTheme(new SinginWeekTheme(mContext));
		circleCalendarView.setSelectedDatys(dateList);
		circleCalendarView.setSelectedDatys2(dateList2);
		circleCalendarView.setDateClick(new MonthView.IDateClick() {

			@Override
			public void onClickOnDate(int year, int month, int day) {
				Toast.makeText(mContext, "点击了" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
