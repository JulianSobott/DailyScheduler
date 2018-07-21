package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import debug.Profiler;
import gui.utils.Bounds;
import utils.Time;

public class TaskField extends Widget implements Clickable{
	private final float OUTLINE_THICKNESS = 2;
	private Dragger draggerTop, draggerBot;
	private TimeBox time_box_start, time_box_duration, time_box_end; 
	private Textarea textarea;
	public boolean is_active = false;
	
	private Timeline timeLine;
	
	public TaskField(Timeline timeLine) {
		//this
		this.timeLine = timeLine;
		float limeline_width_leftColumn = timeLine.get_width_of_left_column();
		this.bounds = new Bounds(limeline_width_leftColumn, 0, Gdx.graphics.getWidth() - limeline_width_leftColumn * 2, 0, 0);
		is_active = true;
		
		//DRAGGER
		draggerTop = new Dragger(this, 0.5f, 0);
		this.subWidgets.add(draggerTop);
		draggerBot = new Dragger(this, 0.5f, 1);
		draggerBot.is_active = true;
		this.subWidgets.add(draggerBot);
		
		//TIME BOXES
		float timeBoxWidth = timeLine.get_width_of_left_column() - timeLine.MARGIN;
		time_box_start = new TimeBox(this, 0,  timeBoxWidth);
		time_box_duration = new TimeBox(this, 0.5f, timeBoxWidth);
		time_box_end = new TimeBox(this, 1, timeBoxWidth);
		this.subWidgets.add(time_box_start);
		this.subWidgets.add(time_box_duration);
		this.subWidgets.add(time_box_end);
		
		//Textfield
		textarea = new Textarea(this, 0, 0, 1f, 1.f,
				Bounds.relative_x | Bounds.relative_y | Bounds.relative_width | Bounds.relative_height);
		textarea.setText("WOW");
		this.subWidgets.add(textarea);
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		Time diff;
		time_box_end.time = timeLine.map_y_to_time(this.get_absolute_y() + this.bounds.height);
		diff = new Time(time_box_end.time);
		time_box_start.time = timeLine.map_y_to_time(this.get_absolute_y());
		diff.subtract(time_box_start.time);
		time_box_duration.time = diff;
		
		
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.9f, 0.6f, 0.3f, 1));
		sr.rect(bounds.x- OUTLINE_THICKNESS, bounds.y- OUTLINE_THICKNESS, bounds.width + 2 * OUTLINE_THICKNESS, bounds.height + 2*OUTLINE_THICKNESS);
		sr.setColor(new Color(0.7f, 1, 0.3f, 1));
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		sr.end();
		
			
		super.render(sr, sb);
	}
	
	public void update_position(float touchY) {
		if(draggerTop.is_active) {
			adjustBounds(this.bounds.x, touchY, this.bounds.width, this.bounds.height + this.bounds.y - touchY);
		}
		else if(draggerBot.is_active) {
			adjustHeight(touchY - this.bounds.y);
		}
		
	}
	
	public void set_start(float y) {
		this.bounds.y = y;
	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked_sub = false;
		draggerBot.is_active = false;
		draggerTop.is_active = false;
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
			activate();
		}
		return clicked || clicked_sub;
	}

	public void handle_key_input(int key_code) {
		this.textarea.handle_key_input(key_code);
	}
	public void handle_char_input(char c) {
		this.textarea.handle_char_input(c);
	}

	@Override
	public void activate() {
		this.is_active = true;
		show();
	}

	@Override
	public void deactivate() {
		this.is_active = false;
		for(Widget w : subWidgets) {
			if(w instanceof Clickable) {
				((Clickable) w).deactivate();
			}
		}
		hideSubWidgets();
	}

	@Override
	public void show() {
		draggerBot.show();
		draggerTop.show();
		time_box_start.show();
		time_box_duration.show();
		time_box_end.show();
	}
	@Override
	public void hide() {
		hideSubWidgets();
	}
	
	public void hideSubWidgets() {
		draggerBot.hide();
		draggerTop.hide();
		time_box_start.hide();
		time_box_duration.hide();
		time_box_end.hide();
	}

	@Override
	protected void update() {
		
	}
	
}
