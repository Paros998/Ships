package com.ourshipsgame;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameButton extends TextButton {

    public Skin skin;

    public GameButton(String nameTag, float x, float y, Skin skin) {
        super(nameTag, skin);
        this.setX(x - this.getWidth() / 2);
        this.setY(y);
    }
}
