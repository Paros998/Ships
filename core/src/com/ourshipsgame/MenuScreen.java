package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen implements Screen, Constant {

    // vars mandatory
    private MenuGlobalElements menuElements;
    private SpriteBatch sb;
    private Stage stage;
    private GameButton playButton, helpButon, scoreButton, optionsButton, quitButton;

    public MenuScreen(Game game) {
        menuElements = new MenuGlobalElements(game);
    }

    private void createGraphics() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Buttons
        playButton = new GameButton("Play", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100, menuElements.skin, 1, menuElements);
        helpButon = new GameButton("Help", GAME_WIDTH / 2, GAME_HEIGHT / 2, menuElements.skin, 2, menuElements);
        scoreButton = new GameButton("Score", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100, menuElements.skin, 3, menuElements);
        optionsButton = new GameButton("Options", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 200, menuElements.skin, 4, menuElements);
        quitButton = new GameButton("Exit", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300, menuElements.skin, 5, menuElements);

        stage.addActor(playButton);
        stage.addActor(helpButon);
        stage.addActor(scoreButton);
        stage.addActor(optionsButton);
        stage.addActor(quitButton);
    }

    // update logics method
    private void update(float deltaTime) {
        menuElements.moveMenu(deltaTime);
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
        sb.draw(menuElements.menuTexture.texture, menuElements.menuTexture.x, menuElements.menuTexture.y);
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
        menuElements.dispose();
        System.out.println("Elements from main menu disposed.");
    }
}
