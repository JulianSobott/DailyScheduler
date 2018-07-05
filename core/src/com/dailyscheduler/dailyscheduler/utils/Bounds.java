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
	
	/**
	 * all positions are absolute
	 */
	public Bounds() {
		this(0, 0, 0, 0, 0);
	}
	
	/**
	 * @param flags indicates which positions are set to relative
	 */
	public Bounds(int flags) {
		this(0, 0, 0, 0, flags);
	}
	
	public Bounds(float x, float y, float width, float height, int flags) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.flags = flags;
	}
	
	public boolean isRelative(int searched_position) {
		if((this.flags & searched_position) == searched_position) {
			return true;
		}
		return false;
	}
	
	public static boolean isRelative(int given, int searched) {
		if((given & searched) == searched) {
			return true;
		}
		return false;
	}
	
	public boolean is_position_inside(Bounds position) {
		//TODO implement this
		return false;
	}
}
