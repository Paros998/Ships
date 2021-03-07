package com.ourshipsgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.handlers.Constant;

public class OptionScreen implements Screen, Constant {

    private MenuGlobalElements menuElements;
    private Stage stage;
    private SpriteBatch batch;
    private GameButton backButton;

    public OptionScreen(MenuGlobalElements menuElements) {
        this.menuElements = menuElements;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        // Buttons
        backButton = new GameButton("Back to Main Menu", GAME_WIDTH / 2 - 600, GAME_HEIGHT / 2 - 300, 
            menuElements.skin, 6, menuElements);

        stage.addActor(backButton);
    }

    private void update(float deltaTime) {
        menuElements.moveMenu(deltaTime);
        stage.act();
    }

    @Override
    public void render(float delta) {
        update(delta);
        
        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(menuElements.menuTexture.texture, menuElements.menuTexture.x, menuElements.menuTexture.y);
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
    }

}
