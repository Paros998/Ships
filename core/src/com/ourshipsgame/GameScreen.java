package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen extends GameEngine implements InputProcessor {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private float runTime;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private int gameStage = 1;
    private TiledMap map;
    private int[] layers;
    // other vars

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");

    }

    // method to create elements
    private void createGraphics() {
        // textures
        map = new TmxMapLoader().load("core/assets/map/mp1.tmx");
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(false, gameWidth_f, gameHeight_f);
        // sprites
        if (preparation(true)) {
            // changing to next phase
            gameStage = 2;
        }
        // etc
        layers = new int[2];
        layers[0] = 0;
        layers[1] = 1;
    }

    private void handleInput(float deltaTime) {
        /// Buttons pressed

        // Mouse Events Later

    }

    // update logics method
    private void update(float deltaTime) {
        runTime += deltaTime;
        handleInput(deltaTime);
        for (int i = 0; i < sum; i++) {
            FirstBoardShipsSprites[i].updateTexture();
        }

    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        // buffer screen
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //
        camera.update();
        renderer.setView(camera);
        renderer.render();
        // update
        update(deltaTime);
        // render things
        sb.begin();
        sr.setAutoShapeType(true);
        sr.begin();

        // sb.draw(mapTexture.drawTexture(), 0, 0, gameWidth, gameHeight);

        // Map First Always kurwa!!!!!!!!!!!!!
        // Do not place any drawings up!!

        // Ships
        for (int i = 0; i < sum; i++) {
            FirstBoardShipsSprites[i].drawSprite(sb, true, sr);
        }

        // Turrets
        for (int i = 0; i < sum; i++) {
            FirstBoardShipsSprites[i].drawTurrets(sb);
        }

        sb.end();
        sr.end();
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        createGraphics();
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (gameStage == 2) {
            if (Gdx.input.isKeyPressed(Keys.R))
                if ((activeSpriteDrag <= sum - 1) && (activeSpriteDrag >= 0))
                    rotateActualShip();
        }
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
        if (gameStage == 2)
            // rotateTurretsWithMouse(screenX, screenY);
            return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
