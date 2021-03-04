package com.ourshipsgame.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ourshipsgame.Main;
import com.ourshipsgame.handlers.Constant;

public class DesktopLauncher implements Constant {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Ships Game v1.0 Credits -> Patryk Grzywacz | Dominik Grudzień | Adrian Pełka";
		cfg.addIcon("core/assets/icon/ship.png", FileType.Local);
		cfg.width = GAME_WIDTH;
		cfg.height = GAME_HEIGHT;
		cfg.vSyncEnabled = VSYNCENABLED;
		cfg.allowSoftwareMode = ALLOWSOFTWAREMODE;
		cfg.foregroundFPS = FPSMAX;
		cfg.undecorated = UNDECORATED;
		cfg.fullscreen = false;
		new LwjglApplication(new Main(), cfg);
	}
}
