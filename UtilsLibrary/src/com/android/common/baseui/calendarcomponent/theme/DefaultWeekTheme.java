package com.android.common.baseui.calendarcomponent.theme;

import android.content.Context;
import android.graphics.Color;

import com.android.common.utils.AppUtil;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DefaultWeekTheme implements IWeekTheme {
	int dip42;
	
	public DefaultWeekTheme(Context context) {
		dip42 = AppUtil.dip2px(context, 42);
	}
	
    @Override
    public int colorTopLinen() {
        return Color.parseColor("#CCE4F2");
    }

    @Override
    public int colorBottomLine() {
        return Color.parseColor("#CCE4F2");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#1FC2F3");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#fa4451");
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
        return 22;
    }
}
