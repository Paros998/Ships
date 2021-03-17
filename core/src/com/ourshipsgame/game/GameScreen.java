package com.ourshipsgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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
    private BitmapFont font;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
    }

    private void createMap() {
        map = new TmxMapLoader().load("core/assets/map/mp1.tmx");
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(false, gameWidth_f, gameHeight_f);

        // Map layers
        layers = new int[2];
        layers[0] = 0;
        layers[1] = 1;
    }

    private void drawMap() {
        switch (gameStage) {
        case 2:
            renderer.render();
            break;
        case 3:
            renderer.render(layers);
            break;
        }
    }

    private void drawShipsEnTurrets() {
        switch (gameStage) {
        case 2:
            for (int i = 0; i < sum; i++) {
                FirstBoardShipsSprites[i].drawSprite(sb, true, sr);
                FirstBoardShipsSprites[i].drawTurrets(sb);
            }
            break;
        case 3:
            for (int i = 0; i < sum; i++) {
                FirstBoardShipsSprites[i].drawSprite(sb);
                FirstBoardShipsSprites[i].drawTurrets(sb);
            }
            break;
        }
    }

    private void createFonts() {
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("core/assets/fonts/Raleway-ExtraLightItalic.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 43;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BROWN;
        parameter.color = Color.GOLD;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    // method to create elements
    private void createGraphics() {
        // Map ,textures,cameras
        createMap();
        // Fonts and its parameters
        createFonts();

        // changing game stage from loading to Placing ships
        if (preparation(true))
            gameStage = 2;

    }

    private void handleInput(float deltaTime) {
        /// Buttons pressed

    }

    // update logics of game
    private void update(float deltaTime) {
        runTime += deltaTime;
        // handleInput(deltaTime);
        for (int i = 0; i < sum; i++) {
            FirstBoardShipsSprites[i].updateTexture();
        }
        switch (gameStage) {
        case 2:
            // if(placement)
            // gameStage = 3;
            break;
        case 3:
            break;
        }
    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        // buffer screen
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Map update and tilemap render
        camera.update();
        renderer.setView(camera);
        drawMap();
        // update
        update(deltaTime);
        // render things below
        sb.begin();
        sr.setAutoShapeType(true);
        sr.begin();
        // Map First Always kurwa!!!!!!!!!!!!!
        // Do not place any drawings up!!

        // Ships // Turrets
        drawShipsEnTurrets();

        // Texts
        if (gameStage == 2)
            drawStage2Text(font, sb);

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
        // if (gameStage == 2)
        // rotateTurretsWithMouse(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
