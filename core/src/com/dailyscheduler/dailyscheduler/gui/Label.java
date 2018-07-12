package com.dailyscheduler.dailyscheduler.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	public List<String> all_lines = new LinkedList<String>(); 
	
	protected BitmapFont font = FontManager.getFont(24);
	protected GlyphLayout layout = new GlyphLayout();
	protected int padding = 5;
	protected final int FONT_SIZE_DP = 24;
	protected final int LINE_SPACING = 6;
 	
	public boolean auto_line_break = false;
	public boolean is_in_one_line = false;
	
	private int idx_start_character = 0;
	private int idx_end_character = 0;
	
	
	private static Map<String, Float> width_of_string_cache = new HashMap<>(); 
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

		this.idx_end_character = plain_text.length();

	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0.8f, 0.8f, 0.8f, 1));
		sr.rect(get_absolute_x(), get_absolute_y(), get_absolute_width(), get_absolute_height());
		sr.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sb.begin();
		sb.setColor(new Color(1, 0, 0, 1));
		font.draw(sb, plain_text, get_absolute_x() + padding, get_absolute_y() + padding, idx_start_character, idx_end_character, get_absolute_width() - padding, Align.topLeft, true);
		sb.end();
		super.render(sr, sb);
	}
	
	protected void fitTextToField() {
		this.idx_end_character = this.plain_text.length();
	}
	
	protected void fitFieldToText() {
		
	}
	

	protected float getWidthOfCharW() {
		layout.setText(font, "W");
		return layout.width;
	}
	
	protected float getWidthOfChar(char c) {
		layout.setText(font, Character.toString(c));
		return layout.width;
	}
	protected static float getWidthOfChar(char c, int font_size_dp) {
		BitmapFont font = FontManager.getFont(font_size_dp);
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, Character.toString(c));
		return layout.width;
	}
	
	protected float getLineHeight() {
		return (FontManager.dp_to_pixel(FONT_SIZE_DP) + LINE_SPACING);
	}
	
	public void addChar(char c) {
		this.plain_text += c;
		fitTextToField();
	}
	
	public void setText(String text) {
		this.plain_text = text;
		fitTextToField();
	}
	
	public int getNumLines() {
		layout.setText(font, plain_text, idx_start_character, idx_end_character, new Color(1, 1, 1, 1), get_absolute_width(), Align.topLeft, auto_line_break, null);
		return (int)((layout.height - font.getData().capHeight) / (font.getData().lineHeight));
	}
	
	public static float getWidthOfLine(String line, int font_size_dp) {
		if(width_of_string_cache.containsKey(line)) {
			return width_of_string_cache.get(line);
		}
		char[] chars = new char[line.length()];	
		line.getChars(0, line.length(), chars, 0);
		float width = 0;
		for(char c : chars) {
			width += getWidthOfChar(c, font_size_dp);
		}
		width_of_string_cache.put(line, width);
		return width;
	}

}
