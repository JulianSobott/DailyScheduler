package com.dailyscheduler.dailyscheduler.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.dailyscheduler.dailyscheduler.utils.Bounds;
import com.dailyscheduler.dailyscheduler.utils.FontManager;

public class Cursor extends Widget{
	public int idx_position = 0;
	public int idx_line = 0;
	private final float extra_height = 3;
	private TextField textField;
	
	private float blink_time = 0.7f;
	private long last_toggle_time = 0;
	
	public Cursor(TextField tf) {
		this.parent = tf;
		this.textField = tf;
		this.bounds = new Bounds();
		this.idx_line = Math.max(textField.all_lines.size() - 1, 0);
		this.idx_position = Math.max(textField.all_lines.size() == 0 ? 0 : textField.all_lines.get(idx_line).length() - 1, 0);
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		/*super.render(sr, sb);
		if(System.currentTimeMillis() - last_toggle_time > blink_time * 1000) {
			this.is_hidden = !this.is_hidden;
			last_toggle_time = System.currentTimeMillis();
		}
		if(!this.is_hidden && textField.is_active) {
			calc_absolute_position();
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(.05f, 0.01f, 1.0f, 1));
			sr.line(get_absolute_x(), get_absolute_y() - extra_height/2, get_absolute_x(), get_absolute_y() - extra_height/2 + get_absolute_height());
			sr.end();
			
		}*/
	}
	
	private void calc_absolute_position() {
		/*float margin_left = 0;
		String curr_line = textField.all_lines.get(idx_line);
		char[] chars = new char[curr_line.length()];
		curr_line.getChars(0, curr_line.length(), chars, 0);
		for(int i = 0; i < idx_position; i++) {
			margin_left += textField.getWidthOfChar(chars[i]);
		}
		this.bounds.x = margin_left + textField.padding;
		this.bounds.y = idx_line * textField.getLineHeight() + textField.padding;
		this.bounds.height = FontManager.dp_to_pixel(textField.FONT_SIZE_DP) + extra_height;
		*/
	}
	
	protected void set_indices_by_position(float x, float y) {
		/*Vector2 inner_position = global_position_to_inner_position(x, y);
		this.idx_line = Math.max((int)(inner_position.y / textField.getLineHeight()), 0);
		this.idx_line = Math.min(idx_line, textField.all_lines.size() - 1);
		
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
		this.idx_position = Math.min(idx_position, curr_line.length());
		this.show();
		*/
	}
	
	public void move_left() {
		this.show();
		if(this.idx_position > 0)
			this.idx_position--;
		else {
			move_up();
			move_end();
		}
		
	}
	
	public void move_right() {
		this.show();
		if(this.idx_position < this.textField.all_lines.get(this.idx_line).length()) {
			this.idx_position++;
		}else {
			if(this.idx_line < this.textField.all_lines.size() - 1) {
				move_down();
				move_pos1();
			}
			else
				move_end();
		}
	}

	public void move_up() {
		this.show();
		if(this.idx_line > 0) {
			this.idx_line--;
			if(this.idx_position > this.textField.all_lines.get(this.idx_line).length())
				move_end();
		}
		else
			move_pos1();
	}

	public void move_down() {
		this.show();
		if(this.idx_line < this.textField.all_lines.size() - 1) {
			this.idx_line++;
			if(this.idx_position > this.textField.all_lines.get(this.idx_line).length())
				move_end();
		}
		else
			move_end();
	}
	
	public void move_pos1() {
		this.show();
		this.idx_position = 0;
	}
	
	public void move_end() {
		this.show();
		this.idx_position = this.textField.all_lines.get(this.idx_line).length();
	}
	
	
	@Override
	public void show() {
		this.is_hidden = false;
		last_toggle_time = System.currentTimeMillis();
	}
}