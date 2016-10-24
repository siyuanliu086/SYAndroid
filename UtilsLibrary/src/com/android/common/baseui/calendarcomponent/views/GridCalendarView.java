package com.android.common.baseui.calendarcomponent.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.baseui.calendarcomponent.GridMonthView;
import com.android.common.baseui.calendarcomponent.entity.CalendarInfo;
import com.android.common.baseui.calendarcomponent.theme.IDayTheme;
import com.android.common.baseui.calendarcomponent.theme.IWeekTheme;
import com.android.common.utils.DateUtil;
import com.common.android.utilslibrary.R;

/**
 * Created by Administrator on 2016/7/31.
 */
public class GridCalendarView extends LinearLayout implements View.OnClickListener {
    private WeekView weekView;
    private GridMonthView gridMonthView;
    private TextView textViewYear,textViewMonth;
    public GridCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.utilslib_calendar_grid_date,null);
        weekView = new WeekView(context,null);
        gridMonthView = new GridMonthView(context,null);
        
        addView(view,llParams);
        addView(weekView,llParams);
        addView(gridMonthView,llParams);

        view.findViewById(R.id.utilslib_calendar_icon_left).setOnClickListener(this);
        view.findViewById(R.id.utilslib_calendar_icon_right).setOnClickListener(this);
        textViewYear = (TextView) view.findViewById(R.id.utilslib_calendar_tv_year);
        textViewMonth = (TextView) view.findViewById(R.id.utilslib_calendar_tv_month);
        gridMonthView.setMonthLisener(new MonthView.IMonthLisener() {
            @Override
            public void setTextMonth() {
                textViewYear.setText(gridMonthView.getSelYear()+"年");
                textViewMonth.setText((gridMonthView.getSelMonth() + 1)+"月");
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
        gridMonthView.setDateClick(dateClick);
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
        gridMonthView.setCalendarInfos(calendarInfos);
    }

    public void setDayTheme(IDayTheme theme){
        gridMonthView.setTheme(theme);
    }

    public void setWeekTheme(IWeekTheme weekTheme){
        weekView.setWeekTheme(weekTheme);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.utilslib_calendar_icon_left){
            gridMonthView.onLeftClick();
        }else{
            gridMonthView.onRightClick();
        }
    }
}