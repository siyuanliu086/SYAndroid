package com.android.common.utils;

import android.graphics.Color;

public class ColorUtil {
	public static int setColor(int color) {
		int red = (color & 0xff0000) >> 16;
		int green = (color & 0x00ff00) >> 8;
		int blue = (color & 0x0000ff);
		return Color.rgb(red, green, blue);
	}
}
