package com.android.common.baseui.calendarcomponent.theme;

import com.android.common.utils.DensityUtil;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Administrator on 2016/7/31.
 */
public class SinginWeekTheme implements IWeekTheme {
	int dip42;
	
	public SinginWeekTheme(Context context) {
		dip42 = DensityUtil.dip2px(context, 42);
	}
	
    @Override
    public int colorTopLinen() {
        return Color.parseColor("#cccccc");
    }

    @Override
    public int colorBottomLine() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#333333");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#333333");
    }

    @Override
    public int colorWeekView() {
        return Color.parseColor("#FEFEFF");
    }

    @Override
    public int sizeLine() {
        return 4;
    }

    @Override
    public int sizeText() {
        return 20;
    }
}
