package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dailyscheduler.dailyscheduler.utils.Time;

public class TimeBox extends Widget {
	private final float HEIGHT = 30.f;
	private final float WIDTH = 45.f;
	
	BitmapFont font = new BitmapFont(true);
	public Time time = new Time(0);
	
	public TimeBox(Widget parent, float y) {
		super(parent);
		this.x = Gdx.graphics.getWidth() - 50;
		this.y = y;
		this.height = HEIGHT;
		this.width= WIDTH;
		this.position = Position.absolute_relative;
		this.centered_y = true;
	}

	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		if(!is_hidden) {
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(0.7f, 0.7f, 0.7f, 1));
			sr.rect(get_absolute_x(), get_absolute_y(), this.width, this.height);
			sr.end();
			
			sb.begin();
			font.draw(sb, time.toString(), get_absolute_x(), get_absolute_y() + this.height / 2 - font.getLineHeight()/2);
			sb.end();
			super.render(sr, sb);
		}
	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		// TODO Auto-generated method stub
		return super.check_on_click(click_position);
	}
}
