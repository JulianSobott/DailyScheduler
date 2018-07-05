package com.dailyscheduler.dailyscheduler.utils;

public class Bounds{
	public static final int relative_x 		= 0b0001;
	public static final int relative_y 		= 0b0010;
	public static final int relative_width 	= 0b0100;
	public static final int relative_height = 0b1000;
	
	private int flags;
	
	public float x;
	public float y;
	public float width;
	public float height;
	
	public Bounds(int flags) {
		this.flags = flags; 
	}
	
	public boolean isRelative(int searched_position) {
		if((this.flags & Bounds.relative_x) == Bounds.relative_x) {
			return true;
		}
		return false;
	}
}
