package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

    private final String ID = getClass().getName();

    // vars
    private Game game;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(ID, "This class is loaded!");
    }

    @Override
    public void render(float delta) {
        // game loop
        super.render(delta);
    }

    @Override
    public void show() {
        Gdx.app.log(ID, "The game is running");
    }

    @Override
    public void pause() {
        Gdx.app.log(ID, "The game is paused");
    }

    @Override
    public void resume() {
        Gdx.app.log(ID, "The game is resumed");
    }
}
