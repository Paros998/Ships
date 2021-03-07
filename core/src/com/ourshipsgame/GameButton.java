package com.ourshipsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameButton extends TextButton {

    public Skin skin;
    Game game;

    public GameButton(String nameTag, float x, float y, Skin skin, final int buttonNumber, final Game game) {
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
		case 1:
            game.setScreen(new GameScreen(game));
			break;
			
		case 2:
			break;
			
		case 3:
			break;
			
		case 4:
			break;
			
		case 5:
			Gdx.app.exit();
			break;
		}
	}

}
