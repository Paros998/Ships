package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.GameButton;
import com.ourshipsgame.GameObject;
import com.ourshipsgame.Main;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen implements Screen, Constant {

    // vars mandatory
    private Main game;
    public Stage stage;
    public SpriteBatch batch;
    private GameObject shipLogo;

    private GameButton playButton, helpButon, scoreButton, optionsButton, quitButton;

    public MenuScreen(Main game) {
        this.game = game;
    }

    private void createGraphics() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        shipLogo = new GameObject("core/assets/shipstextures/ship-logo.png", 0, 0, true);
        shipLogo.getSprite().setSize(shipLogo.width / 2, shipLogo.height / 2);
        shipLogo.getSprite().setX(GAME_WIDTH / 2  - shipLogo.getSprite().getWidth() / 2);
        shipLogo.getSprite().setY(GAME_HEIGHT / 2  - shipLogo.getSprite().getHeight() / 2 + 320);

        // Buttons
        playButton = new GameButton("Play", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100, game.menuElements.skin, 1, game);

        helpButon = new GameButton("Help", GAME_WIDTH / 2, GAME_HEIGHT / 2, game.menuElements.skin, 2, game);

        scoreButton = new GameButton("Score", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100, game.menuElements.skin, 3, game);

        optionsButton = new GameButton("Options", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 200, game.menuElements.skin, 4,
                game);

        quitButton = new GameButton("Exit", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300, game.menuElements.skin, 5, game);

        stage.addActor(playButton);
        stage.addActor(helpButon);
        stage.addActor(scoreButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
    }

    // update logics method
    private void update(float deltaTime) {
        stage.act();
        game.menuElements.moveMenu(deltaTime);
    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        // update
        update(deltaTime);

        // buffer screen
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render things
        batch.begin();

        batch.draw(game.menuElements.menuTexture.getTexture(), game.menuElements.menuTexture.x,
                game.menuElements.menuTexture.y);

        shipLogo.drawSprite(batch);

        game.menuElements.font.draw (
            batch, game.menuElements.layout, 
            GAME_WIDTH / 2 - game.menuElements.layout.width / 2, GAME_HEIGHT - 50
        );

        batch.end();
        stage.draw();
    }

    @Override
    public void show() {
        createGraphics();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        System.out.println("Elements from Main Menu disposed.");
    }
}
