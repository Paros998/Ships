package com.ourshipsgame.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.game.GameObject;
import com.ourshipsgame.handlers.Constant;

public class Hud implements Constant {

    // Fields
    private GameObject uiBar;
    private GameImageButton gameMenuButton;
    private Table layoutTable;
    private Stage stage;
    private Skin skin;

    // Constructor
    public Hud(AssetManager manager) {
        skin = new Skin();
        skin = manager.get("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json", Skin.class);
        layoutTable = new Table(skin);
        layoutTable.bottom();
        layoutTable.setFillParent(true);

        // Main bar
        Texture uiTexture = manager.get("core/assets/ui/CustomTopBar.bmp", Texture.class);
        uiBar = new GameObject((uiTexture), 0, 0, true, false, null);
        uiBar.getSprite().setX(GAME_WIDTH / 2 - uiBar.getWidth() / 2);
        uiBar.getSprite().setY(uiBar.getHeight() / 84);
        uiBar.getSprite().setScale(3.0f, 1);

        stage = new Stage(new ScreenViewport());

        // Close button
        Texture[] internalStylePaths = new Texture[2];
        internalStylePaths[0] = manager.get("core/assets/ui/ui.hud/ui/global/modern/gear.png", Texture.class);
        internalStylePaths[1] = manager.get("core/assets/ui/ui.hud/ui/global/modern/gear-press.png", Texture.class);
        gameMenuButton = new GameImageButton(skin, stage, internalStylePaths);
        gameMenuButton.setOptionsListener();

        layoutTable.add(gameMenuButton).expandX().padTop(10);

        stage.addActor(layoutTable);
    }

    // Methods
    public void update() {
        stage.act();
        stage.draw();
    }

    public void render(SpriteBatch batch) {
        uiBar.getSprite().draw(batch);
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isPasued() {
        return gameMenuButton.getGameMenuState();
    }
}
