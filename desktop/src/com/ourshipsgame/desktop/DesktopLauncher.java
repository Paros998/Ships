package com.ourshipsgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ourshipsgame.Main;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 1920;
		cfg.height = 1080;
		new LwjglApplication(new Main(), cfg);
	}
}
