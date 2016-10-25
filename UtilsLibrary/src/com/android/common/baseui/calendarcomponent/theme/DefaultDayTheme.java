package com.android.common.baseui.calendarcomponent.theme;

import com.android.common.utils.DensityUtil;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Administrator on 2016/7/30.
 */
public class DefaultDayTheme implements IDayTheme {
	int dip42;
	
	public DefaultDayTheme(Context context) {
		dip42 = DensityUtil.dip2px(context, 42);
	}
	
    @Override
    public int colorSelectBG() {
        return Color.parseColor("#13A4D3");
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
        return Color.parseColor("#404040");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#404040");
    }

    @Override
    public int colorDecor() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorRest() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorWork() {
        return Color.parseColor("#FF9B12");
    }

    @Override
    public int colorDesc() {
        return Color.parseColor("#FF9B12");
    }

    @Override
    public int sizeDay() {
        return 48;
    }

    @Override
    public int sizeDecor() {
        return 6;
    }

    @Override
    public int sizeDesc() {
        return 26;
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
