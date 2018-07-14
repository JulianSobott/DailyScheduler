package gui.utils;

public class Padding implements StyleValue{

	public float top;
	public float right;
	public float bot;
	public float left;
	
	public Padding(float all) {
		this(all, all);
	}
	
	public Padding(float side, float top_bot) {
		this(top_bot, side, top_bot, side);
	}
	
	public Padding(float top, float right, float bot, float left) {
		this.top = top;
		this.right = right;
		this.bot = bot;
		this.left = left;
	}
}
