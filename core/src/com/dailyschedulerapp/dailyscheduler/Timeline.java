package com.dailyschedulerapp.dailyscheduler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dailyschedulerapp.dailyscheduler.utils.Time;

public class Timeline implements InputProcessor{
	final Time dayStart = new Time(7);
	final Time dayEnd = new Time(22);
	
	
	BitmapFont font = new BitmapFont(true);
	
	void render(ShapeRenderer sr, SpriteBatch sb) {
		sb.begin();
		int idx = 0;
		float gap = Gdx.graphics.getHeight() / (dayEnd.hour - dayStart.hour + 2);
		for(int hour = dayStart.hour; hour <= dayEnd.hour; hour++) {
			font.draw(sb, hour + ":00", 10, idx * gap + gap);
			idx++;
		}
		sb.end();

		sr.begin(ShapeType.Filled);
		idx = 0;
		for(int hour = dayStart.hour; hour <= dayEnd.hour; hour++) {
			float y = idx * gap + gap + 7;
			sr.line(50, y, Gdx.graphics.getWidth(), y);
			idx++;
		}
		sr.end();
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
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
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
