package com.ourshipsgame.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameImageButton extends ImageButton {
 
    private Stage stage;
    private Skin skin;
    private OptionsWindow optionsWindow;

    public GameImageButton(float x, float y, Skin skin, Stage stage, String[] stylePaths) {
        super(skin);
        this.skin = skin;
        this.stage = stage;
        this.setX(x - this.getWidth() / 2);
        this.setY(y);
        int imageUp = 0, imageDown = 1;
        this.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(stylePaths[imageUp])));
        this.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(stylePaths[imageDown])));
    }

    private void createOptionsWindow() {
        if(optionsWindow == null) {
            optionsWindow = new OptionsWindow(skin, stage);
            optionsWindow.turnedOn = true;
        }
    }

    public void setOptionsListener() {
        this.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                createOptionsWindow();
                if(optionsWindow != null && optionsWindow.turnedOn)
                    return;
                else {
                    optionsWindow = null;
                    createOptionsWindow();
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }
}
