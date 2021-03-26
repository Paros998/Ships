package com.ourshipsgame.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OptionsWindow extends Dialog {

    // Fields
    private enum Actions {
        RESUME_GAME, OPTIONS, EXIT_GAME;
    }

    private Table layoutTable;
    public boolean turnedOn;
    private Hud hud;
    
    // Constructor
    public OptionsWindow(String windowName, Hud hud) {
        super(windowName, hud.getSkin());
        this.hud = hud;
        turnedOn = false;

        layoutTable = new Table();
        layoutTable.center();
        layoutTable.setFillParent(true);

        button("Resume Game", Actions.RESUME_GAME);
        button("Options", Actions.OPTIONS);
        button("Quit Game", Actions.EXIT_GAME);
        layoutTable.add(this).expandX().padBottom(10);
    }

    // Method
    @Override
    protected void result(final Object act) {
        Actions action = Actions.valueOf(act.toString());
        switch(action) {
            case RESUME_GAME:
                hud.gameSettings.playSound();
                hide();
                turnedOn = false;
                break;

            case OPTIONS: // temporary
                hud.gameSettings.playSound();
                hide();
                turnedOn = false;
                break;
            
            case EXIT_GAME:
                hud.gameSettings.playSound();
                Gdx.app.exit();
                break;
        }
    }
}
