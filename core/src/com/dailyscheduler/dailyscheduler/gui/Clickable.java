package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.math.Vector2;

public interface Clickable {
	public boolean check_on_click(Vector2 click_position);
	public void activate();
	public void deactivate();
}
