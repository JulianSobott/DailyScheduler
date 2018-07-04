package com.dailyschedulerapp.dailyscheduler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dailyschedulerapp.dailyscheduler.gui.Widget;

public class TaskField extends Widget{
	private final float OUTLINE_THICKNESS = 7;
	private Dragger draggerTop, draggerBot;
	public boolean is_active = false;
	
	public TaskField() {
		draggerTop = new Dragger(this, 0.5f, 0);
		this.subWidgets.add(draggerTop);
		draggerBot = new Dragger(this, 0.5f, 1);
		this.subWidgets.add(draggerBot);
		this.x = 50;
		this.y = 0;
		this.height = 0;
		this.width = Gdx.graphics.getWidth() - 100;
		is_active = true;
	}
	
	public void render(ShapeRenderer sr, SpriteBatch sb) {	
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.9f, 0.6f, 0.3f, 1));
		sr.rect(x- OUTLINE_THICKNESS, y- OUTLINE_THICKNESS, width + 2 * OUTLINE_THICKNESS, height + 2*OUTLINE_THICKNESS);
		sr.setColor(new Color(0.7f, 1, 0.3f, 1));
		sr.rect(x, y, width, height);
		
		sr.end();
		if(is_active) {
			draggerBot.show();
			draggerTop.show();
		}else {
			draggerBot.hide();
			draggerTop.hide();
		}
			
		super.render(sr, sb);
	}
	
	void update_position_down(float touchY) {
		this.height = touchY - this.y;
	}
	
	void set_start(float y) {
		this.y = y;
	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked =  false;
		if(clicked = super.check_on_click(click_position)) {
			is_active = true;
		}
		return clicked;
	}
}
