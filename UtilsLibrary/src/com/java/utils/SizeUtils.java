package com.java.utils;

/**
 * @TiTle SizeUtils.java
 * @Package com.android.common.utils
 * @Description 大小转换
 * @Date 2016年10月21日
 * @Author siyuan
 * @Refactor siyuan FIX 2016-10-21 10:51:27
 * @Company ISoftStone ZHHB
 */
public class SizeUtils {

    /** gb to byte **/
    public static final long GB_2_BYTE = 1073741824;
    /** mb to byte **/
    public static final long MB_2_BYTE = 1048576;
    /** kb to byte **/
    public static final long KB_2_BYTE = 1024;

    /**
     * 文件大小转换
     * @param size
     * @return
     */
    public static String formatSize(long size) {
         if (size >= GB_2_BYTE) {
             return String.format("%.1f GB", (float) size / GB_2_BYTE);
         } else if (size >= MB_2_BYTE) {
             float f = (float) size / MB_2_BYTE;
             return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
         } else if (size >= KB_2_BYTE) {
             float f = (float) size / KB_2_BYTE;
             return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
         } else {        	 
        	 return String.format("%d B", size);
         }
    }
    
    public static String formatSize(String size) {
    	if(size != null && !size.isEmpty()) {
    		long sizel = Long.valueOf(size);
    		return formatSize(sizel);
    	}
    	return "";
    }
}
