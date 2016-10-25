package com.android.common.baseui.calendarcomponent;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import com.android.common.baseui.calendarcomponent.theme.SinginDayTheme;
import com.android.common.baseui.calendarcomponent.views.MonthView;
import com.android.common.utils.DateUtil;
import com.android.common.utils.DensityUtil;

/**
 * @TiTle SinginMonthView.java
 * @Package com.android.common.baseui.calendarcomponent
 * @Description 签到（核心绘制），绿色南通-政务版
 * @Date 2016年10月25日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class SinginMonthView extends MonthView {
	protected List<String> selectedDays2 = new ArrayList<String>();

    public void setSelectedDays2(List<String> selectedDays) {
    	this.selectedDays2 = selectedDays;
    	invalidate();
    }
    
    protected boolean isSelectedDay2(int year, int month, int day) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(year).append("-").append(month).append("-").append(day);
    	if(selectedDays2.contains(sb.toString())) {
    		return true;
    	}
		return false;
	}
    
    public SinginMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawLines(Canvas canvas, int rowsCount) {
        int rightX = getWidth();
        Path path;
        float startX = 0;
        float endX = rightX;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(theme.colorLine());
        for(int row = 1; row <= rowsCount ;row++){
            float startY = row * rowSize;
            path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(endX, startY);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void drawBG(Canvas canvas, int column, int row, int year, int month, int day) {
        float startRecX = columnSize * column + 1;
        float startRecY = rowSize * row +1;
        float endRecX = startRecX + columnSize - 2 * 1;
        float endRecY = startRecY + rowSize - 2 * 1;
        float cx = (startRecX + endRecX) / 2;
        float cy = (startRecY + endRecY) / 2;
        float radius = columnSize < (rowSize * 0.6) ? columnSize / 2 : (float)(rowSize * 0.6) / 2;
        paint.setColor(theme.colorSelectBG());
        
        if(isSelectedDay(year, month + 1, day)){ //绘制背景色圆形
        	System.out.println(" drawBG " + year + "-"+month+"-"+day);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx,cy,radius * 1.4f,paint);
        } else if(isSelectedDay2(year, month + 1, day)){ //绘制背景色圆形
        	paint.setColor(Color.parseColor("#BBBBBB"));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx,cy,radius * 1.4f,paint);
        } 
        
//        if(day== selDay && year == selYear && month == selMonth){
//        	paint.setStyle(Paint.Style.STROKE);
//          canvas.drawCircle(cx,cy,radius,paint);
//        }
//        // 今日不绘制
//        if(day== currDay && currDay != selDay && currMonth == selMonth){//今日绘制圆环
//            paint.setStyle(Paint.Style.STROKE);
//            canvas.drawCircle(cx,cy,radius,paint);
//        }
    }

	@Override
    protected void drawDecor(Canvas canvas, int column, int row, int year,int month,int day) {
		// nothing
    }

    @Override
    protected void drawRest(Canvas canvas, int column, int row, int year,int month,int day) {
    	// nothing
    }

    @Override
    protected void drawText(Canvas canvas, int column, int row, int year,int month,int day) {
        paint.setTextSize(theme.sizeDay());
        float startX = columnSize * column + (columnSize - paint.measureText(day+""))/2;
        float startY = rowSize * row + rowSize/2 - (paint.ascent() + paint.descent())/2;
        paint.setStyle(Paint.Style.STROKE);
//        if(day== selDay && year == selYear && month == selMonth){
//        	//日期为选中的日期
//        	paint.setColor(theme.colorSelectDay());
//        	canvas.drawText(day+"", startX, startY, paint);
//        }else 
//        	if(day== currDay && currDay != selDay && currMonth == selMonth){//今日的颜色，不是选中的时候
//            //正常月，选中其他日期，则今日为红色
//            paint.setColor(theme.colorToday());
//            canvas.drawText(day+"", startX, startY, paint);
//        }else{
        if(column == 0 || column == 6) {
        	paint.setColor(theme.colorWeekend());
        } else {
        	paint.setColor(theme.colorWeekday());
        }
        
        if(isSelectedDay(year, month + 1, day) || isSelectedDay2(year, month + 1, day)){ 
        	paint.setColor(theme.colorSelectDay());
        }
        canvas.drawText(day+"", startX, startY, paint);
//        }
        	
    }

    @Override
    protected void createTheme() {
        theme = new SinginDayTheme(getContext());
    }
}
