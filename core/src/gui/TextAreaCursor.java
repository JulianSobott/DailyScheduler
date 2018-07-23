package gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import gui.utils.Bounds;
import utils.FontManager;

public class TextAreaCursor extends Widget {

	public int idx_position = 0;
	public int idx_line = 0;
	private final float extra_height = 3;
	private Textarea textarea;
	
	private float blink_time = 0.7f;
	private long last_toggle_time = 0;
	
	private boolean isMultiline = false;
	
	public TextAreaCursor(Textarea ta) {
		this.parent = ta;
		this.textarea = ta;
		this.bounds = new Bounds();
		this.idx_line = Math.max(textarea.all_lines.size() - 1, 0);
		this.idx_position = Math.max(textarea.getNumLines() == 0 ? 0 : textarea.getLineByIdx(idx_line).length() - 1, 0);
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		super.render(sr, sb);
		if(System.currentTimeMillis() - last_toggle_time > blink_time * 1000) {
			this.is_hidden = !this.is_hidden;
			last_toggle_time = System.currentTimeMillis();
		}
		if(!this.is_hidden && textarea.is_active) {
			calc_absolute_position();
			sr.begin(ShapeType.Filled);
			sr.setColor(new Color(.05f, 0.01f, 1.0f, 1));
			sr.line(get_absolute_x(), get_absolute_y() - extra_height/2, get_absolute_x(), get_absolute_y() - extra_height/2 + get_absolute_height());
			sr.end();
			
		}
	}
	
	private void calc_absolute_position() {
		float margin_left = 0;
		String curr_line = "";

		curr_line = this.textarea.getLineByIdx(idx_line);
		char[] chars = new char[curr_line.length()];
		curr_line.getChars(0, curr_line.length(), chars, 0);
		for(int i = 0; i < idx_position; i++) {
			margin_left += textarea.getWidthOfChar(chars[i]);
		}
		this.bounds.x = margin_left + textarea.style.padding.left;
		this.bounds.y = idx_line * textarea.getLineHeight() + textarea.style.padding.top;
		this.bounds.height = FontManager.dp_to_pixel(textarea.FONT_SIZE_DP) + extra_height;
	}
	
	protected void set_indices_by_position(float x, float y) {
		Vector2 inner_position = global_position_to_inner_position(x, y);
		
		calcIdxLineByY(y, inner_position.y);
		calcIdxPositionByX(x, inner_position.x);
		/*this.idx_position = 0;
		
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
		*/
		this.show();
	}
	
	private void calcIdxPositionByX(float x, float innerX) {
		String currentLine = "";
		currentLine = this.textarea.getLineByIdx(this.idx_line);
		float currCursorX = this.textarea.style.padding.left;
		char[] chars = new char[currentLine.length()];
		currentLine.getChars(0, currentLine.length(), chars, 0);
		this.idx_position = 0;
		for(int i = 0; i < currentLine.length(); i++) {
			currCursorX += textarea.getWidthOfChar(chars[i]);
			if(currCursorX < innerX)
				this.idx_position++;
			else
				break;
		}
	}

	private void calcIdxLineByY(float y, float innerY) {
		if(innerY <= 0) {
			this.idx_line = 0;
			return;
		}
		if(y > innerY + this.textarea.get_absolute_height()) {
			this.idx_line = ((Textarea) textarea).getNumLines() - 1; 
			return;
		}
		
		this.idx_line = (int)(innerY / ((Textarea)textarea).getLineHeight());
		this.idx_line = Math.min(idx_line, ((Textarea)textarea).getNumLines() - 1);
	}

	public void move_left() {
		this.show();
		if(this.idx_position > 0)
			this.idx_position--;
		else {
			if(this.idx_position != 0 || this.idx_line != 0) {
				move_up();
				move_end();
			}
		}
		
	}
	
	public void move_right() {
		this.show();
		if(this.idx_position < this.textarea.getLineByIdx(this.idx_line).length()) {
			this.idx_position++;
		}else {
			if(this.idx_line < this.textarea.getNumLines() - 1) {
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
			if(this.idx_position > this.textarea.getLineByIdx(this.idx_line).length())
				move_end();
		}
		else
			move_pos1();
	}

	public void move_down() {
		this.show();
		if(this.idx_line < this.textarea.getNumLines() - 1) {
			this.idx_line++;
			if(this.idx_position > this.textarea.getLineByIdx(this.idx_line).length())
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
		this.idx_position = this.textarea.getLineByIdx(this.idx_line).length();
	}
	
	
	@Override
	public void show() {
		this.is_hidden = false;
		last_toggle_time = System.currentTimeMillis();
	}

	@Override
	protected void update() {
		
	}
}
