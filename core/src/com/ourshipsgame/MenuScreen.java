package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen extends ScreenAdapter implements Constant {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    private Stage stage;
    // other vars
    private GameObject menuTexture;
    private Skin skin;
    private GameButton playButton, helpButon, scoreButton, optionsButton, quitButton;

    private int direction = 0;

    public MenuScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
    }

    private void moveMenu(float deltaTime) {
        if ((menuTexture.x <= 0) && (direction == 0)) {
            if (menuTexture.x <= -119)
                direction = 1;
            menuTexture.moveTexture(-20 * deltaTime);
        }

        if ((menuTexture.x >= -120) && (direction == 1)) {
            if (menuTexture.x >= -1)
                direction = 0;
            menuTexture.moveTexture(20 * deltaTime);
        }

    }

    private void createGraphics() {
        menuTexture = new GameObject("core/assets/backgroundtextures/paperTextOld.png", 0, 0, true);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Buttons
        skin = new Skin(Gdx.files.internal("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json"));
        playButton = new GameButton("Play", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100, skin);
        helpButon = new GameButton("Help", GAME_WIDTH / 2, GAME_HEIGHT / 2, skin);
        scoreButton = new GameButton("Score", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100, skin);
        optionsButton = new GameButton("Options", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 200, skin);
        quitButton = new GameButton("Exit", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300, skin);

        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                processEnter(4);
            }
        });

        helpButon.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                processEnter(3);
            }
        });

        scoreButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                processEnter(2);
            }
        });

        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (event.isTouchFocusCancel())
                    processEnter(1);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        stage.addActor(playButton);
        stage.addActor(helpButon);
        stage.addActor(scoreButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
    }

    private void handleButtons() {
        if (quitButton.isPressed())
            Gdx.app.exit();
    }

    private void processEnter(int option) {
        // Changing screen based on screenInt and Enter pressed
        switch (option) {
            case 4: {
                super.dispose();
                game.setScreen(new GameScreen(game));
                break;
            }
            case 3: {
                // super.dispose();
                // game.setScreen(new OptionScreen(game));
                break;
            }
            case 2: {
                // super.dispose();
                // game.setScreen(new ScoreScreen(game));
                break;
            }
            case 1: {
                Gdx.app.exit();
                break;
            }

        }
    }

    // update logics method
    private void update(float deltaTime) {
        moveMenu(deltaTime);
        handleButtons();
    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        // buffer screen
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        // update
        update(deltaTime);
        // render things
        sb.begin();
        sb.draw(menuTexture.texture, menuTexture.x, menuTexture.y);
        sb.end();
        stage.draw();
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
