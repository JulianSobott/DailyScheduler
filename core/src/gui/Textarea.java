package gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import gui.utils.Font;
import gui.utils.Padding;

public class Textarea extends Textfield {

	private List<Integer> idx_auto_line_breaks = new ArrayList<Integer>();
	private List<Integer> idx_existing_line_breaks = new ArrayList<Integer>();
	private List<Integer> idx_all_line_breaks = new ArrayList<Integer>();
	
	private boolean charIsWiderThanRect = false;
	private int idxLastVisibleCharacter = 54;
	private int numLines = 1;
	
	protected TextAreaCursor cursor;
	
	public Textarea(Widget parent, float x, float y, float width, float height, int bounds_flags) {
		super(parent, x, y, width, height, bounds_flags);
		init();
	}

	public Textarea() {
		init();
	}
	
	private void init() {
		this.style.padding = new Padding(9);
		this.style.font = new Font();
		this.cursor = new TextAreaCursor(this);
		this.subWidgets.add(cursor);
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sr.begin(ShapeType.Filled);
		sr.rect(get_absolute_x(), get_absolute_y(), get_absolute_width(), get_absolute_height());
		sr.end();
		
		if(!charIsWiderThanRect && this.formatted_text.length() > 0) {
			sb.begin();
			font.draw(
					sb, this.formatted_text, get_absolute_x() + this.style.padding.left, get_absolute_y() + this.style.padding.top,
					0, idxLastVisibleCharacter, get_absolute_width(), Align.left, false);
			sb.end();
		}
		this.cursor.render(sr, sb);
		//super.render(sr, sb);
	}
	
	@Override
	protected void update() {
		this.idx_auto_line_breaks.clear();
		this.idx_existing_line_breaks.clear();
		this.idx_all_line_breaks.clear();
		calcIndicesLineBreaks();
		insertCalculatedLineBreaks();
		concatToAllLineBreaks();
		calcLastVisibleCharacter();
	}

	@Override
	public boolean check_on_click(Vector2 click_position) {
		boolean clicked = false;
		if(super.check_on_click(click_position)) {
			clicked = true;
			this.cursor.set_indices_by_position(click_position.x, click_position.y);
		}
		return clicked;
	}
	public void setText(String t) {
		this.plain_text = t;
		this.formatted_text = t;
		update();
	}
	
	private void calcIndicesLineBreaks() {
		this.numLines = 1;
		float currentWidth = 0;
		int idxChar = 0;
		int idxLastSpace = 0;
		currentWidth += this.style.padding.left + this.style.padding.right;
		
		while(idxChar < formatted_text.length()) {
			char c = formatted_text.charAt(idxChar);
			if(Character.isWhitespace(c)) {
				idxLastSpace = idxChar;
			}
			if(c == '\n' || c == '\r') {
				idx_existing_line_breaks.add(idxChar);
				currentWidth = this.style.padding.left + this.style.padding.right;
				idxLastSpace = 0;
				this.numLines++;
			}else {
				currentWidth += this.style.font.getWidthofChar(c);
				//TODO ADD letter spacing? currentWidth += this.style.font.gdxFont.
				if(currentWidth > get_absolute_width()) {
					if(idxLastSpace == 0) {
						if(idxChar == 0) charIsWiderThanRect = true;
						idx_auto_line_breaks.add(Math.max(idxChar - 1, 0));
						idxChar -= 2;
					}else {
						idx_auto_line_breaks.add(idxLastSpace + 1);
						idxChar = idxLastSpace;
					}
					currentWidth = this.style.padding.left + this.style.padding.right;
					idxLastSpace = 0;
				}
			}
			
			idxChar++;
		}
	}
	
	private void insertCalculatedLineBreaks() {
		int insertedChars = 0;
		for(int idx_line_break : idx_auto_line_breaks) {
			/*if(Character.isWhitespace(formatted_text.charAt(idx_line_break)))
				formatted_text = formatted_text.substring(0, idx_line_break + insertedChars) + '\n' + formatted_text.substring(idx_line_break + insertedChars + 1);
			else*/
			formatted_text = formatted_text.substring(0, idx_line_break + insertedChars) + '\n' + formatted_text.substring(idx_line_break + insertedChars);
			insertedChars++;
		}
	}
	
	private int getNumWideChars() {
		return plain_text.length() - idx_existing_line_breaks.size();
	}
	
