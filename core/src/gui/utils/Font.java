package gui.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import utils.FontManager;

public class Font implements StyleValue{

	public int fontSizeDP = 24;
	public BitmapFont gdxFont = FontManager.getFont(fontSizeDP); 
	
	public Font() {
		// TODO Auto-generated constructor stub
	}
	
	public float getWidthofChar(char c) {
		GlyphLayout layout = new GlyphLayout();
		layout.setText(gdxFont, Character.toString(c));
		return layout.width;
	}
}
