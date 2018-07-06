package com.dailyscheduler.dailyscheduler.utils;

import com.badlogic.gdx.math.Vector2;
import com.dailyscheduler.dailyscheduler.gui.Widget;

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
	
	public boolean is_position_inside_absolute_widget(Vector2 pos) {
		if(this.flags != 0) {
			if(this.x <= pos.x && this.x + this.width >= pos.x && this.y <= pos.y && this.y + this.height >= pos.y)
				return true;
		}
		return false;
	}
	
	public boolean is_position_inside(Vector2 pos, Widget corresponding_widget) {
		if(		corresponding_widget.get_absolute_x() <= pos.x &&
				corresponding_widget.get_absolute_x() + corresponding_widget.get_absolute_width() >= pos.x &&
				corresponding_widget.get_absolute_y() <= pos.y &&
				corresponding_widget.get_absolute_y() + corresponding_widget.get_absolute_height() >= pos.y)
					return true;
		else
			return false;
	}
	
}
