package gui;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import datahandling.DataHandler;
import datahandling.Request;
import debug.Profiler;
import gui.utils.Mouse;
import utils.Event;

public abstract class Scene extends Widget implements InputProcessor{
	
	public DataHandler dataHandler;
	private Queue<Event> events = new LinkedList<Event>();
	private Mouse mouse;
	
	public Scene(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
		this.mouse = new Mouse(this);
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		this.handleEvents();
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
		this.mouse.touch_down(new Vector2(screenX, screenY));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.mouse.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.mouse.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.mouse.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.mouse.scrolled(amount);
		return false;
	}
	
	public void saveRequest() {
		this.dataHandler.addRequest(Request.save);
	}
	
	public void longPress(int screenX, int screenY) {
		System.out.println("long pressed scene");
	}
	
	public void addEvent(Event event) {
		this.events.add(event);
	}
	
	private void handleEvents() {
		for(Event e : events) {
			switch(e) {
			case long_press:
				//TODO useful implementation
			}
		}
	}
}
