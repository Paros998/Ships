package com.ourshipsgame.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.ourshipsgame.GameTextButton;

public class OptionsWindow {

    // Fields
    private GameTextButton resumeButton, optionsButton, exitGameButton;
    private Table layoutTable;
    private Stage stage;
    public boolean turnedOn;
    
    // Constructor
    public OptionsWindow(Skin skin, Stage stage) {
        this.stage = stage;
        turnedOn = false;
        resumeButton = new GameTextButton("Resume Game", skin, 1);
        optionsButton = new GameTextButton("Options", skin, 2);
        exitGameButton = new GameTextButton("Quit Game", skin, 3);
        resumeButton.setListener(1, this);
        exitGameButton.setListener(3, this);

        layoutTable = new Table();
        layoutTable.center();
        layoutTable.setFillParent(true);
        layoutTable.add(resumeButton).expandX().padBottom(10);
        layoutTable.row();
        layoutTable.add(optionsButton).expandX().padBottom(10);
        layoutTable.row();
        layoutTable.add(exitGameButton).expandX().padBottom(10);

        stage.addActor(layoutTable);
    }

    public void hideWindow() {
        Array<Actor> actors = stage.getActors();
        actors.removeIndex(actors.size - 1);
        turnedOn = false;
    }
}
