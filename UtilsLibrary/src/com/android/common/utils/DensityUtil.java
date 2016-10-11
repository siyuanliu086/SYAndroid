package com.android.common.utils;

import android.content.Context;

/**
 * 
 * @author Keven.Cheng
 *	
 * @日期： 2014-1-13 上午11:43:30
 * 
 * @详解：dp px 转换
 */
public class DensityUtil {
	
	/** 
     * 根据手机的分辨率dp 转成(像素)
     */  
    public static int dip2px(float density, float dpValue) {  
        return (int) (dpValue * density + 0.5f);  
    }  
	
    /** 
     * 根据手机的分辨率dp 转成(像素)
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     *根据手机的分辨率px(像素) 的单转成dp 
     */  
    public static int px2dip(float density, float pxValue) {  
        return (int) (pxValue / density + 0.5f);  
    } 
    
    /** 
     *根据手机的分辨率px(像素) 的单转成dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}

