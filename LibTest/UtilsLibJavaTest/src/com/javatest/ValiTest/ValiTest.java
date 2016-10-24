package com.javatest.ValiTest;

import java.util.Calendar;

public class ValiTest {
	public static void main(String[] args) {
//		System.out.println(getFirstDayWeek(2016, 10));
		System.out.println(getMonthDays(2016, 2));
	}
	
    public static int getMonthDays(int year, int month) {
		if (month > 12) {
			month = 1;
			year += 1;
		} else if (month < 1) {
			month = 12;
			year -= 1;
		}
		int[] arr = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int days = 0;

		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			arr[1] = 29; // ÈòÄê2ÔÂ29Ìì
		}

		try {
			days = arr[month - 1];
		} catch (Exception e) {
			e.getStackTrace();
		}
		return days;
	}

//    public static int getFirstDayWeek(int mSelYear, int mSelMonth) {
//    	Calendar calendar = Calendar.getInstance();      
//    	calendar.set(mSelYear, mSelMonth - 1, 1);      
//    	
//    	int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
//    	if (w < 0){        
//    		w = 0;      
//    	}      
//    	return w;
//	}
	
}
