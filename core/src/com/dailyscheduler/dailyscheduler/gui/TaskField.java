package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dailyscheduler.dailyscheduler.utils.Time;

public class TaskField extends Widget{
	private final float OUTLINE_THICKNESS = 2;
	private Dragger draggerTop, draggerBot;
	private TimeBox time_box_start, time_box_duration, time_box_end; 
	private Textfield textField;
	public boolean is_active = false;
	
	private Timeline timeLine;
	
	public TaskField(Timeline timeLine) {
		//DRAGGER
		draggerTop = new Dragger(this, 0.5f, 0);
		this.subWidgets.add(draggerTop);
		draggerBot = new Dragger(this, 0.5f, 1);
		draggerBot.is_active = true;
		this.subWidgets.add(draggerBot);
		
		//TIME BOXES
		time_box_start = new TimeBox(this, 0);
		time_box_duration = new TimeBox(this, 0.5f);
		time_box_end = new TimeBox(this, 1);
		this.subWidgets.add(time_box_start);
		this.subWidgets.add(time_box_duration);
		this.subWidgets.add(time_box_end);
		//this
		this.timeLine = timeLine;
		this.x = 50;
		this.y = 0;
		this.height = 0;
		this.width = Gdx.graphics.getWidth() - 100;
		is_active = true;
		
		//Textfield
		textField = new Textfield(this);
		this.subWidgets.add(textField);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		Time diff;
		time_box_end.time = timeLine.map_y_to_time(this.get_absolute_y() + this.height);
		diff = new Time(time_box_end.time);
		time_box_start.time = timeLine.map_y_to_time(this.get_absolute_y());
		diff.subtract(time_box_start.time);
		time_box_duration.time = diff;
		
		
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.9f, 0.6f, 0.3f, 1));
		sr.rect(x- OUTLINE_THICKNESS, y- OUTLINE_THICKNESS, width + 2 * OUTLINE_THICKNESS, height + 2*OUTLINE_THICKNESS);
		sr.setColor(new Color(0.7f, 1, 0.3f, 1));
		sr.rect(x, y, width, height);
		
		sr.end();
		if(is_active) {
			draggerBot.show();
			draggerTop.show();
			time_box_start.show();
			time_box_duration.show();
			time_box_end.show();
		}else {
			draggerBot.hide();
			draggerTop.hide();
			time_box_start.hide();
			time_box_duration.hide();
			time_box_end.hide();
		}
			
		super.render(sr, sb);
	}
	
	public void update_position(float touchY) {
		if(draggerTop.is_active) {
			this.height += this.y - touchY;
			this.y = touchY;
		}
		else if(draggerBot.is_active) {
			this.height = touchY - this.y;
		}else {
			System.err.println("No Dragger is active to updtae in TaskField.update_position()");
		}
		
	}
	
	public void set_start(float y) {
		this.y = y;
	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked_sub = false;
		for(Widget w : subWidgets) {
			if(w.check_on_click(click_position)) {
				clicked_sub = true;
				if(w instanceof Dragger) {
					draggerBot.is_active = false;
					draggerTop.is_active = false;
					((Dragger) w).is_active = true;
				}
			}
			
			
		}
		boolean clicked =  false;
		if(clicked = super.check_on_click(click_position) || clicked_sub) {
			is_active = true;
		}
		return clicked || clicked_sub;
	}
	
	
}
