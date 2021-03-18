package com.ourshipsgame.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.game.GameObject;

public class Hud {

    // Fields
    private GameObject uiBar;
    private Table layoutTable;
    private Stage stage;
    private Skin skin;

    // Constructor
    public Hud() {

        layoutTable = new Table();
        layoutTable.bottom();
        layoutTable.setFillParent(true);
        // layoutTable.setBackground();

        // uiBar = new GameObject("", x, y, createSprite, createAnimator, vector)

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(layoutTable);
    }

    // Methods
    public void updateHud() {
        stage.act();
        stage.draw();
    }
}
