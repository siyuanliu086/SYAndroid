package com.android.common.baseui.calendarcomponent.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.baseui.calendarcomponent.ADCircleMonthView;
import com.android.common.baseui.calendarcomponent.entity.CalendarInfo;
import com.android.common.baseui.calendarcomponent.theme.IDayTheme;
import com.android.common.baseui.calendarcomponent.theme.IWeekTheme;
import com.common.android.utilslibrary.R;
import com.java.utils.DateUtil;

/**
 * Created by Administrator on 2016/8/7.
 */
public class ADCircleCalendarView extends LinearLayout implements View.OnClickListener {
    private WeekView weekView;
    private ADCircleMonthView circleMonthView;
    private TextView textViewYear,textViewMonth;
    public ADCircleCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.utilslib_calendar_grid_date,null);
        weekView = new WeekView(context,null);
        circleMonthView = new ADCircleMonthView(context,null);
        
        addView(view,llParams);
        addView(weekView,llParams);
        addView(circleMonthView,llParams);

        view.findViewById(R.id.utilslib_calendar_icon_left).setOnClickListener(this);
        view.findViewById(R.id.utilslib_calendar_icon_right).setOnClickListener(this);
        textViewYear = (TextView) view.findViewById(R.id.utilslib_calendar_tv_year);
        textViewMonth = (TextView) view.findViewById(R.id.utilslib_calendar_tv_month);
        circleMonthView.setMonthLisener(new MonthView.IMonthLisener() {
            @Override
            public void setTextMonth() {
                textViewYear.setText(circleMonthView.getSelYear()+"年");
                textViewMonth.setText((circleMonthView.getSelMonth() + 1)+"月");
            }
        });
        // 初始值
        textViewYear.setText(DateUtil.getCurrentYear() + "年");
        textViewMonth.setText(DateUtil.getCurrentMonth() + "月");
    }

    /**
     * 设置日历点击事件
     * @param dateClick
     */
    public void setDateClick(MonthView.IDateClick dateClick){
        circleMonthView.setDateClick(dateClick);
    }

    /**
     * 设置星期的形式
     * @param weekString
     * 默认值	"日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString){
        weekView.setWeekString(weekString);
    }

    public void setCalendarInfos(List<CalendarInfo> calendarInfos){
        circleMonthView.setCalendarInfos(calendarInfos);
    }

    public void setDayTheme(IDayTheme theme){
        circleMonthView.setTheme(theme);
    }

    public void setWeekTheme(IWeekTheme weekTheme){
        weekView.setWeekTheme(weekTheme);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.utilslib_calendar_icon_left){
            circleMonthView.onLeftClick();
        }else{
            circleMonthView.onRightClick();
        }
    }
}
