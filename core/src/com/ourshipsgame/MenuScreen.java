package com.ourshipsgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.handlers.Constant;

public class MenuScreen implements Screen, Constant {

    // vars mandatory
    private Main game;
    public Stage stage;
    public SpriteBatch batch;
    
    private GameButton playButton, helpButon, scoreButton, optionsButton, quitButton;

    public MenuScreen(Main game) {
        this.game = game;
    }

    private void createGraphics() {
        stage = new Stage(new ScreenViewport());
        batch =  new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        // Buttons
        playButton = new GameButton("Play", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100,
         game.menuElements.skin, 1, game);

        helpButon = new GameButton("Help", GAME_WIDTH / 2, GAME_HEIGHT / 2,
         game.menuElements.skin, 2, game);

        scoreButton = new GameButton("Score", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100,
         game.menuElements.skin, 3, game);

        optionsButton = new GameButton("Options", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 200,
         game.menuElements.skin, 4, game);

        quitButton = new GameButton("Exit", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300,
         game.menuElements.skin, 5, game);


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

        batch.draw(game.menuElements.menuTexture.texture, 
            game.menuElements.menuTexture.x, game.menuElements.menuTexture.y);

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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        System.out.println("Elements from Main Menu disposed.");
    }
}
