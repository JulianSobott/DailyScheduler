package com.dailyscheduler.dailyscheduler.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dailyscheduler.dailyscheduler.utils.Bounds;

public abstract class Widget {
	
	protected Bounds bounds = new Bounds();
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
	
	public void adjustHeight(float new_height) {
		adjustBounds(this.bounds.x, this.bounds.y, this.bounds.width, new_height);
	}
	
	public void adjustBounds(float new_x, float new_y, float new_width, float new_height) {
		this.bounds.x = new_x;
		this.bounds.y = new_y;
		this.bounds.width = new_width;
		this.bounds.height = new_height;
	}
	
	public float get_absolute_x() {
		if(!bounds.isRelative(Bounds.relative_x)) {
			if(this.parent == null)
				return this.bounds.x;
			else
				return this.parent.get_absolute_x() + this.bounds.x;
		}else {
			return this.parent.bounds.x + this.bounds.x * this.parent.bounds.width - (centered_x ? 0.5f * this.bounds.width : 0);
		}
	}

	public float get_absolute_y() {
		if(!bounds.isRelative(Bounds.relative_y)) {
			if(this.parent == null)
				return this.bounds.y;
			else
				return this.parent.get_absolute_y() + this.bounds.y;
		}else {
			return this.parent.bounds.y + this.bounds.y * this.parent.bounds.height - (centered_y ? 0.5f * this.bounds.height : 0);
		}
	}
	
	public float get_absolute_width() {
		if(!bounds.isRelative(Bounds.relative_width)) {
			return this.bounds.width;
		}else {
			return this.parent.bounds.width * this.bounds.width;
		}
	}
	
	public float get_absolute_height() {
		if(!bounds.isRelative(Bounds.relative_height)) {
			return this.bounds.height;
		}else {
			return this.parent.bounds.height * this.bounds.height;
		}
	}
	
	public void hide() {
		this.is_hidden = true;
	}
	
	public void show() {
		this.is_hidden = false;
	}
	
	public boolean check_on_click(Vector2 click_position) {
		if(get_absolute_x() <= click_position.x && get_absolute_x() + get_absolute_width() >= click_position.x) {
			if(get_absolute_y() <= click_position.y && get_absolute_y() + get_absolute_height() >= click_position.y)
				return true;
			else
				return false;
		}
		else {
			return false;
		}
	}
	
	protected Vector2 global_position_to_inner_position(float x , float y) {
		if(this.parent != null) {
			x = x - this.parent.get_absolute_x();
			y = y - this.parent.get_absolute_y();
		}
		return new Vector2(x, y);
	}
}
