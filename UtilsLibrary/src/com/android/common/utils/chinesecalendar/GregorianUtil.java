package com.android.common.utils.chinesecalendar;

import java.util.Calendar;

/**
 * @TiTle GregorianUtil.java
 * @Package com.android.common.utils.chinesecalendar
 * @Description 农历节气处理--对公历日期的处理类，节气预设
 * @Date 2016年10月24日
 * @Author siyuan
 * @Refactor 
 * @Company ISoftStone ZHHB
 */
public class GregorianUtil {
	 private final static String[][] GRE_FESTVIAL = {
         // 一月
         { "元旦", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 二月
         { "", "", "", "", "", "", "", "", "", "", "", "", "", "情人节", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 三月
         { "", "", "", "", "", "", "", "妇女节", "", "", "", "植树节", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "" },
         // 四月
         { "愚人节", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 五月
         { "劳动节", "", "", "青年节", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "" },
         // 六月
         { "儿童节", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 七月
         { "建党节", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 八月
         { "建军节", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 九月
         { "开学日", "", "", "", "", "", "", "", "", "教师节", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 十月
         { "国庆节", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 十一月
         { "", "", "", "", "", "", "", "", "", "", "光棍节", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
         // 十二月
         { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                 "", "", "", "", "", "", "", "", "", "圣诞节", "", "", "", "",
                 "", "" }, };
	 private int mMonth;
	 private int mDay;
	
	 public GregorianUtil(Calendar calendar) {
	     mMonth = calendar.get(Calendar.MONTH);
	     mDay = calendar.get(Calendar.DATE);
	 }
	
	 public String getGremessage() {
	     return GRE_FESTVIAL[mMonth][mDay - 1];
	 }
}