	public int getNumLines() {
		return idx_auto_line_breaks.size() + idx_existing_line_breaks.size() + 1;
	}
	
	@Override
	protected float getLineHeight() {
		return this.style.font.gdxFont.getLineHeight();
	}
	
	public String getLineByIdx(int idx) {
		if(idx > getNumLines() - 1 || idx < 0) return "";
		if(idx == 0)
			return this.formatted_text.substring(0, getIdxLineEndByIdxLine(0));
		if(idx == getNumLines() - 1)
			return this.formatted_text.substring(this.idx_all_line_breaks.get(idx));
		return this.formatted_text.substring(this.idx_all_line_breaks.get(idx - 1), this.idx_all_line_breaks.get(idx));
	}
	public void setTextByLineIdx(int idx, String text) {
		if(idx > getNumLines() - 1 || idx < 0) return;
		if(idx == 0) {
			this.formatted_text = text + this.formatted_text.substring(getIdxLineEndByIdxLine(0));
			return;
		}
		if(idx == getNumLines() - 1) {
			this.formatted_text = this.formatted_text.substring(0, this.idx_all_line_breaks.get(idx - 1)) + text;
			return;
		}
		this.formatted_text = 
				this.formatted_text.substring(0, this.idx_all_line_breaks.get(idx - 1)) + 
				text + 
				this.formatted_text.substring(this.idx_all_line_breaks.get(idx));
	}
	
	private int getIdxLineEndByIdxLine(int idxLine) {
		return this.idx_all_line_breaks.size() == 0 ? this.formatted_text.length() : this.idx_all_line_breaks.get(idxLine - 1);
	}
	
	private void concatToAllLineBreaks() {
		int idx_existing = 0;
		for(int val_auto : idx_auto_line_breaks) {
			if(idx_existing > idx_existing_line_breaks.size() - 1) {
				idx_all_line_breaks.add(val_auto);
			}else {
				if(val_auto < idx_existing_line_breaks.get(idx_existing)) {
					idx_all_line_breaks.add(val_auto);
				}else {
					idx_all_line_breaks.add(idx_existing_line_breaks.get(idx_existing));
					idx_existing++;
				}
			}
			
		}
		for(; idx_existing < idx_existing_line_breaks.size(); idx_existing++) {
			idx_all_line_breaks.add(idx_existing_line_breaks.get(idx_existing));
		}
	}
	
	private void calcLastVisibleCharacter() {
		/*int idxCurrLine = 0;
		float currHeight = 0;
		currHeight = this.style.padding.top + this.style.padding.bot;
		while(currHeight < get_absolute_height() && idxCurrLine < idx_all_line_breaks.size() - 1) {
			currHeight += this.style.font.gdxFont.getLineHeight();
			idxCurrLine++;
		}*/
		int numVisibleLines = (int)((get_absolute_height() - (this.style.padding.top + this.style.padding.bot)) / this.style.font.gdxFont.getLineHeight());
		if(numVisibleLines == 0) {
			idxLastVisibleCharacter = 0;
			return;
		}
		if(numVisibleLines >= this.numLines ) {
			idxLastVisibleCharacter = this.formatted_text.length() ;
		}else {
			idxLastVisibleCharacter = idx_all_line_breaks.get(numVisibleLines - 1);
		}
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
			super.addChar(c);
			//add_char_at_cursor(c);
		}	  
	}
	
	private void delete_previous_to_cursor_char() {
		String currLine = this.all_lines.get(this.cursor.idx_line);
		if(this.cursor.idx_position > 0) {
			String new_line = currLine.substring(0, this.cursor.idx_position - 1) + currLine.substring(this.cursor.idx_position);
			this.setTextByLineIdx(this.cursor.idx_line, new_line);
		}
		this.cursor.move_left();	
		
	}
	
	private void add_char_at_cursor(char c) {
		String currLine = getLineByIdx(this.cursor.idx_line);
		if(this.cursor.idx_position < currLine.length() - 1) {
			String new_line = currLine.substring(0, this.cursor.idx_position ) + c +  currLine.substring(this.cursor.idx_position + 1);			
			this.setTextByLineIdx(this.cursor.idx_line, new_line);
		}else {
			String new_line = currLine.substring(0, this.cursor.idx_position ) + c;
			this.setTextByLineIdx(this.cursor.idx_line, new_line);
			//super.add_new_line_text("");
		}
		this.cursor.move_right();
	}
}
