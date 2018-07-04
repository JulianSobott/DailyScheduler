package com.dailyschedulerapp.dailyscheduler.utils;

public class Time {
	public int hour;
	public int minute;
	
	public Time(int hour, int minute){
		this.hour = hour;
		this.minute = minute;
	}
	
	public Time(int hour) {
		this(hour, 0);
	}
}
