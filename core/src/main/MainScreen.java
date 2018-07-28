package main;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import datahandling.DataHandler;
import debug.Profiler;
import gui.Label;
import gui.Menu;
import gui.Scene;
import gui.TaskField;
import gui.Textarea;
import gui.Timeline;
import gui.Widget;
import gui.utils.Bounds;

public class MainScreen extends Scene implements InputProcessor{
	
	public MainScreen(DataHandler dataHandler) {
		super(dataHandler);
	}

	private Vector2 touchStart;
	private int idx_active_task_field;
	
	public Timeline timeline = new Timeline();
	public List<TaskField> taskFields = new ArrayList<TaskField>();
	private Menu menu = new Menu();
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {

		super.render(sr, sb);
		timeline.render(sr, sb);
		for(TaskField taskField : taskFields) {
			taskField.render(sr, sb);
		}
		
		menu.render(sr, sb);
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
		super.touchDown(screenX, screenY, pointer, button);
		for(TaskField tf : taskFields) {
			tf.deactivate(); 
		}
		boolean clicked = false;
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
		if(clicked) 
			return true;

		Profiler.start("new taskfield", 104);
		touchStart = new Vector2(screenX, screenY);
		TaskField taskField = new TaskField(this, timeline);
		Profiler.end(104);
		taskField.set_start(screenY);
		taskFields.add(taskField);
		this.subWidgets.add(taskField);
		idx_active_task_field = taskFields.size() - 1;
		addedNewWidgetForSaving(taskField);
		Profiler.end(100);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		super.touchUp(screenX, screenY, pointer, button);
		this.menu.checkTouchUp(new Vector2(screenX, screenY));
		this.menu.hide();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		super.touchDragged(screenX, screenY, pointer);
		boolean overlaps = false;
		TaskField active_tf = this.taskFields.get(this.idx_active_task_field);
		Bounds previousBounds = new Bounds(active_tf.bounds);
		active_tf.update_position(screenY);			
		for(TaskField tf : taskFields) {
			if(!tf.is_active) {
				if(Bounds.overlap(tf.get_absolute_bounds(), active_tf.get_absolute_bounds()))
					overlaps = true;
			}	
		}
		if(overlaps) {
			active_tf.bounds = previousBounds;	
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		super.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		super.scrolled(amount);
		return false;
	}
	
	@Override
	public void longPress(int screenX, int screenY) {
		TaskField active_tf = this.taskFields.get(this.idx_active_task_field);
		if(Bounds.is_position_inside(new Vector2(screenX, screenY), active_tf)) {
			this.menu = new Menu(active_tf, screenX, screenY);
		}
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}
	
	private void addedNewWidgetForSaving(Widget w) {
		if(w instanceof TaskField) {
			this.dataHandler.addNewTask(((TaskField)w).task);
		}
	}
}
