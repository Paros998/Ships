package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends GameEngine implements InputProcessor {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private float runTime;
    private GameObject mapTexture;

    // other vars
    private boolean prepDone = false;
    protected int activeSpriteDrag = 1;
    protected float xSprite;
    protected float ySprite;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");

    }

    private boolean preparation(boolean computerEnemy) {
        boolean done = false;
        for (int i = 0; i < BOX_X_AXIS_NUMBER; i++)
            for (int j = 0; j < BOX_Y_AXIS_NUMBER; j++) {
                FirstBoardShipsPos[i][j] = SecondBoardShipsPos[i][j] = FirstPlayerShotsDone[i][j] = SecondPlayerShotsDone[i][j] = 0;
            }

        for (int i = 0; i < sum; i++) {
            if (i <= 2) {
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/threeshipModel.png",
                        FirstBoardStart.x + (i * BOX_WIDTH_F), GAME_HEIGHT_F - FirstBoardStart.y, true, 3);
            }
            if (i > 2 && i <= 6) {
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/threeshipModel.png",
                        FirstBoardStart.x + (i * BOX_WIDTH_F), GAME_HEIGHT_F - FirstBoardStart.y, true, 3);
            } else
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/threeshipModel.png",
                        FirstBoardStart.x + (i * BOX_WIDTH_F), GAME_HEIGHT_F - FirstBoardStart.y, true, 3);
        }
        done = true;
        return done;
    }

    // method to create elements
    private void createGraphics() {
        // textures
        mapTexture = new GameObject("core/assets/backgroundtextures/map.png", 0, 0, false, 0);
        // sprites
        prepDone = preparation(true);

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
        for (int i = 0; i < sum; i++) {
            if (FirstBoardShipsSprites[i].spriteContains(new Vector2(screenX, GAME_HEIGHT_F - screenY))) {
                activeSpriteDrag = i;
                System.out.println(activeSpriteDrag);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        activeSpriteDrag = 99;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (activeSpriteDrag <= sum - 1 && activeSpriteDrag >= 0) {
            xSprite = FirstBoardShipsSprites[activeSpriteDrag].width / 2;
            ySprite = FirstBoardShipsSprites[activeSpriteDrag].height / 2;
            FirstBoardShipsSprites[activeSpriteDrag]
                    .setSpritePos(new Vector2(screenX - xSprite, GAME_HEIGHT_F - screenY - ySprite));

        }
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
