package gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import gui.utils.Bounds;

public class Menu extends Widget {
	
	private final float DIAMETER_CIRCLE = 20;
	
	public Menu(Widget parent, int abs_x, int abs_y) {
		super(parent);
		Vector2 absParentBounds = Bounds.absScrennToAbsParent(new Vector2(abs_x, abs_y), parent);
		this.adjustBounds(absParentBounds.x - DIAMETER_CIRCLE/2, absParentBounds.y - DIAMETER_CIRCLE/2, DIAMETER_CIRCLE, DIAMETER_CIRCLE);
		this.is_hidden = false;
	}
	
	public Menu() {
		this.is_hidden = true;
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		if(!is_hidden) {
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(100/255, 255/255, 100/255, 1));
			sr.ellipse(get_absolute_x(), get_absolute_y(), get_absolute_width(), get_absolute_height());
			sr.end();
			super.render(sr, sb);
		}	
	}
	
	@Override
	protected void update() {
		// TODO Auto-generated method stub

	}

	public void checkTouchUp(Vector2 vector2) {
		
	}

}
