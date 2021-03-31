package com.ourshipsgame.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.game.GameObject;
import com.ourshipsgame.game.GameSettings;
import com.ourshipsgame.handlers.Constant;

public class Hud implements Constant {

    // Fields
    private GameObject uiBar;
    private GameImageButton gameMenuButton;
    private float buttonsWidth, buttonsHeight;
    private Table layoutTable;
    private Stage stage;
    private Skin skin;
    public GameSettings gameSettings;

    // Constructor
    public Hud(AssetManager manager) {
        skin = new Skin();
        skin = manager.get("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json", Skin.class);
        layoutTable = new Table();
        layoutTable.setFillParent(true);

        stage = new Stage(new ScreenViewport());

        // Close button
        Texture[] buttonStyles = new Texture[2];
        buttonStyles[0] = manager.get("core/assets/ui/ui.hud/ui/global/modern/gear.png", Texture.class);
        buttonStyles[1] = manager.get("core/assets/ui/ui.hud/ui/global/modern/gear-press.png", Texture.class);

        Sprite[] buttonStylesSprites = new Sprite[2];

        for (int i = 0; i < buttonStylesSprites.length; i++) {
            buttonStylesSprites[i] = new Sprite(buttonStyles[i]);
            buttonStylesSprites[i].setSize(buttonStylesSprites[i].getWidth() / 1.25f, buttonStylesSprites[i].getHeight() / 1.25f);
        }

        buttonsWidth = buttonStylesSprites[0].getWidth();
        buttonsHeight = buttonStylesSprites[0].getHeight();

        gameMenuButton = new GameImageButton(this, buttonStylesSprites);
        gameMenuButton.setOptionsListener();

        // Adding actors to Table
        layoutTable.bottom();
        layoutTable.add(gameMenuButton).expandX().padBottom(13);

        stage.addActor(layoutTable);
    }

    // Methods
    private void setButtonsSprites(Texture[] textures, Sprite[] sprites) {
        for(int i = 0; i < sprites.length; i++) {
            sprites[i] = new Sprite(textures[i]);
            sprites[i].setSize(buttonsWidth, buttonsHeight);
        }
    }

    public void update() {
        stage.act();
        stage.draw();
    }

    public void render(SpriteBatch batch) {
       // uiBar.getSprite().draw(batch);
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public boolean isPasued() {
        return gameMenuButton.getGameMenuState();
    }
}
