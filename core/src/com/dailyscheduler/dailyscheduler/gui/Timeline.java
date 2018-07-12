package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dailyscheduler.dailyscheduler.debug.Profiler;
import com.dailyscheduler.dailyscheduler.utils.Bounds;
import com.dailyscheduler.dailyscheduler.utils.FontManager;
import com.dailyscheduler.dailyscheduler.utils.Time;

public class Timeline extends Widget{
	final Time dayStart = new Time(7);
	final Time dayEnd = new Time(22);
	
	private float y_start;
	private float width_of_left_column;
	private float pos_x_2_lines;
	public final float MARGIN = 10;
	private float gap_hours;
	
	BitmapFont font = FontManager.getFont(24);
	GlyphLayout layout = new GlyphLayout();
	
	
	
	public Timeline() {
		this.bounds = new Bounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
		y_start = Gdx.graphics.getHeight() / (dayEnd.hour - dayStart.hour + 2);
		this.width_of_left_column = get_width_of_left_column();
		this.pos_x_2_lines = Gdx.graphics.getWidth() - width_of_left_column;
		gap_hours = Gdx.graphics.getHeight() / (dayEnd.hour - dayStart.hour + 2);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sb.begin();
		int idx = 0;
		
		for(int hour = dayStart.hour; hour <= dayEnd.hour; hour++) {
			font.draw(sb, hour + ":00", MARGIN, idx * gap_hours + y_start - font.getLineHeight()/2);
			idx++;
		}
		sb.end();
		sr.begin(ShapeType.Filled);
		idx = 0;
		sr.setColor(new Color(0.9f, 0.9f, 0.9f, 1));
		for(int hour = dayStart.hour; hour <= dayEnd.hour; hour++) {
			float y = idx * gap_hours + y_start;
			sr.line(this.width_of_left_column, y, this.pos_x_2_lines, y);
			idx++;
		}
		sr.setColor(new Color(1.0f, 0.3f, 0.3f, 1.0f));
		float y = map_time_to_y(Time.getCurrentTime());
		sr.line(this.width_of_left_column, y, this.pos_x_2_lines, y);
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

	public float map_time_to_y(Time t) {
		float time_line_num_hours = dayEnd.hour - dayStart.hour;
		float time_line_height =  time_line_num_hours * gap_hours;
		float map_multiplicator =  time_line_height / time_line_num_hours;
		float abs_y = (t.hour - dayStart.hour) * map_multiplicator + y_start;
		abs_y += ((float)t.minute / 60) * map_multiplicator;
		return abs_y;
	}
	
	public float get_width_of_left_column(){
		return  Label.getWidthOfLine("00:00", 24) + MARGIN * 2;
	}
}
