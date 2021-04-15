package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.Main;
import com.ourshipsgame.game.GameObject;
import com.ourshipsgame.handlers.Constant;
import com.ourshipsgame.hud.GameTextButton;

/**
 * Klasa zawierająca główne menu gry.
 */
public class MenuScreen implements Screen, Constant {

    /**
     * Obiekt klasy Main. Odpowiedzialny głównie za zarządzanie ekranami. W tej
     * klasie jest również odwołaniem do klasy MenuGlobalElements.
     */
    private Main game;

    /**
     * Scena z silnika libGDX. Sprawia, że elementy w grze są interaktywne oraz
     * rysuje je na ekranie.
     */
    public Stage stage;

    /**
     * Obiekt silnika libGDX. Rysuje obiekty na ekranie tj. sprite'y czy tekstury.
     */
    public SpriteBatch batch;

    /**
     * Obiekty klasy GameObject. Są to statki w głównym menu gry.
     */
    private GameObject notDestroyedShip;
    private GameObject destroyedShip;
    private GameObject fire;

    /**
     * Tablica obiektów GameObject. Są to pociski 'wystrzeliwane' ze statków w
     * głównym menu gry.
     */
    private GameObject[] projectile;

    /**
     * Przyciski w głównym menu gry. Są przejściem do ekranu kolejno rozgrywki,
     * pomocy, wyników, opcji. Przycisk ostatni to wyjście z gry.
     */
    private GameTextButton playButton;
    private GameTextButton helpButon;
    private GameTextButton scoreButton;
    private GameTextButton optionsButton;
    private GameTextButton quitButton;

    /**
     * Główny i jedyny konstruktor klasy MenuScreen.
     * 
     * @param game Obiekt klasy Main.
     */
    public MenuScreen(Main game) {
        this.game = game;
    }

    /**
     * Metoda odpowiedzialna za odświeżanie opreacji w menu gry.
     * 
     * @param deltaTime Główny czas silniku libGDX.
     */
    private void update(float deltaTime) {
        stage.act();
        game.menuElements.moveMenu(deltaTime);
        fire.updateAnimation();
    }

    /**
     * Metoda odpowiedzialna za renderowanie menu gry.
     * 
     * @param deltaTime Główny czas silniku libGDX.
     */
    @Override
    public void render(float deltaTime) {
        // Updating menu
        update(deltaTime);

        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rendering menu
        batch.begin();

        batch.draw(game.menuElements.menuTexture.getTexture(), game.menuElements.menuTexture.x,
                game.menuElements.menuTexture.y);

        batch.draw(fire.getSprite(), fire.x, fire.y);
        notDestroyedShip.drawSprite(batch);
        destroyedShip.drawSprite(batch);

        for (GameObject fireBall : projectile)
            fireBall.drawSprite(batch);

        game.menuElements.font.draw(batch, game.menuElements.layout,
                GAME_WIDTH / 2 - game.menuElements.layout.width / 2, GAME_HEIGHT - 50);

        batch.end();
        stage.draw();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        /** Background scene */

        // Creating projectile
        projectile = new GameObject[4];

        for (int i = 0; i < projectile.length; i++) {
            projectile[i] = new GameObject(new Texture("core/assets/backgroundtextures/projectile-vertical.png"), 0, 0,
                    true, false, null);

            projectile[i].getSprite().setSize(projectile[i].width * 2, projectile[i].height * 2);
            projectile[i].x = projectile[i].getSprite().getX();
            projectile[i].y = projectile[i].getSprite().getY();

            if ((i & 1) == 0) { // even
                projectile[i].getSprite().flip(true, false);
                projectile[i].getSprite().rotate(-45.0f);
            } else { // odd
                projectile[i].getSprite().flip(false, true);
                projectile[i].getSprite().rotate(45.0f);
            }
        }

        float x = GAME_WIDTH / 2 - projectile[0].getSprite().getWidth() / 2;
        float y = GAME_HEIGHT / 2 - projectile[0].getSprite().getHeight() / 2;

        Vector2[] positions = { new Vector2(x + 100, y + 330), new Vector2(x - 50, y + 300),
                new Vector2(x + 60, y + 380), new Vector2(x - 50, y + 330) };

        for (int i = 0; i < projectile.length; i++)
            projectile[i].getSprite().setPosition(positions[i].x, positions[i].y);

        // Creating fire at destroyedShip
        fire = new GameObject(new Texture("core/assets/backgroundtextures/fire-animation.png"), 0, 0, true, true,
                new Vector2(10, 1));
        fire.getSprite().setSize(fire.width / 2, fire.height / 2);
        fire.getSprite().setX(GAME_WIDTH / 2 - fire.getSprite().getWidth() / 2 + 100);
        fire.getSprite().setY(GAME_HEIGHT / 2 - fire.getSprite().getHeight() / 2 + 310);
        fire.x = fire.getSprite().getX();
        fire.y = fire.getSprite().getY();

        // Creating notDestroyedShip
        notDestroyedShip = new GameObject(new Texture("core/assets/backgroundtextures/ship1.png"), 0, 0, true, false,
                null);
        notDestroyedShip.getSprite().setSize(notDestroyedShip.width / 2, notDestroyedShip.height / 2);
        notDestroyedShip.getSprite().setX(GAME_WIDTH / 2 - notDestroyedShip.getSprite().getWidth() / 2 + 200);
        notDestroyedShip.getSprite().setY(GAME_HEIGHT / 2 - notDestroyedShip.getSprite().getHeight() / 2 + 320);

        // Creating destroyedShip
        destroyedShip = new GameObject(new Texture("core/assets/backgroundtextures/ship2.png"), 0, 0, true, false,
                null);
        destroyedShip.getSprite().setSize(destroyedShip.width / 2, destroyedShip.height / 2);
        destroyedShip.getSprite().setX(GAME_WIDTH / 2 - destroyedShip.getSprite().getWidth() / 2 - 200);
        destroyedShip.getSprite().setY(GAME_HEIGHT / 2 - destroyedShip.getSprite().getHeight() / 2 + 320);

        // Creating buttons
        playButton = new GameTextButton("Play", GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100, game.menuElements.skin, 1, game);

        helpButon = new GameTextButton("Help", GAME_WIDTH / 2, GAME_HEIGHT / 2, game.menuElements.skin, 2, game);

        scoreButton = new GameTextButton("Score", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100, game.menuElements.skin, 3,
                game);

        optionsButton = new GameTextButton("Options", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 200, game.menuElements.skin, 4,
                game);

        quitButton = new GameTextButton("Exit", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300, game.menuElements.skin, 5, game);

        // Adding actors to scene
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

    /**
     * @param width
     * @param height
     */
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
    }
}
