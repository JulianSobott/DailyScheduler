package gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import gui.utils.Font;
import gui.utils.Padding;

public class Textarea extends Label {

	private String text = "";
	private List<Integer> idx_auto_line_breaks = new ArrayList<Integer>();
	private List<Integer> idx_existing_line_breaks = new ArrayList<Integer>();
	
	private boolean charIsWiderThanRect = false;
	
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
	}
	
	@Override
	public void render(ShapeRenderer sr, SpriteBatch sb) {
		sr.begin(ShapeType.Filled);
		sr.rect(get_absolute_x(), get_absolute_y(), get_absolute_width(), get_absolute_height());
		sr.end();
		
		if(!charIsWiderThanRect) {
			sb.begin();
			font.draw(sb, text, get_absolute_x() + this.style.padding.left, get_absolute_y() + this.style.padding.top);
			sb.end();
		}
		
	}
	
	public void changed() {
		this.idx_auto_line_breaks.clear();
		this.idx_existing_line_breaks.clear();
		calcIndicesLineBreaks();
		insertCalculatedLineBreaks();
	}
	
	public void setText(String t) {
		this.text = t;
		changed();
	}
	
	private void calcIndicesLineBreaks() {
		float currentWidth = 0;
		int idxChar = 0;
		int idxLastSpace = 0;
		currentWidth += this.style.padding.left + this.style.padding.right;
		
		while(idxChar < text.length()) {
			char c = text.charAt(idxChar);
			if(Character.isWhitespace(c)) {
				idxLastSpace = idxChar;
			}
			if(c == '\n' || c == '\r') {
				idx_existing_line_breaks.add(idxChar);
				currentWidth = this.style.padding.left + this.style.padding.right;
				idxLastSpace = 0;
			}
			currentWidth += this.style.font.getWidthofChar(c);
			//TODO ADD letter spacing? currentWidth += this.style.font.gdxFont.
			if(currentWidth > get_absolute_width()) {
				if(idxLastSpace == 0) {
					if(idxChar == 0) charIsWiderThanRect = true;
					idx_auto_line_breaks.add(Math.max(idxChar - 1, 0));
				}else {
					idx_auto_line_breaks.add(idxLastSpace);
				}
				currentWidth = this.style.padding.left + this.style.padding.right;
				idxLastSpace = 0;
			}
			idxChar++;
		}
	}
	
	private void insertCalculatedLineBreaks() {
		int insertedChars = 0;
		for(int idx_line_break : idx_auto_line_breaks) {
			if(Character.isWhitespace(text.charAt(idx_line_break)))
				text = text.substring(0, idx_line_break + insertedChars) + '\n' + text.substring(idx_line_break + insertedChars + 1);
			else
				text = text.substring(0, idx_line_break + insertedChars + 1) + '\n' + text.substring(idx_line_break + insertedChars + 1);
			insertedChars++;
		}
	}
	
	private int getNumWideChars() {
		return text.length() - idx_auto_line_breaks.size() - idx_existing_line_breaks.size();
	}
	
}
