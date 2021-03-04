package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends GameEngine {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private float runTime;
    private GameObject mapTexture;
    // other vars

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
    }

    // method to create elements
    private void createGraphics() {
        // textures
        mapTexture = new GameObject("core/assets/backgroundtextures/map.png", 0, 0, false);
        // sprites
        preparation(true);

        // etc

    }

    private void handleInput(float deltaTime) {
        /// Buttons pressed

        // Mouse Events Later

    }

    // update logics method
    private void update(float deltaTime) {
        runTime += deltaTime;
        handleInput(deltaTime);
    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        // buffer screen
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update
        update(deltaTime);

        // render things
        sb.begin();
        sb.draw(mapTexture.drawTexture(), 0, 0, GAME_WIDTH, GAME_HEIGHT);

        // Map First Always kurwa!!!!!!!!!!!!!
        // Do not place any drawings up!!

        // Ships
        for (int i = 0; i < sum; i++)
            FirstBoardShipsSprites[i].drawSprite(sb);

        // Turrets

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
