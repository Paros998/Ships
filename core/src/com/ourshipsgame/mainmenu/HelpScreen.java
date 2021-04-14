package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.Main;
import com.ourshipsgame.handlers.Constant;
import com.ourshipsgame.hud.GameTextButton;

public class HelpScreen implements Screen, Constant {
    private Main game;
    private Table childTable, mainTable;
    private Sprite smallShip, mediumShip, largeShip, greenCrosshair, redCrosshair, cursor;
    private GameTextButton backButton;
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

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

        // Loading custom font
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("core/assets/fonts/Raleway-ExtraLightItalic.ttf"));
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.BROWN;
        parameter.size = 25;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);

        LabelStyle style = new LabelStyle(font, Color.GOLD);

        backButton = new GameTextButton("Back", 0, 0, game.menuElements.skin, 6, game);

        // Loading ships
        smallShip = new Sprite(new Texture("core/assets/oneship/one/oneshipModel.png"));
        mediumShip = new Sprite(new Texture("core/assets/oneship/two/twoshipModel.png"));
        largeShip = new Sprite(new Texture("core/assets/oneship/three/threeshipModel.png"));

        // Loading crosshairs
        greenCrosshair = new Sprite(new Texture("core/assets/cursors/crosshairGreen.png"));
        redCrosshair = new Sprite(new Texture("core/assets/cursors/crosshairRed.png"));
        cursor = new Sprite(new Texture("core/assets/ui/ui.hud/cursors/test.png"));


        // Creating main Table for buttons and child Table
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.left();

        // Creating Table for descriptions and icons
        childTable = new Table();
        //childTable.setFillParent(true);

        // Adding elements to child Table
        Label label;

        // Small Ship expand
        childTable.add(new Image(new SpriteDrawable(smallShip))).expandX();
        label = new Label("- small ship. Has 1 life.", style);
        childTable.add(label).expandX();

        // Green Crosshair expand
        childTable.add(new Image(new SpriteDrawable(greenCrosshair))).expandX().padLeft(20);
        label = new Label("- means that you are able to shoot.", style);
        childTable.add(label).expandX();

        childTable.row();

        // Medium Ship expand
        childTable.add(new Image(new SpriteDrawable(mediumShip))).expandX();
        label = new Label("- medium ship. Has 2 lifes.", style);
        childTable.add(label).expandX();

        // Red Crosshait expand
        childTable.add(new Image(new SpriteDrawable(redCrosshair))).expandX().padLeft(20);
        label = new Label("- means that you aren't able to shoot.", style);
        childTable.add(label).expandX();

        childTable.row();

        // Large Ship expand
        childTable.add(new Image(new SpriteDrawable(largeShip))).expandX();
        label = new Label("- large ship. Has 3 lifes.", style);
        childTable.add(label).expandX();

        // Cursor expand
        childTable.add(new Image(new SpriteDrawable(cursor))).expandX().padLeft(20);
        label = new Label("- mouse cursor. You can press buttons with it.", style);
        childTable.add(label).expandX();

        // Adding elements to main Table
        mainTable.add(childTable);//.padRight(200);
        mainTable.row();
        mainTable.add(backButton).padLeft(GAME_WIDTH / 2 - backButton.getWidth() / 2);

        stage.addActor(mainTable);
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