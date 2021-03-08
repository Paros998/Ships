package com.ourshipsgame;

import com.badlogic.gdx.Game;

public class Main extends Game {

    public MenuGlobalElements menuElements;

    @Override
    public void create() {
        menuElements = new MenuGlobalElements(this);
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
