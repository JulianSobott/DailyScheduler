package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dailyscheduler.dailyscheduler.utils.FontManager;
import com.dailyscheduler.dailyscheduler.utils.Time;

public class Timeline extends Widget{
	final Time dayStart = new Time(7);
	final Time dayEnd = new Time(22);
	
	float y_start;
	
	BitmapFont font = FontManager.getFont(24);
	
	public Timeline() {
		this.x = 0;
		this.y = 0;
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		y_start = Gdx.graphics.getHeight() / (dayEnd.hour - dayStart.hour + 2);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sb.begin();
		int idx = 0;
		float gap = Gdx.graphics.getHeight() / (dayEnd.hour - dayStart.hour + 2);
		for(int hour = dayStart.hour; hour <= dayEnd.hour; hour++) {
			font.draw(sb, hour + ":00", 10, idx * gap + y_start - font.getLineHeight()/2);
			idx++;
		}
		sb.end();

		sr.begin(ShapeType.Filled);
		idx = 0;
		sr.setColor(new Color(0.9f, 0.9f, 0.9f, 1));
		for(int hour = dayStart.hour; hour <= dayEnd.hour; hour++) {
			float y = idx * gap + y_start;
			sr.line(50, y, Gdx.graphics.getWidth(), y);
			idx++;
		}
		sr.end();
	}
	
	public Time map_y_to_time(float abs_y) {
		Time t = new Time(0);
		float map_multiplicator = (dayEnd.hour - dayStart.hour + 2);
		map_multiplicator /= Gdx.graphics.getHeight();
		t.hour = dayStart.hour + (int)(abs_y * map_multiplicator) - 1;
		float x = (abs_y * map_multiplicator - (int)(abs_y * map_multiplicator));
		t.minute = (int) (60 * x);
		return t;
	}
}
