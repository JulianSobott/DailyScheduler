package com.dailyschedulerapp.dailyscheduler.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.catangame.catan.local.Framework;
import com.dailyschedulerapp.dailyscheduler.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.title = "Daily Scheduler";
		config.width = 600;
		config.height = 800;
		config.samples = 8;
		new LwjglApplication(new Main(Main.DeviceMode.DESKTOP), config);
	}
}
