package main;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import debug.Profiler;
import gui.Label;
import gui.Scene;
import gui.TaskField;
import gui.Textarea;
import gui.Timeline;
import gui.Widget;

public class MainScreen extends Scene implements InputProcessor{
	private Vector2 touchStart;
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
		if(taskFields.size() > 0) {
			taskFields.get(this.idx_active_task_field).handle_key_input(keycode);
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		if(taskFields.size() > 0) {
			taskFields.get(this.idx_active_task_field).handle_char_input(character);
		}

		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(TaskField tf : taskFields) {
			tf.deactivate();
		}
		for(Widget widget : subWidgets) {
			if(widget.check_on_click(new Vector2(screenX, screenY))) {
				if(widget instanceof TaskField) {
					int i = 0;
					for(TaskField tf: taskFields) {
						if(tf == widget)
							idx_active_task_field = i;
						i++;
					}
				}
				return true;
			}
		}

		touchStart = new Vector2(screenX, screenY);
		TaskField taskField = new TaskField(timeline);

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
		taskFields.get(idx_active_task_field).update_position(screenY);
		return true;
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

	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}
}
