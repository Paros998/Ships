package com.ourshipsgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.handlers.Constant;

public class OptionScreen implements Screen, Constant {

    private Main game;
    private GameButton backButton;
    public Stage stage;
    public SpriteBatch batch;

    public OptionScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch =  new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        // Buttons
        backButton = new GameButton("Back to Main Menu", GAME_WIDTH / 2 - 600, GAME_HEIGHT / 2 - 300, 
            game.menuElements.skin, 6, game);

        stage.addActor(backButton);
    }

    private void update(float deltaTime) {
        game.menuElements.moveMenu(deltaTime);
        stage.act();
    }

    @Override
    public void render(float delta) {
        update(delta);
        
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(game.menuElements.menuTexture.texture, 
        game.menuElements.menuTexture.x, game.menuElements.menuTexture.y);

        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
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
        System.out.println("Elements from Option Menu disposed.");
    }

}
