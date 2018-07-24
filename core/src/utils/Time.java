package utils;

import java.time.LocalDateTime;

public class Time {
	public int hour = 0;
	public int minute = 0;
	
	public Time(int hour, int minute){
		this.hour = hour;
		this.minute = minute;
	}
	
	public Time(int hour) {
		this(hour, 0);
	}
	
	public Time(Time t) {
		this.hour = t.hour;
		this.minute = t.minute;
	}
	
	public String toString() {
		return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : int_to_2decimals(minute));
	}
	
	private int int_to_2decimals(int i) {
		int new_int = i;
		while(new_int >= 100) {
			new_int /= 10;
		}
		return new_int;
	}

	public void subtract(Time time) {
		int min_1 = 60 - time.minute;
		int min_2 = this.minute;
		int min_end = min_1 + min_2;
		int hour = 0;
		if(min_end >= 60) {
			hour += min_end / 60;
			min_end -= hour * 60;
		}
		hour += this.hour - time.hour - 1;
		this.minute = min_end;
		this.hour = hour;
	}
	
	public static Time getCurrentTime() {
		int hours = LocalDateTime.now().getHour();
		int minutes = LocalDateTime.now().getMinute();;
		return new Time(hours, minutes);
	}
	
	public static int toInt(Time t) {
		return t.hour * 60 + t.minute;
	}
	
	public static Time intToTime(int i) {
		int hour = i / 60;
		int minute = i % 60;
		return new Time(hour, minute);
	}
}
