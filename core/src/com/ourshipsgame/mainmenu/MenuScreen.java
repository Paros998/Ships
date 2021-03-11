package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    private GameObject shootingShip, destroyedShip, fire;
    private GameObject[] fireBalls;

    private GameButton playButton, helpButon, scoreButton, optionsButton, quitButton;

    public MenuScreen(Main game) {
        this.game = game;
    }

    // update logics method
    private void update(float deltaTime) {
        stage.act();
        game.menuElements.moveMenu(deltaTime);
        fire.updateAnimation();
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

        batch.draw(fire.getSprite(), fire.x, fire.y);
        shootingShip.drawSprite(batch);
        destroyedShip.drawSprite(batch);

        for(GameObject fireBall : fireBalls)
            fireBall.drawSprite(batch);

        game.menuElements.font.draw (
            batch, game.menuElements.layout, 
            GAME_WIDTH / 2 - game.menuElements.layout.width / 2, GAME_HEIGHT - 50
        );

        batch.end();
        stage.draw();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        // Background Scene
        fireBalls = new GameObject[2];
        for(int i = 0; i < 2; i++) {
            fireBalls[i] = new GameObject("core/assets/backgroundtextures/projectile.png",
            0, 0, true, false, null);

            fireBalls[i].getSprite().setSize(fireBalls[i].width / 4, fireBalls[i].height / 4);
            fireBalls[i].x = fireBalls[i].getSprite().getX();
            fireBalls[i].y = fireBalls[i].getSprite().getY();
        }
        fireBalls[0].getSprite().setX(GAME_WIDTH / 2  - fireBalls[0].getSprite().getWidth() / 2 + 100);
        fireBalls[0].getSprite().setY(GAME_HEIGHT / 2  - fireBalls[0].getSprite().getHeight() / 2 + 310);
        fireBalls[1].getSprite().setX(GAME_WIDTH / 2  - fireBalls[1].getSprite().getWidth() / 2 - 300);
        fireBalls[1].getSprite().setY(GAME_HEIGHT / 2  - fireBalls[1].getSprite().getHeight() / 2 + 300);

        fireBalls[0].getSprite().flip(true, false);
        fireBalls[0].getSprite().rotate(90.0f);
        fireBalls[1].getSprite().flip(true, true);
        fireBalls[1].getSprite().rotate(90.0f);

        fire = new GameObject("core/assets/backgroundtextures/fire-animation.png",
            0, 0, true, true, new Vector2(10, 1));
        fire.getSprite().setSize(fire.width / 2, fire.height / 2);
        fire.getSprite().setX(GAME_WIDTH / 2  - fire.getSprite().getWidth() / 2 + 100);
        fire.getSprite().setY(GAME_HEIGHT / 2  - fire.getSprite().getHeight() / 2 + 310);
        fire.x = fire.getSprite().getX();
        fire.y = fire.getSprite().getY();

        shootingShip = new GameObject("core/assets/backgroundtextures/ship1.png", 0, 0, true, false, null);
        shootingShip.getSprite().setSize(shootingShip.width / 2, shootingShip.height / 2);
        shootingShip.getSprite().setX(GAME_WIDTH / 2  - shootingShip.getSprite().getWidth() / 2 + 200);
        shootingShip.getSprite().setY(GAME_HEIGHT / 2  - shootingShip.getSprite().getHeight() / 2 + 320);

        destroyedShip = new GameObject("core/assets/backgroundtextures/ship2.png", 0, 0, true, false, null);
        destroyedShip.getSprite().setSize(destroyedShip.width / 2, destroyedShip.height / 2);
        destroyedShip.getSprite().setX(GAME_WIDTH / 2  - destroyedShip.getSprite().getWidth() / 2 - 200);
        destroyedShip.getSprite().setY(GAME_HEIGHT / 2  - destroyedShip.getSprite().getHeight() / 2 + 320);

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
