package com.android.common.baseui.calendarcomponent.theme;

import com.android.common.utils.AppUtil;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Administrator on 2016/8/9.
 */
public class ADCircleDayTheme implements IDayTheme {
	int dip42;
	
	public ADCircleDayTheme(Context context) {
		dip42 = AppUtil.dip2px(context, 42);
	}
	
    @Override
    public int colorSelectBG() {
        return Color.parseColor("#38C0C3");
    }

    @Override
    public int colorSelectDay() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorToday() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorMonthView() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#4F4F4F");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#BEBEBE");
    }

    @Override
    public int colorDecor() {
        return Color.parseColor("#4AB9AE");
    }

    @Override
    public int colorRest() {
        return Color.parseColor("#2AC5C8");
    }

    @Override
    public int colorWork() {
        return Color.parseColor("#C78D7D");
    }

    @Override
    public int colorDesc() {
        return Color.parseColor("#4F4F4F");
    }

    @Override
    public int sizeDay() {
        return 48;
    }

    @Override
    public int sizeDesc() {
        return 26;
    }

    @Override
    public int sizeDecor() {
        return 4;
    }

    @Override
    public int dateHeight() {
        return dip42;
    }

    @Override
    public int colorLine() {
        return Color.parseColor("#CBCBCB");
    }

    @Override
    public int smoothMode() {
        return 0;
    }

}
