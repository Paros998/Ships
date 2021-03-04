package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen extends ScreenAdapter implements Constant {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private float runTime;

    public MenuScreen(Game game) {
        this.game = game;
    }

    private void createGraphics() {

    }

    private void handleInput() {
        /// Buttons pressed
    }

}
