package com.dailyscheduler.dailyscheduler.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager {
	public FontManager() {
		
	}
	
	public static BitmapFont getFont(float dp) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Italic.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        int fontSize = (int)(dp * Gdx.graphics.getDensity());
        parameter.size = fontSize;
        parameter.flip = true;

        return generator.generateFont(parameter);
	}
	
	public static int dp_to_pixel(float dp) {
		return (int)(dp * Gdx.graphics.getDensity());
	}

}
