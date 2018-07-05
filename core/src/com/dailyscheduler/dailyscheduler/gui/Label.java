package com.dailyscheduler.dailyscheduler.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.dailyscheduler.dailyscheduler.utils.Bounds;
import com.dailyscheduler.dailyscheduler.utils.FontManager;

public class Label extends Widget {

	protected String plain_text = "";
	protected String formatted_text = "";
	protected List<String> all_lines = new ArrayList<String>();
	
	protected BitmapFont font = FontManager.getFont(24);
	protected GlyphLayout layout = new GlyphLayout();
	protected int padding = 5;
	protected final int FONT_SIZE_DP = 24;
	protected final int LINE_SPACING = 6;
 	
	public boolean auto_line_break = false;
	public boolean is_in_one_line = false;
	
	private int idx_start_character = 0;
	private int idx_end_character = 0;
	
	/**
	 * 
	 * @param parent
	 * @param x
	 * @param y
	 * @param width
	 * @param height if 0 size is set to line_height 
	 * @param position 
	 */
	public Label(Widget parent, float x, float y, float width, float height, int bounds_flags) {
		super(parent);
		init(x, y, width, height, bounds_flags);
	}

	public Label() {
		init(0, 0, 100, 0, 0);
	}

	private void init(float x, float y, float width, float height, int bounds_flags) {
		this.bounds = new Bounds(
				x,
				y,
				width,
				Bounds.isRelative(bounds_flags, Bounds.relative_height) ? height : Math.max(FontManager.dp_to_pixel(FONT_SIZE_DP), height),
				bounds_flags);
		setText("ioWeCg H--@ lovt ÜWW D\nVWWqooooiii");
		this.idx_end_character = plain_text.length();
		fitTextToField();
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		
		sr.begin(ShapeType.Filled);
		sr.rect(get_absolute_x(), get_absolute_y(), get_absolute_width(), get_absolute_height());
		sr.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sb.begin();
		sb.setColor(new Color(1, 0, 0, 0));
		int idx_line = 0;
		if(all_lines.size() == 1)
			font.draw(sb, plain_text, 
					get_absolute_x() + this.padding,
					get_absolute_y() + this.padding,
					idx_start_character, idx_end_character,
					get_absolute_width(), Align.right, false);
		else {
			for(String str : all_lines) {
				if(font.getLineHeight() * (idx_line + 1) > get_absolute_height()) {
					break;
				}
				font.draw(sb, str, 
						get_absolute_x() + this.padding,
						get_absolute_y() + this.padding + idx_line * getLineHeight());
				idx_line++;	
			}
		}
		
		
		sb.end();
		super.render(sr, sb);
	}
	
	protected void fitTextToField() {
		if(auto_line_break || !is_in_one_line) {
			formatted_text = plain_text;
			layout.setText(font, all_lines.get(all_lines.size() - 1));
			int idx_last_new_line = 0;
			int idx_line = 0;
			while(layout.width > this.bounds.width && idx_last_new_line != -1 || idx_line < all_lines.size() - 1) { 
				idx_last_new_line = addNewLine(idx_last_new_line, get_absolute_width(), idx_line);
				layout.setText(font, all_lines.get(all_lines.size() - 1));
				idx_line++;
			}			
		}else {
			int max_characters_per_line = (int)(this.bounds.width/getWidthOfChar());
			idx_start_character = Math.max(plain_text.length() - max_characters_per_line , 0);
			idx_end_character = plain_text.length();
		}
	}
	
	protected void fitFieldToText() {
		this.bounds.height = (FontManager.dp_to_pixel(FONT_SIZE_DP) + LINE_SPACING) * all_lines.size() + 2 * padding;
		for(String str : all_lines) {
			if(str.length() * getWidthOfChar() > this.bounds.width)
				this.bounds.width = str.length() * getWidthOfChar();
		}
	}
	
	private int addNewLine(int idx_last_new_line, float max_width, int idx_line) {	
		int currentLineWidth = 0;
		int idx_possible_new_line = 0;
		int idx_char = 0;
		String curr_line = all_lines.get(idx_line);
		char[] chars = new char[curr_line.length()];
		
		curr_line.getChars(0, curr_line.length(), chars, 0);
		for(char c : chars) {
			if(c == ' ') {
				idx_possible_new_line = idx_char;
			}
			idx_char++;
			currentLineWidth += getWidthOfChar();
			if(currentLineWidth > max_width) {
				all_lines.set(idx_line,  curr_line.substring(0, idx_possible_new_line));
				all_lines.add(idx_line + 1, curr_line.substring(idx_possible_new_line + 1));
				formatted_text = formatted_text.substring(idx_last_new_line, idx_possible_new_line) + '\n' + formatted_text.substring(idx_possible_new_line + 1);
				idx_last_new_line = idx_possible_new_line;
				return idx_last_new_line;
			}
		}
		return -1;
	}

	protected float getWidthOfChar() {
		layout.setText(font, "W");
		return layout.width;
	}
	
	protected float getWidthOfChar(char c) {
		layout.setText(font, Character.toString(c));
		return layout.width;
	}
	
	protected float getLineHeight() {
		return (FontManager.dp_to_pixel(FONT_SIZE_DP) + LINE_SPACING);
	}
	
	public void setText(String plainText) {
		this.plain_text = plainText;
		all_lines.add(plainText);
		if(auto_line_break || !is_in_one_line) {
			int idx;
			this.formatted_text = new String(plainText);
			while((idx = formatted_text.indexOf('\n')) != -1) {
				all_lines.set(all_lines.size() - 1,  formatted_text.substring(0, idx));
				formatted_text = formatted_text.substring(idx + 1);
				all_lines.add(formatted_text);
			}
			fitTextToField();
		}
	}

}
