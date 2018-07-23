package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import gui.utils.Bounds;
import utils.FontManager;
import utils.Time;

public class TimeBox extends Label {
	private final float HEIGHT = 30.f;
	private final float WIDTH = 45.f;

	protected BitmapFont font = FontManager.getFont(24);
	public Time time = new Time(0);
	
	public TimeBox(Widget parent, float y, float width) {
		super();
		this.parent = parent;
		
		this.bounds = new Bounds(this.parent.get_absolute_width(), 	y, width, getLineHeight(), Bounds.relative_y);

		this.centered_y = true;
	}

	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		if(!is_hidden) {
			
			super.setText(time.toString()); //TODO extract to function
			super.render(sr, sb);
		}
	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		// TODO Auto-generated method stub
		return super.check_on_click(click_position);
	}
}
