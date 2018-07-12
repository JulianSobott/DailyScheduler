package com.dailyscheduler.dailyscheduler.gui;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class TextField extends Label implements Clickable{
	public boolean is_active;
	private Cursor cursor;
	
	public TextField(Widget parent, float x, float y, float width, float height, int bounds_flags) {
		super(parent, x, y, width, height,bounds_flags);
		init();
	}

	public TextField() {
		super();
		init();
	}
	
	private void init() {
		this.is_active = true;
		this.cursor = new Cursor(this);
		this.subWidgets.add(cursor);
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		super.render(sr, sb);

	}
	
	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked;
		if(clicked = super.check_on_click(click_position)) {
			is_active = true;
			cursor.set_indices_by_position(click_position.x, click_position.y);
		}
		return clicked;
	}

	@Override
	public void activate() {
		this.is_active = true;
	}

	@Override
	public void deactivate() {
		this.is_active = false;
	}
	
	
	public void handle_key_input(int key_code) {
		switch (key_code) {
		case Input.Keys.LEFT:
			cursor.move_left();
			break;
		case Input.Keys.UP:
			cursor.move_up();
			break;
		case Input.Keys.RIGHT:
			cursor.move_right();
			break;
		case Input.Keys.DOWN:
			cursor.move_down();
			break;
		case Input.Keys.END:
			cursor.move_end();
			break;
		case Input.Buttons.BACK:
			cursor.move_pos1();
			break;
		case Input.Keys.BACKSPACE:
			delete_previous_to_cursor_char();
			break;
		case Input.Keys.SPACE:
			add_char_at_cursor(' ');
			break;
		default:
			break;
		}
	}
	public void handle_char_input(char c) {
		String all_possible_chars = "[0-9a-zA-Z~#;:?/@&!\"'´`%*=¬.,-^\\s]+";
		if(String.valueOf(c).matches(all_possible_chars)) {
			add_char_at_cursor(c);
		}	  
	}
	
	private void delete_previous_to_cursor_char() {
		String currLine = this.all_lines.get(this.cursor.idx_line);
		if(this.cursor.idx_position > 0) {
			String new_line = currLine.substring(0, this.cursor.idx_position - 1) + currLine.substring(this.cursor.idx_position);
			this.all_lines.set(this.cursor.idx_line, new_line);
		}
		this.cursor.move_left();	
		
	}
	
	private void add_char_at_cursor(char c) {
		String currLine = this.all_lines.get(this.cursor.idx_line);
		if(this.cursor.idx_position < currLine.length() - 1) {
			String new_line = currLine.substring(0, this.cursor.idx_position ) + c +  currLine.substring(this.cursor.idx_position + 1);
			this.all_lines.set(this.cursor.idx_line, new_line);
		}else {
			String new_line = currLine.substring(0, this.cursor.idx_position ) + c;
			this.all_lines.set(this.cursor.idx_line, new_line);
			//super.add_new_line_text("");
		}
		this.cursor.move_right();
	}
}
