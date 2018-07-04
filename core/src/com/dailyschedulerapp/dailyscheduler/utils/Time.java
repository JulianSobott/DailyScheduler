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
	
	public String toString() {
		return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : int_to_2decimals(minute));
	}
	
	private int int_to_2decimals(int i) {
		int new_int = i;
		do {
			new_int /= 10;
		}while(new_int >= 100);
		return new_int;
	}
}
