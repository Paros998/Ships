package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {

    private final String id = getClass().getName();

    // vars
    private Game game;
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private Texture texture;
    private Sprite sprite;

    // constructor
    public GameScreen(Game game) {
        this.game = game;
        Gdx.app.log(id, "This class is loaded!");
    }

    // method to create elements
    private void createGraphics() {
        int width = 8;
        int height = 8;

        // pixmap
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        pixmap.drawRectangle(0, 0, width, height);

        // textures
        texture = new Texture(pixmap);

        // sprites
        sprite = new Sprite(texture);
        sprite.setPosition(1f, 1f);
        sprite.setSize(.5f, .5f);

    }

    // game loop method
    @Override
    public void render(float deltaTime) {
        sb.setProjectionMatrix(cam.combined);
        // update
        sprite.rotate(10f);
        // render things
        sb.begin();
        sprite.draw(sb);
        sb.end();
        super.render(deltaTime);
    }

    @Override
    public void show() {
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 5f, 5f);

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
