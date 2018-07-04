package com.dailyschedulerapp.dailyscheduler;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dailyschedulerapp.dailyscheduler.gui.Scene;
import com.dailyschedulerapp.dailyscheduler.gui.Widget;

public class MainScreen extends Scene implements InputProcessor{
	private Vector2 touchStart;
	private Vector2 touchCurrent;
	private int idx_active_task_field;
	
	public Timeline timeline = new Timeline();
	public List<TaskField> taskFields = new ArrayList<TaskField>();
	
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		super.render(sr, sb);
		timeline.render(sr, sb);
		for(TaskField taskField : taskFields) {
			taskField.render(sr, sb);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(TaskField tf : taskFields) {
			tf.is_active = false;
		}
		for(Widget widget : subWidgets) {
			if(widget.check_on_click(new Vector2(screenX, screenY))) {
				return true;
			}
		}
		
		touchStart = new Vector2(screenX, screenY);
		touchCurrent = touchStart;
		TaskField taskField = new TaskField();
		taskField.set_start(screenY);
		taskFields.add(taskField);
		this.subWidgets.add(taskField);
		idx_active_task_field = taskFields.size() - 1;
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		touchCurrent = new Vector2(screenX, screenY);
		taskFields.get(idx_active_task_field).update_position_down(screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
