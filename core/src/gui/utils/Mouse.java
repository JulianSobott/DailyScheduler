package gui.utils;

import com.badlogic.gdx.math.Vector2;

import gui.Scene;
import utils.Logger;
import utils.Time;

public class Mouse {
	
	private long time_touch_down;
	private Vector2 position_touch_down;
	private boolean long_click_is_possible = true; 
	
	private float time_long_press_in_s = 1.5f;
	private boolean clicked_long_handled = false;
	private boolean touchDown;
	
	private Vector2 mousPosition = new Vector2();
	private Scene scene;
	
	public Mouse(Scene scene) {
		this.scene = scene;
	}
	
	public void touch_down(Vector2 pos) {
		this.mousPosition = pos;
		this.time_touch_down = System.currentTimeMillis();
		this.position_touch_down = pos;	
		this.long_click_is_possible = true;
		this.touchDown = true;
		this.checkForLongClick();
	}
	

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.touchDown = false;
		this.interruptLongClick();
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.mousPosition = new Vector2(screenX, screenY);
		this.interruptLongClick();
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		this.mousPosition = new Vector2(screenX, screenY);
		if(touchDown)
			this.interruptLongClick();
		return false;
	}

	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void checkForLongClick() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(long_click_is_possible && !clicked_long_handled) {
					long curr_time = System.currentTimeMillis();
					if(Time.millisToSeconds(curr_time - time_touch_down) >= time_long_press_in_s) {
						scene.longPress((int)position_touch_down.x, (int)position_touch_down.y);
						clicked_long_handled = true;
					}
				}
				clicked_long_handled = false;
			}
		}, "checkForLongClick");
		t.start();
	}
	
	public void interruptLongClick() {
		this.long_click_is_possible = false;
	}
	
	public Vector2 getMousePosition() {
		return this.mousPosition;
	}
}
