package gui.utils;

public class Style {

	public Padding padding;
	public Font font;
	
	public Style() {
		// TODO Auto-generated constructor stub
	}

	public void add_sytle(StyleValue style){
		if(style instanceof Padding) {
			this.padding = (Padding) style;
		}
	}
}
