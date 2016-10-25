package com.android.common.baseui.calendarcomponent.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.baseui.calendarcomponent.SinginMonthView;
import com.android.common.baseui.calendarcomponent.entity.CalendarInfo;
import com.android.common.baseui.calendarcomponent.theme.IDayTheme;
import com.android.common.baseui.calendarcomponent.theme.IWeekTheme;
import com.android.common.baseui.views.IconFontTextView;
import com.android.common.utils.DateUtil;
import com.common.android.utilslibrary.R;

/**
 * @TiTle SinginCircleCalendarView.java
 * @Package com.android.common.baseui.calendarcomponent.views
 * @Description 签到的日历View，用于绿色南通政务版签到
 * @Date 2016年10月25日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class SinginCircleCalendarView extends LinearLayout implements View.OnClickListener {
    private WeekView weekView;
    private SinginMonthView circleMonthView;
    private TextView textViewMonth;
    public SinginCircleCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout dateView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.utilslib_calendar_signin_date,null);
        weekView = new WeekView(context,null);
        circleMonthView = new SinginMonthView(context,null);
        
        addView(dateView, llParams);
        addView(weekView, llParams);
        addView(circleMonthView,llParams);
        
        dateView.findViewById(R.id.utilslib_calendar_icon_left).setOnClickListener(this);
        dateView.findViewById(R.id.utilslib_calendar_icon_right).setOnClickListener(this);
        
        textViewMonth = (TextView) dateView.findViewById(R.id.utilslib_calendar_tv_month);
        circleMonthView.setMonthLisener(new SinginMonthView.IMonthLisener() {
            @Override
            public void setTextMonth() {
                textViewMonth.setText(DateUtil.getMonthName(circleMonthView.getSelMonth()));
            }
        });
        // 初始值
        textViewMonth.setText(DateUtil.getMonthName(DateUtil.getCurrentMonth()));
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
    
    public void setSelectedDatys(List<String> selectedDays){
    	circleMonthView.setSelectedDays(selectedDays);
    }
    
    public void setSelectedDatys2(List<String> selectedDays){
    	circleMonthView.setSelectedDays2(selectedDays);
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
