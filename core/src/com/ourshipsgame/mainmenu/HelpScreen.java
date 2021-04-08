package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.Main;
import com.ourshipsgame.handlers.Constant;
import com.ourshipsgame.hud.GameTextButton;

public class HelpScreen implements Screen, Constant {
    private Main game;
    private Table layoutTable;
    private GameTextButton backButton;
    private Stage stage;
    private SpriteBatch batch;

    public HelpScreen(Main game) {
        this.game = game;
    }

    private void update(float deltaTime) {
        game.menuElements.moveMenu(deltaTime);
        stage.act();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        backButton = new GameTextButton("Back", 0, 0, game.menuElements.skin, 6, game);

        // Creating layout table
        layoutTable = new Table();
        layoutTable.setFillParent(true);
        layoutTable.top();

        // Adding elements to Table
        layoutTable.add(backButton);

        stage.addActor(layoutTable);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(game.menuElements.menuTexture.getTexture(), game.menuElements.menuTexture.x,
                game.menuElements.menuTexture.y);

        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
