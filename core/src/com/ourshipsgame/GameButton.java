package com.ourshipsgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;

public class GameButton extends TextButton {

    public Skin skin;
    Main game;

    public GameButton(String nameTag, float x, float y, Skin skin, final int buttonNumber, final Main game) {
        super(nameTag, skin);
        this.game = game;
        this.setX(x - this.getWidth() / 2);
        this.setY(y);

        this.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuOptions(buttonNumber);
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void menuOptions(int option) {
		switch(option) {
		case 1: // Enters to the game
            game.getScreen().dispose();
            game.setScreen(new GameScreen(game)); 
			break;
			
		case 2: // Enters to a help screen
			break;
			
		case 3: // Enters to a scores screen
			break;
			
		case 4: // Enters to a settings screen
            game.getScreen().dispose();
            game.setScreen(new OptionScreen(game));
			break;
			
		case 5: // Exits game
			Gdx.app.exit();
			break;

        case 6: // Backs from current screen
            game.getScreen().dispose();
            game.setScreen(new MenuScreen(game));
            break;
		}
	}

}
