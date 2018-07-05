package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dailyscheduler.dailyscheduler.utils.Bounds;
import com.dailyscheduler.dailyscheduler.utils.FontManager;

public class Cursor extends Widget{
	public int idx_position = 1;
	public int idx_line = 0;
	private final float extra_height = 3;
	private TextField textField;
	
	
	
	public Cursor(TextField tf) {
		this.parent = tf;
		this.textField = tf;
		this.bounds = new Bounds();
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		super.render(sr, sb);
		calc_absolute_position();
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(.05f, 0.01f, 1.0f, 1));
		sr.line(get_absolute_x(), get_absolute_y() - extra_height/2, get_absolute_x(), get_absolute_y() - extra_height/2 + get_absolute_height());
		sr.end();
	}
	
	private void calc_absolute_position() {
		float margin_left = 0;
		String curr_line = textField.all_lines.get(idx_line);
		char[] chars = new char[curr_line.length()];
		curr_line.getChars(0, curr_line.length(), chars, 0);
		for(int i = 0; i < idx_position; i++) {
			margin_left += textField.getWidthOfChar(chars[i]);
		}
		this.bounds.x = margin_left + textField.padding;
		this.bounds.y = idx_line * textField.getLineHeight() + textField.padding;
		this.bounds.height = FontManager.dp_to_pixel(textField.FONT_SIZE_DP) + extra_height;
	}
	
	protected void set_indices_by_position(float x, float y) {
		Vector2 inner_position = global_position_to_inner_position(x, y);
		this.idx_line = (int)(inner_position.y / textField.getLineHeight());
		this.idx_position = 0;
		
		float margin_left = 0;
		String curr_line = textField.all_lines.get(idx_line);
		char[] chars = new char[curr_line.length()];
		curr_line.getChars(0, curr_line.length(), chars, 0);
		for(int i = 0; i < curr_line.length(); i++) {
			margin_left += textField.getWidthOfChar(chars[i]);
			if(margin_left < inner_position.x)
				this.idx_position++;
			else
				break;
		}
	}
}
