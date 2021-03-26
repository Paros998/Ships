package com.ourshipsgame.mainmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ourshipsgame.game.GameObject;
import com.ourshipsgame.game.GameSettings;

public class MenuGlobalElements {

    public Game game;
    public GameSettings gameSettings;
    public GlyphLayout layout;
    public BitmapFont font;
    public Pixmap pixmap;
    public Cursor cursor;
    public GameObject menuTexture;
    public Skin skin;
    private int direction = 0;

    public MenuGlobalElements(Game game) {

        this.game = game;
        gameSettings = new GameSettings(game);
        menuTexture = new GameObject(new Texture("core/assets/backgroundtextures/paperTextOld.png"), 0, 0, true, false,
                null);
        skin = new Skin(Gdx.files.internal("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json"));

        // Text, font
        font = new BitmapFont(Gdx.files.internal("core/assets/buttons/skins/rusty-robot/raw/font-title-export.fnt"));
        layout = new GlyphLayout();
        layout.setText(font, "Ships Game");

        // Cursor
        pixmap = new Pixmap(Gdx.files.internal("core/assets/ui/ui.hud/cursors/test.png"));
        int xHotspot = pixmap.getWidth() / 2;
        int yHotspot = pixmap.getHeight() / 2;
        cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        Gdx.graphics.setCursor(cursor);
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

    public void disposeMenu() {
        gameSettings.clickSound.dispose();
        gameSettings.music.dispose();
        gameSettings = null;
    }
}
