package com.dailyscheduler.dailyscheduler.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Widget {
	
	public enum Position {
		absolute_absolute, relative_relative, absolute_relative, relative_absolute
	}
	
	protected Position position = Position.absolute_absolute;
	protected float x, y, width, height;
	protected boolean centered_x = false; //Origin of widget is in center or at top_left corner
	protected boolean centered_y = false; //Origin of widget is in center or at top_left corner
	protected boolean is_hidden = false;
	protected Widget parent;
	
	protected List<Widget> subWidgets = new ArrayList<Widget>();
	
	public Widget(Widget parent) {
		this.parent = parent;
	}
	public Widget() {};
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		if(!is_hidden) {
			for(Widget widget : subWidgets) {
				widget.render(sr, sb);
			}
		}
	}
	
	public float get_absolute_x() {
		if(position == Position.absolute_relative || position == Position.absolute_absolute) {
			return this.x;
		}else {
			return this.parent.x + this.x * this.parent.width - (centered_x ? 0.5f * this.width : 0);
		}
	}
	
	public float get_absolute_y() {
		if(position == Position.absolute_absolute || position == Position.relative_absolute) {
			return this.y;
		}else {
			return this.parent.y + this.y * this.parent.height - (centered_y ? 0.5f * this.height : 0);
		}
	}
	
	public void hide() {
		this.is_hidden = true;
	}
	
	public void show() {
		this.is_hidden = false;
	}
	
	public boolean check_on_click(Vector2 click_position) {
		if(get_absolute_x() <= click_position.x && get_absolute_x() + this.width >= click_position.x) {
			if(get_absolute_y() <= click_position.y && get_absolute_y() + this.height >= click_position.y)
				return true;
			else
				return false;
		}
		else {
			return false;
		}
	}
}
