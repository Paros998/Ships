package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen extends ScreenAdapter implements Constant {

    private final String id = getClass().getName();

    // vars mandatory
    private Game game;
    private SpriteBatch sb;
    // other vars
    private GameObject menuTexture, playButton, optionButton, quitButton, scoreButton;

    private int direction = 0;
    private int screenInt = 4;

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
        menuTexture = new GameObject("core/assets/backgroundtextures/paperTextOld.png", 0, 0, false);

    }

    private void processEnter(float deltaTime) {
        // Changing screen based on screenInt and Enter pressed
        switch (screenInt) {
            case 4: {
                super.dispose();
                game.setScreen(new GameScreen(game));
                break;
            }
            case 3: {
                super.dispose();
                // game.setScreen(new OptionScreen(game));
                break;
            }
            case 2: {
                super.dispose();
                // game.setScreen(new ScoreScreen(game));
                break;
            }
            case 1: {
                Gdx.app.exit();
                break;
            }

        }
    }

    private void handleInput(float deltaTime) {
        /// Keys pressed
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            processEnter(deltaTime);
            return;
        }

        if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP)) {
            if (screenInt == 4)
                return;
            else
                screenInt += 1;
        } else if (Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            if (screenInt == 1)
                return;
            else
                screenInt -= 1;
        } else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            screenInt = 1;
            processEnter(deltaTime);
        }

    }

    // update logics method
    private void update(float deltaTime) {
        moveMenu(deltaTime);
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

        sb.draw(menuTexture.texture, menuTexture.x, menuTexture.y);

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
