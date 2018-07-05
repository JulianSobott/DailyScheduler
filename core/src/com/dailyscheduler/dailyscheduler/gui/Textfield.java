package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class TextField extends Label {
	public boolean is_active;
	private Cursor cursor;
	
	public TextField(Widget parent, float x, float y, float width, float height, int bounds_flags) {
		super(parent, x, y, width, height,bounds_flags);
		init();
	}

	public TextField() {
		super();
		init();
	}
	
	private void init() {
		this.is_active = true;
		this.cursor = new Cursor(this);
		this.subWidgets.add(cursor);
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		super.render(sr, sb);

	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked;
		if(clicked = super.check_on_click(click_position)) {
			is_active = true;
			cursor.set_indices_by_position(click_position.x, click_position.y);
		}
		return clicked;
	}

}
