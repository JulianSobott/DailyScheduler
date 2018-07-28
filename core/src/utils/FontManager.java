package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

import debug.Profiler;

public class FontManager {

	private static Map<Float, BitmapFont> fonts = new HashMap<Float, BitmapFont>();
	public FontManager() {
		
	}
	
	public static BitmapFont getFont(float dp) {
		if(fonts.containsKey(dp)){
			return fonts.get(dp);
		}
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Italic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        int fontSize = (int)(dp * Gdx.graphics.getDensity());
        parameter.size = fontSize;
        parameter.flip = true;
		BitmapFont font = generator.generateFont(parameter);
		fonts.put(dp, font);
        return font;
	}
	
	public static int dp_to_pixel(float dp) {
		return (int)(dp * Gdx.graphics.getDensity());
	}

}
