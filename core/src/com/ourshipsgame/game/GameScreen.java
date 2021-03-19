package com.ourshipsgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.ourshipsgame.hud.Hud;

public class GameScreen extends GameEngine implements InputProcessor {

    private final String id = getClass().getName();

    private AssetManager manager;
    private Game game;
    private InputMultiplexer inputMultiplexer;
    private Hud hud;
    private SpriteBatch sb;
    private ShapeRenderer sr;
    private float runTime;
    private float progress;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private int gameStage = 1;
    private TiledMap map;
    private Texture loadingTexture;
    private boolean createdTextures = false;
    private boolean shootOrder = false;
    private int[] layers;
    private float rotateTime;
    private float shootTime;
    // other vars
    private BitmapFont font;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
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
                FirstBoardShipsSprites[i].updateTexture();
                FirstBoardShipsSprites[i].drawSprite(sb, true, sr);
                FirstBoardShipsSprites[i].drawTurrets(sb);
            }
            break;
        case 3:
            for (int i = 0; i < sum; i++) {
                FirstBoardShipsSprites[i].updateTexture();
                FirstBoardShipsSprites[i].drawSprite(sb);
                FirstBoardShipsSprites[i].drawTurrets(sb);
            }
            break;
        }
    }

    private void createMap() {
        map = (TiledMap) manager.get("core/assets/map/mp1.tmx");
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(false, gameWidth_f, gameHeight_f);

        // Map layers
        layers = new int[2];
        layers[0] = 0;
        layers[1] = 1;
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

    private void loadAssets() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("core/assets/map/mp1.tmx", TiledMap.class);
        loadGameEngine(manager);

    }

    // method to create elements
    private void createGraphics() {
        // Map ,textures,cameras
        createMap();
        // changing game stage from loading to Placing ships
        if (preparation(true, manager)) {
            gameStage = 2;

            hud = new Hud();
            createdTextures = true;
            rotateSound.loop(0.5f);
            rotateSound.pause();
        }
    }

    private void handleInput(float deltaTime) {
        /// Buttons pressed
    }

    // update logics of game
    private void update(float deltaTime) {
        runTime += deltaTime;

        rotateTime += deltaTime;
        if (rotateTime >= 0.2f) {
            rotateTime -= 0.2f;
            rotateSound.pause();
        }
        handleInput(deltaTime);

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

        if (manager.update()) {
            // When loading screen disappers
            if (createdTextures == false) {
                loadingTexture.dispose();
                createGraphics();
                inputMultiplexer = new InputMultiplexer();
                inputMultiplexer.addProcessor(this);
                inputMultiplexer.addProcessor(hud.getStage());
                Gdx.input.setInputProcessor(inputMultiplexer);
            }
            // Map update and tilemap render
            if (hud.isPasued())
                Gdx.input.setInputProcessor(hud.getStage());
            else
                Gdx.input.setInputProcessor(inputMultiplexer);

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
            switch (gameStage) {
            case 2:
                drawStage2Text(font, sb);
                break;
            case 3:
                if (shootOrder) {
                    shootTime += deltaTime;
                    if (shootTime <= 1f) {
                        for (int i = 0; i < sum; i++) {
                            shootEffect[i].updateAnimation(FirstBoardShipsSprites[i]);
                            shootEffect[i].drawAnimation(sb);
                        }
                    } else {
                        shootOrder = false;
                        shootTime = 0f;
                    }
                }
                break;
            }

            hud.render(sb);

            sb.end();
            sr.end();
            hud.update();
        } else {
            // While loading the game assets
            progress = manager.getProgress();
            sb.begin();
            sb.draw(loadingTexture, 0, 0);
            String load = "Loading " + progress * 100 + "%";
            font.draw(sb, load, (gameWidth_f / 2f) - 150, (gameHeight_f / 2f) + 43);
            sb.end();
        }
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        manager = new AssetManager();
        loadingTexture = new Texture("core/assets/backgroundtextures/paperTextOld.png");
        createFonts();
        loadAssets();

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
        if (gameStage == 3) {
            shootOrder = shoot();
        }
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
        if (gameStage == 3) {
            rotateSound.resume();
            rotateTurretsWithMouse(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
