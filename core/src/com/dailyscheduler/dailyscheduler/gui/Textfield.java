package com.dailyscheduler.dailyscheduler.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.dailyscheduler.dailyscheduler.utils.FontManager;

public class Textfield extends Widget {

	private String plain_text = "";
	private String formatted_text = "";
	private List<String> all_lines = new ArrayList<String>();
	
	private BitmapFont font = FontManager.getFont(24);
	private GlyphLayout layout = new GlyphLayout();
	private int padding = 5;
	private final int FONT_SIZE_DP = 24;
	private final int LINE_SPACING = 4;
 	
	public boolean auto_line_break = true;
	
	
	public Textfield(Widget parent) {
		super(parent);
		init();
	}

	public Textfield() {
		init();
	}

	private void init() {
		this.plain_text = "WOW WOW WOWO WOg";
		this.all_lines.add(plain_text);
		this.x = 10;
		this.y = 10;
		this.width = 40;
		this.height = FontManager.dp_to_pixel(FONT_SIZE_DP) * 4 + 2*padding;
		formatText();
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		
		sr.begin(ShapeType.Filled);
		sr.rect(get_absolute_x() - this.padding, get_absolute_y() - this.padding, this.width + this.padding * 2, this.height + this.padding * 2);
		sr.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sb.begin();
		sb.setColor(new Color(1, 0, 0, 0));
		int idx_line = 0;
		for(String str : all_lines) {
			font.draw(sb, str, this.x, this.y + idx_line * FontManager.dp_to_pixel(FONT_SIZE_DP));
			idx_line++;
			if(font.getLineHeight() * idx_line > this.height) {
				break;
			}
		}
		
		sb.end();
		super.render(sr, sb);
	}
	
	private void formatText() {
		if(auto_line_break) {
			formatted_text = plain_text;
			layout.setText(font, all_lines.get(all_lines.size() - 1));
			int idx_last_new_line = 0;
			while(layout.width > this.width && idx_last_new_line != -1) { 
				idx_last_new_line = addNewLine(idx_last_new_line, this.width);
				layout.setText(font, all_lines.get(all_lines.size() - 1));
			}			
		}
	}
	
	private int addNewLine(int idx_last_new_line, float max_width) {
		layout.setText(font, "C");
		float charWidth = layout.width;
		int currentLineWidth = 0;
		int idx_possible_new_line = 0;
		int idx_char = 0;
		String curr_line = all_lines.get(all_lines.size() - 1);
		char[] chars = new char[curr_line.length()];
		
		curr_line.getChars(0, curr_line.length(), chars, 0);
		for(char c : chars) {
			if(c == ' ') {
				idx_possible_new_line = idx_char;
			}
			idx_char++;
			currentLineWidth += charWidth;
			if(currentLineWidth > max_width) {
				all_lines.set(all_lines.size() - 1,  curr_line.substring(0, idx_possible_new_line));
				all_lines.add(curr_line.substring(idx_possible_new_line + 1));
				formatted_text = formatted_text.substring(idx_last_new_line, idx_possible_new_line) + '\n' + formatted_text.substring(idx_possible_new_line + 1);
				idx_last_new_line = idx_possible_new_line;
				return idx_last_new_line;
			}
		}
		return -1;
	}
}
