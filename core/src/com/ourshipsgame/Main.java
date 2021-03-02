package com.ourshipsgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Ships!";
        config.width = 1920;
        config.height = 1080;
        config.fullscreen = false;
        config.foregroundFPS = 60;
        config.resizable = true;
        System.out.println("App launched w:" + config.width + " h:" + config.height);
        new LwjglApplication(new MyGame(), config);
    }
}
