package com.dailyschedulerapp.dailyscheduler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dailyschedulerapp.dailyscheduler.gui.Widget;

public class Dragger extends Widget{
	private final float WIDTH = 50;
	private final float HEIGHT = 20;
	
	public Dragger(Widget parent, float x, float y) {
		this.parent = parent;
		this.position = Position.relative;
		this.x = x;
		this.y = y;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.centered = true;
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		if(!is_hidden) {
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(0, 1, 0.3f, 1));
			sr.setColor(new Color(1, 0, 0.3f, 1));
			if(position == Position.relative) {
				sr.rect(get_absolute_x(), get_absolute_y(), WIDTH, HEIGHT);
			}
			sr.end();
			super.render(sr, sb);
		}
	}
}
