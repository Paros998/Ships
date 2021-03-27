package com.ourshipsgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
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
import com.ourshipsgame.Main;
import com.ourshipsgame.hud.Hud;

public class GameScreen extends GameEngine implements InputProcessor {

    private final String id = getClass().getName();
    private AssetManager manager;
    private Main game;
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
    private boolean shootSound = false;

    private int[] layers;
    private float rotateTime;
    private float shootTime;
    // other vars
    private BitmapFont font;

    // constructor
    public GameScreen(Main game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
    }

    // Draw methods

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
                FirstBoardShipsSprites[i].drawSprite(sb, true, false, sr);
                FirstBoardShipsSprites[i].drawTurrets(sb);
                SecondBoardShipsSprites[i].updateTexture();
                SecondBoardShipsSprites[i].drawSprite(sb, true, false, sr);
                SecondBoardShipsSprites[i].drawTurrets(sb);
            }
            break;
        case 3:
            for (int i = 0; i < sum; i++) {
                FirstBoardShipsSprites[i].updateTexture();
                FirstBoardShipsSprites[i].drawSprite(sb);
                FirstBoardShipsSprites[i].drawTurrets(sb);
                SecondBoardShipsSprites[i].updateTexture();
                SecondBoardShipsSprites[i].drawSprite(sb);
                SecondBoardShipsSprites[i].drawTurrets(sb);
            }
            break;
        }
    }

    private void drawShootingEffect(float deltaTime) {
        shootTime += deltaTime;
        if (shootTime <= 1f) {
            for (int i = 0; i < sum; i++) {
                if (FirstBoardShipsSprites[i].shipDestroyed)
                    continue;
                shootEffect[i].updateAnimation(FirstBoardShipsSprites[i]);
                shootEffect[i].drawAnimation(sb);
            }
        } else {
            rotateEnabled = true;
            shootOrder = false;
            shootTime = 0f;
        }
    }

    private void drawLoadingScreen() {
        progress = manager.getProgress();
        sb.begin();
        sb.draw(loadingTexture, 0, 0);
        String load = "Loading " + progress * 100 + "%";
        font.draw(sb, load, (gameWidth_f / 2f) - 175, (gameHeight_f / 2f) + 43);
        sb.end();
    }

    // Create methods

    private void createMap() {
        map = (TiledMap) manager.get("core/assets/map/mp1.tmx");
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(false, gameWidth_f, gameHeight_f);

        // Map layers
        layers = new int[3];
        layers[0] = 0;
        layers[1] = 1;
        layers[2] = 2;
    }

    private void createFonts() {
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("core/assets/fonts/Raleway-ExtraLightItalic.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 43;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.WHITE;
        parameter.color = Color.RED;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    // method to create elements
    private void createGraphics() {
        // Map ,textures,cameras
        createMap();
        // changing game stage from loading to Placing ships
        if (preparation(true, manager)) {
            gameStage = 2;
            hud = new Hud(manager);
            createdTextures = true;
        }
        hud.gameSettings = game.menuElements.gameSettings;
        // Deleting GlobalMenuElements object
        game.menuElements = null;
    }

    // loading method
    private void loadAssets() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("core/assets/map/mp1.tmx", TiledMap.class);
        loadGameEngine(manager);
        loadHudAssets(manager);
    }

    // Sound effects methods
    private void startRotateSound() {
        rotateSound.loop(0.5f);
        rotateSound.pause();
    }

    private void playShootSound() {
        if (shootTime >= 0f && shootTime < 0.02) {
            ShootSounds[0].play(0.5f);
            ShootSounds[1].play(0.5f);
            ShootSounds[2].play(0.5f);
        } else if (shootTime >= 0.02 && shootTime < 0.03) {
            ShootSounds[3].play(0.6f);
            ShootSounds[4].play(0.6f);
            ShootSounds[5].play(0.6f);
            ShootSounds[6].play(0.7f);
            ShootSounds[7].play(0.7f);
        } else if (shootTime >= 0.03 && shootTime < 0.04) {
            ShootSounds[0].play(0.8f);
            ShootSounds[1].play(0.8f);
            ShootSounds[2].play(0.8f);

            ShootSounds[8].play(0.8f);
            ShootSounds[9].play(0.8f);
            ShootSounds[10].play(0.9f);
            ShootSounds[11].play(0.9f);
        } else if (shootTime >= 1.0f)
            shootSound = false;
    }

    // Input and update methods
    private void handleInput(float deltaTime) {
        /// Buttons pressed
    }

    // Invoked after ready button is pressed in stage 2
    public void readyButtonCheck() {
        if (checkAllShips()) {
            firstBoard.placeShipOnBoard(sum);
            // secondBoard.placeShipOnBoard(sum);
            gameStage = 3;
            startRotateSound();
        }
    }

    // update logics of game
    private void update(float deltaTime) {
        runTime += deltaTime;

        rotateTime += deltaTime;
        if (rotateTime >= 0.3f) {
            rotateTime -= 0.32f;
            rotateSound.pause();
        }
        handleInput(deltaTime);

        switch (gameStage) {
        case 2:
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
                // tmp

            }
            if (hud.isPasued())
                Gdx.input.setInputProcessor(hud.getStage());
            else
                Gdx.input.setInputProcessor(inputMultiplexer);

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
            // Do not place any drawings up!!
            // font.draw(sb, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, GAME_HEIGHT_F
            // );
            // Ships // Turrets
            drawShipsEnTurrets();

            // Texts
            switch (gameStage) {
            case 2:
                drawStage2Text(font, sb);
                break;
            case 3:
                if (shootOrder) {
                    rotateEnabled = false;
                    if (shootSound) {
                        playShootSound();
                    }
                    drawShootingEffect(deltaTime);
                    if (hitted) {
                        // drawHit(deltaTime);
                    }
                    // else
                    // drawMiss(deltaTime);
                }
                break;
            }

            hud.render(sb);

            sb.end();
            sr.end();
            hud.update();
        } else {
            // While loading the game assets
            drawLoadingScreen();
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
            if (Gdx.input.isKeyPressed(Keys.E))
                readyButtonCheck();
            if (Gdx.input.isKeyPressed(Keys.Q))
                generateAndPlaceShipsOnBoard(1, true);
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
        if (button == Buttons.LEFT) {
            if (gameStage == 2)
                touchDownSprite(screenX, screenY);
            if (gameStage == 3) {
                shootOrder = shoot(screenX, screenY, 0);
                shootSound = true;
            }
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
            checkEnemyBoard(screenX, screenY);
            if (rotateEnabled) {
                rotateSound.resume();
                rotateTurretsWithMouse(screenX, screenY);
            }

        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
