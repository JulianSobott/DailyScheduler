package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Dragger extends Widget{
	private final float WIDTH = 50;
	private final float HEIGHT = 20;
	
	public boolean is_active  = false;
	
	public Dragger(Widget parent, float x, float y) {
		this.parent = parent;
		this.position = Position.relative_relative;
		this.x = x;
		this.y = y;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.centered_x = true;
		this.centered_y = true;
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		if(!is_hidden) {
			sr.begin(ShapeType.Filled);
			if(is_active)
				sr.setColor(new Color(0.5f, 0.7f, 0.5f, 1f));
			else
				sr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
			if(position == Position.relative_relative) {
				sr.rect(get_absolute_x(), get_absolute_y(), WIDTH, HEIGHT);
			}
			sr.end();
			super.render(sr, sb);
		}
	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked =  false;
		if(clicked = super.check_on_click(click_position)) {
			is_active = true;
		}
		return clicked;	
	}
	
	public void update_position_y(float y) {
		((TaskField)this.parent).update_position(y);
	}
}
