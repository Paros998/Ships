package com.ourshipsgame.mainmenu;

import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.Main;
import com.ourshipsgame.handlers.Constant;
import com.ourshipsgame.hud.GameTextButton;
import com.ourshipsgame.mainmenu.Scores.Node;

public class ScoreScreen implements Screen, Constant {
    private ScrollPane scoreList;
    private GameTextButton backButton;
    private Table layoutTable, scrollTable;
    private Stage stage;
    private Scores scores;
    private Main game;
    private SpriteBatch batch;
    private BitmapFont font;

    public ScoreScreen(Main game) {
        this.game = game;
        scores = new Scores();
    }

    
    /** 
     * @param deltaTime
     */
    private void update(float deltaTime) {
        game.menuElements.moveMenu(deltaTime);
        stage.act();
    }

    @Override
    public void show() {
        // // Creating batch and font
        batch = new SpriteBatch();
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("core/assets/fonts/Raleway-ExtraLightItalic.ttf"));
        parameter.color = Color.GOLD;
        parameter.borderColor = Color.BROWN;
        parameter.size = 25;
        parameter.borderWidth = 2;
        font = generator.generateFont(parameter);

        // Creating screen elements
        backButton = new GameTextButton("Back", 0, 0, game.menuElements.skin, 6, game);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Creating Layout Table
        layoutTable = new Table();
        layoutTable.center();
        layoutTable.setFillParent(true);

        // Creating scoreList
        scrollTable = new Table();
        String text;
        Node node;
        LabelStyle style = new LabelStyle(font, Color.GOLD);
        for (int i = 0; i < scores.scoresList.size(); i++) {
            node = scores.scoresList.get(i);
            text = (i + 1) + " - " + node.name + " Score: " + node.scoreValue + " Time: "
                    + String.format("%.2f", node.timeElapsed) + " Shots accuracy: "
                    + NumberFormat.getPercentInstance().format(node.accuracyRatio);
            Label rLabel = new Label(text, style);
            rLabel.setAlignment(Align.center);
            rLabel.setWrap(true);
            scrollTable.add(rLabel);
            scrollTable.row().pad(20);
        }

        scoreList = new ScrollPane(scrollTable, game.menuElements.skin);
        scoreList.setFillParent(true);
        scoreList.layout();

        // Adding elements to main Table
        scrollTable.add(backButton);
        layoutTable.add(scoreList).fill().expand();

        // Adding actors to stage
        stage.addActor(layoutTable);
    }

    
    /** 
     * @param delta
     */
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

    
    /** 
     * @param width
     * @param height
     */
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
