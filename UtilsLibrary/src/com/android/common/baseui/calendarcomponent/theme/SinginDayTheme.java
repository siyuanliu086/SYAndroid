package com.android.common.baseui.calendarcomponent.theme;

import com.android.common.utils.DensityUtil;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SinginDayTheme implements IDayTheme {
	int dip36;
	
	public SinginDayTheme(Context context) {
		dip36 = DensityUtil.dip2px(context, 36);
	}
	
    @Override
    public int colorSelectBG() {
        return Color.parseColor("#7CBD00");
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
        return Color.parseColor("#888888");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#cccccc");
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
        return dip36;
    }

    @Override
    public int colorLine() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int smoothMode() {
        return 1;
    }
}
