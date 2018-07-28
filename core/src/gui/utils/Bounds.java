package gui.utils;

import com.badlogic.gdx.math.Vector2;

import gui.Widget;

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
	
	public Bounds(Bounds b) {
		this(b.x, b.y, b.width, b.height, b.flags);
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
	
	public static boolean is_position_inside(Vector2 abs_pos, Widget corresponding_widget) {
		if(		corresponding_widget.get_absolute_x() <= abs_pos.x &&
				corresponding_widget.get_absolute_x() + corresponding_widget.get_absolute_width() >= abs_pos.x &&
				corresponding_widget.get_absolute_y() <= abs_pos.y &&
				corresponding_widget.get_absolute_y() + corresponding_widget.get_absolute_height() >= abs_pos.y)
					return true;
		else
			return false;
	}
	
	public static boolean is_position_inside(Vector2 abs_pos, Bounds abs_bounds) {
		if(		abs_bounds.x <= abs_pos.x &&
				abs_bounds.x + abs_bounds.width >= abs_pos.x &&
				abs_bounds.y <= abs_pos.y &&
				abs_bounds.y + abs_bounds.height >= abs_pos.y)
					return true;
		else
			return false;
	}
	
	public static boolean overlap(Bounds abs_b1, Bounds abs_b2) {
		Vector2 p1_top_left = new Vector2(abs_b1.x, abs_b1.y);
		Vector2 p1_bot_right = new Vector2(abs_b1.x + abs_b1.width, abs_b1.y + abs_b1.height);
		Vector2 p2_top_left = new Vector2(abs_b2.x, abs_b2.y);
		Vector2 p2_bot_right = new Vector2(abs_b2.x + abs_b2.width, abs_b2.y + abs_b2.height);
		
		if(p1_top_left.x > p2_bot_right.x || p2_top_left.x > p1_bot_right.x)
			return false;
		if(p1_top_left.y > p2_bot_right.y || p2_top_left.y > p1_bot_right.y)
			return false;
		
		return true;
	}

	public static Vector2 absScrennToAbsParent(Vector2 absScreen, Widget parent) {
		return new Vector2(absScreen.x - parent.get_absolute_x(), absScreen.y - parent.get_absolute_y());
	}
	
}
