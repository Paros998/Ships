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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
    private boolean hitMissSound = false;
    private long sid;
    private int[] layers;
    private float rotateTime;
    private float shootTime;
    private float destroyTime;
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
        case 4:
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
                SecondBoardShipsSprites[i].drawSprite(sb, true, false, sr, true);
                SecondBoardShipsSprites[i].drawTurrets(sb, true);
            }
            break;
        case 3:
            for (int i = 0; i < sum; i++) {
                FirstBoardShipsSprites[i].updateTexture();
                FirstBoardShipsSprites[i].drawSprite(sb);
                FirstBoardShipsSprites[i].drawTurrets(sb);
                SecondBoardShipsSprites[i].updateTexture();
                SecondBoardShipsSprites[i].drawSprite(sb, true);
                SecondBoardShipsSprites[i].drawTurrets(sb, true);
            }
            break;
        case 4:
            for (int i = 0; i < sum; i++) {
                FirstBoardShipsSprites[i].drawSprite(sb);
                FirstBoardShipsSprites[i].drawTurrets(sb);
                SecondBoardShipsSprites[i].drawSprite(sb, true);
                SecondBoardShipsSprites[i].drawTurrets(sb, true);
            }
            break;
        }
    }

    private void drawHit(float deltaTime) {
        if (shootTime <= 1f) {
            hitEffect.updateAnimation();
            hitEffect.drawEffect(sb);
        } else {
            hitted = false;
        }
        hitMissSound = false;
    }

    private void drawMiss(float deltaTime) {
        if (shootTime <= 1f) {
            missEffect.updateAnimation();
            missEffect.drawEffect(sb);
        } else {
            missed = false;
        }
        hitMissSound = false;
    }

    private void drawDestroyment(float deltaTime) {
        destroyTime += deltaTime;
        if (destroyTime <= 1f) {
            destroymentEffect.updateAnimation(true);
            destroymentEffect.drawEffect(sb, true);
        } else {
            destroyed = false;
            destroymentSound = false;
            destroyTime = 0f;
        }
    }

    private void drawShootingEffect(float deltaTime) {
        shootTime += deltaTime;
        if (shootTime <= 1f) {
            if (PlayerTurn == 1) {
                for (int i = 0; i < sum; i++) {
                    if (FirstBoardShipsSprites[i].shipDestroyed)
                        continue;
                    shootEffect[i].updateAnimation(FirstBoardShipsSprites[i]);
                    shootEffect[i].drawAnimation(sb);
                }
            }
        } else {
            if (missed)
                switchTurn();
            rotateEnabled = true;
            shootTime = 0f;
            shootOrder = false;
        }
    }

    private void drawMarks(SpriteBatch batch) {
        float xpos, ypos;
        if (PlayerTurn == 1) {
            if (!shootOrder && !destroyed)
                for (int i = 0; i < 10; i++)
                    for (int j = 0; j < 10; j++) {
                        if (FirstPlayerShotsDone[i][j] == -1) {
                            xpos = i * 64f + SecondBoardStart.x;
                            ypos = j * 64f + SecondBoardStart.y;
                            batch.draw(shootMarks[0], xpos, ypos);
                        } else if (FirstPlayerShotsDone[i][j] == 1) {
                            xpos = i * 64f + SecondBoardStart.x;
                            ypos = j * 64f + SecondBoardStart.y;
                            batch.draw(shootMarks[1], xpos, ypos);
                        }
                    }
        } else
            for (int i = 0; i < 10; i++)
                for (int j = 0; j < 10; j++) {
                    if (FirstPlayerShotsDone[i][j] == -1) {
                        xpos = i * 64f + SecondBoardStart.x;
                        ypos = j * 64f + SecondBoardStart.y;
                        batch.draw(shootMarks[0], xpos, ypos);
                    } else if (FirstPlayerShotsDone[i][j] == 1) {
                        xpos = i * 64f + SecondBoardStart.x;
                        ypos = j * 64f + SecondBoardStart.y;
                        batch.draw(shootMarks[1], xpos, ypos);
                    }
                }
    }

    private void drawScores(SpriteBatch batch) {
        PlayerOne.drawInfo(hudFont, batch, gameWidth_f, gameHeight_f, FirstBoardThreeShipsLeft, FirstBoardTwoShipsLeft,
                FirstBoardOneShipsLeft, shipIcons);
        PlayerTwo.drawInfo(hudFont, batch, gameWidth_f, gameHeight_f, SecondBoardThreeShipsLeft,
                SecondBoardTwoShipsLeft, SecondBoardOneShipsLeft, shipIcons);
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
        layers = new int[4];
        layers[0] = 0;
        layers[1] = 1;
        layers[2] = 2;
        layers[3] = 3;
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
        parameter.size = 16;
        parameter.borderWidth = 0;
        hudFont = generator.generateFont(parameter);
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
        hud.getPlayButton().addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                readyButtonCheck();
                if(gameStage == 3)
                    hud.getStage().getActors().pop();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        hud.getRepeatButton().addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                generateAndPlaceShipsOnBoard(1, true);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
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
        sid = rotateSound.loop(hud.gameSettings.soundVolume);
        rotateSound.pause();
    }

    private void playShootSound() {
        if (shootTime <= 1f) {
            if (PlayerTurn == 1) {
                int j = 0;
                for (int i = 0; i < sum; i++)
                    if (!FirstBoardShipsSprites[i].shipDestroyed)
                        j++;
                for (int i = 0; i < j; i++)
                    ShootSounds[i].play(hud.gameSettings.soundVolume);
            } else {
                int j = 0;
                for (int i = 0; i < sum; i++)
                    if (!SecondBoardShipsSprites[i].shipDestroyed)
                        j++;
                for (int i = 0; i < j; i++)
                    ShootSounds[i].play(hud.gameSettings.soundVolume);
            }
        }
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

        if (PlayerTurn == 1)
            PlayerOne.updateTime(deltaTime);
        else
            PlayerTwo.updateTime(deltaTime);

        if (FirstBoardShipsDestroyed == sum) {
            gameStage = 4;
        } else if (SecondBoardShipsDestroyed == sum) {
            gameStage = 4;
        }

        rotateTime += deltaTime;
        if (rotateTime >= 0.3f) {
            rotateTime -= 0.32f;
            rotateSound.pause();
        }
        handleInput(deltaTime);
        rotateSound.setVolume(sid, hud.gameSettings.soundVolume);
        if (gameStage == 3) {

            // Update AI info
            if (shootOrder)
                return;
            else {
                if (PlayerTurn == 2) {
                    Gdx.graphics.setCursor(crosshairs[2]);
                    if (enemyComputerPlayerAi != null) {
                        shootingEnabled = true;
                        shootOrder = enemyComputerPlayerAi.attackEnemy(deltaTime);
                        if (shootOrder) {
                            shoot((int) enemyComputerPlayerAi.getX(), (int) enemyComputerPlayerAi.getY());
                            shootSound = true;
                            hitMissSound = true;
                            enemyComputerPlayerAi.update(missed, hitted, destroyed, SecondPlayerShotsDone,
                                    FirstBoardShipsSprites, FirstBoardStart, sum);
                            float x, y;
                            x = (enemyComputerPlayerAi.getX() * 64.0f) + FirstBoardStart.x;
                            y = (enemyComputerPlayerAi.getY() * 64.0f) + FirstBoardStart.y;
                            rotateTurretsWithMouse(x, y);
                            PlayerTwo.update(SecondPlayerShotsDone);
                            shootingEnabled = false;
                        }
                    }
                }
            }
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

            // Ships // Turrets
            drawShipsEnTurrets();

            // Texts
            switch (gameStage) {
            case 2:
                drawStage2Text(font, sb);
                break;
            case 3:
                drawScores(sb);
                drawMarks(sb);
                if (shootOrder) {
                    shootingEnabled = false;
                    rotateEnabled = false;
                    if (shootSound) {
                        playShootSound();
                    }
                    drawShootingEffect(deltaTime);
                    Gdx.graphics.setCursor(crosshairs[0]);
                    if (hitted == true && destroyed == false) {
                        if (hitMissSound)
                            hitEffect.playSound(hud.gameSettings.soundVolume);
                        drawHit(deltaTime);
                    }
                    if (missed) {
                        if (hitMissSound)
                            missEffect.playSound(hud.gameSettings.soundVolume);
                        drawMiss(deltaTime);
                    }

                }
                if (destroyed) {
                    missed = false;
                    hitMissSound = false;
                    shootSound = false;
                    if (destroymentSound)
                        destroymentSound = destroymentEffect.playSound(true, hud.gameSettings.soundVolume);
                    drawDestroyment(deltaTime);
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
            /*if (Gdx.input.isKeyPressed(Keys.E))
                readyButtonCheck();
            if (Gdx.input.isKeyPressed(Keys.Q))
                generateAndPlaceShipsOnBoard(1, true);*/
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
                if (PlayerTurn == 1) {
                    shootOrder = shoot(screenX, screenY);
                    PlayerOne.update(FirstPlayerShotsDone);
                    shootSound = true;
                    hitMissSound = true;
                }
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
            if (PlayerTurn == 1) {
                if (!shootOrder)
                    checkEnemyBoard(screenX, screenY);
                if (rotateEnabled) {
                    rotateSound.resume();
                    rotateTurretsWithMouse(screenX, screenY);
                }
            }

        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
