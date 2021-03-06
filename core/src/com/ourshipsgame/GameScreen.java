package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen extends GameEngine implements InputProcessor {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private float runTime;
    private GameObject mapTexture;
    private int gameStage = 1;
    // other vars
    protected int activeSpriteDrag = 1;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");

    }

    // method to create elements
    private void createGraphics() {
        // textures
        mapTexture = new GameObject("core/assets/backgroundtextures/map.png", 0, 0);
        // sprites
        if (preparation(true)) {
            // changing to next phase
            gameStage = 2;
        }
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
        sr.setAutoShapeType(true);
        sr.begin();

        sb.draw(mapTexture.drawTexture(), 0, 0, gameWidth, gameHeight);

        // Map First Always kurwa!!!!!!!!!!!!!
        // Do not place any drawings up!!

        // Ships
        for (int i = 0; i < sum; i++) {
            FirstBoardShipsSprites[i].drawSprite(sb, true, sr);
        }

        // Turrets

        sb.end();
        sr.end();
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        createGraphics();
        Gdx.input.setInputProcessor(this);
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

    @Override
    public void resize(int width, int height) {
        gameHeight = height;
        gameWidth = width;
        gameHeight_f = (float) gameHeight;
        gameWidth_f = (float) gameWidth;
        super.resize(width, height);
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameStage == 2)
            touchDownSprite(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (gameStage == 2)
            touchUpSprite();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (gameStage == 2)
            dragSprite(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
