package com.ourshipsgame.hud;

<<<<<<< HEAD
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.GameObject;
=======
import com.ourshipsgame.game.GameObject;
>>>>>>> 61164429b1c55ceb5e955b46d7c49549e8f5711b

public class Hud {

    // Fields
    private GameObject uiBar;
<<<<<<< HEAD
    private Table layoutTable;
    private Stage stage;
    private Skin skin;
=======
>>>>>>> 61164429b1c55ceb5e955b46d7c49549e8f5711b

    // Constructor
    public Hud() {

<<<<<<< HEAD
        layoutTable = new Table();
        layoutTable.bottom();
        layoutTable.setFillParent(true);
        //layoutTable.setBackground();

        //uiBar = new GameObject("", x, y, createSprite, createAnimator, vector)

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        stage.addActor(layoutTable);
    }

    // Methods
    public void updateHud() {
        stage.act();
        stage.draw();
=======
>>>>>>> 61164429b1c55ceb5e955b46d7c49549e8f5711b
    }
}
