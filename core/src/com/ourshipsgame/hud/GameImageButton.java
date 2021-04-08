package com.ourshipsgame.hud;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class GameImageButton extends ImageButton {

    private Hud hud;
    private OptionsWindow optionsWindow;

    // Game Menu constructor
    public GameImageButton(float x, float y, Hud hud, Sprite[] buttonStyles) {
        super(new SpriteDrawable(buttonStyles[0]), 
            new SpriteDrawable(buttonStyles[1]));
        
        this.setX(x - this.getWidth());
        this.setY(y - this.getHeight());

        this.hud = hud;
        optionsWindow = new OptionsWindow("Game Menu", hud);
    }

    public GameImageButton(Sprite[] buttonStyles) {
        super(new SpriteDrawable(buttonStyles[0]),
            new SpriteDrawable(buttonStyles[1]));
    }

    public void setOptionsListener() {
        this.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hud.gameSettings.playSound();
                optionsWindow.show(hud.getStage());
                optionsWindow.turnedOn = true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    public boolean getGameMenuState() {
        return (boolean) (optionsWindow != null ? optionsWindow.turnedOn : false);
    }
}
