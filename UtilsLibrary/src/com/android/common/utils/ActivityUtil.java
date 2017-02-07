package com.android.common.utils;

import android.app.Activity;
import android.graphics.Rect;

/**
 * @TiTle ActivityUtils.java
 * @Package com.android.common.utils
 * @Description Activity 页面内工具<br>
 * 视图工具三层级 ：应用级{@link AppUtil}、活动级{@link ActivityUtil}、视图级{@link ViewUtil}
 * @Date 2016年10月14日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-14
 * @Company ISoftStone ZHHB
 */
public class ActivityUtil {
	
	/**
     * 获取状态栏高度
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 字符串是否为{@code null}或者空字符串。
     *
     * @param s
     * @return
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
