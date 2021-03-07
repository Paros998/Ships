package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen implements Screen, Constant {

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
        playButton = new GameButton("Play", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100, skin, 1, game);
        helpButon = new GameButton("Help", GAME_WIDTH / 2, GAME_HEIGHT / 2, skin, 2, game);
        scoreButton = new GameButton("Score", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100, skin, 3, game);
        optionsButton = new GameButton("Options", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 200, skin, 4, game);
        quitButton = new GameButton("Exit", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300, skin, 5, game);

        stage.addActor(playButton);
        stage.addActor(helpButon);
        stage.addActor(scoreButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
    }

    // update logics method
    private void update(float deltaTime) {
        moveMenu(deltaTime);
        stage.act();
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
        sb.begin();
        sb.draw(menuTexture.texture, menuTexture.x, menuTexture.y);
        sb.end();
        stage.draw();
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        sb.dispose();
        stage.dispose();
        skin.dispose();
        System.out.println("Elements from main menu disposed.");
    }
}
