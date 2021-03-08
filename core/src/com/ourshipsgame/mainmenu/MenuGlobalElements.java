package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ourshipsgame.GameObject;

public class MenuGlobalElements {

    public Game game;
    public GameObject menuTexture;
    public Skin skin;

    private int direction = 0;

    public MenuGlobalElements(Game game) {
        this.game = game;
        menuTexture = new GameObject("core/assets/backgroundtextures/paperTextOld.png", 0, 0, true);
        skin = new Skin(Gdx.files.internal("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json"));
    }
    
    // Menu methods
    public void moveMenu(float deltaTime) {
        if ((menuTexture.x <= 0) && (direction == 0)) {
            if (menuTexture.x <= -119)
                direction = 1;
            menuTexture.moveTexture(-20 * deltaTime);
        }

        if ((menuTexture.x >= -120) && (direction == 1)) {
            if (menuTexture.x >= -1)
                direction = 0;
            menuTexture.moveTexture(20 * deltaTime);
        }
    }
}
