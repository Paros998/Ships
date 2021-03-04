package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ourshipsgame.handlers.Constant;

public class GameScreen extends ScreenAdapter implements Constant {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private float runTime;
    // other vars
    private Texture bgTexture;
    private Texture shipTexture;
    private Sprite bgSprite;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
    }

    // method to create elements
    private void createGraphics() {

        // textures
        bgTexture = new Texture("core/assets/backgroundtextures/paper_bg.png");
        bgTexture.setAnisotropicFilter(16);
        shipTexture = new Texture("core/assets/icon/statek.png");
        shipTexture.setAnisotropicFilter(16);
        // sprites
        bgSprite = new Sprite(shipTexture);
        bgSprite.setBounds(100, 100, 35, 35);
        // etc

    }

    private void handleInput() {
        /// Buttons pressed

        // Mouse Events Later

    }

    // update logics method
    private void update(float deltaTime) {
        runTime += deltaTime;
        handleInput();
    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        // buffer screen
        Gdx.gl20.glClearColor(1, 0, .5f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // update
        update(deltaTime);
        // render things
        sb.begin();

        sb.end();
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        createGraphics();

        Gdx.app.log(id, "The game is running");
    }

    @Override
    public void pause() {
        Gdx.app.log(id, "The game is paused");
    }

    @Override
    public void resume() {
        Gdx.app.log(id, "The game is resumed");
    }
}
