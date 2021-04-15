package com.ourshipsgame.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ourshipsgame.Main;
import com.ourshipsgame.game.GameScreen;
import com.ourshipsgame.mainmenu.HelpScreen;
import com.ourshipsgame.mainmenu.MenuScreen;
import com.ourshipsgame.mainmenu.OptionScreen;
import com.ourshipsgame.mainmenu.ScoreScreen;

public class GameTextButton extends TextButton {

    public Skin skin;
    Main game;

    // While in Game Constructor
    public GameTextButton(String nameTag, Skin skin, final int buttonNumber) {
        super(nameTag, skin);
    }

    // While in Main Menu Constructor
    public GameTextButton(String nameTag, float x, float y, Skin skin, final int buttonNumber, final Main game) {
        super(nameTag, skin);
        this.game = game;
        this.setX(x - this.getWidth() / 2);
        this.setY(y);

        this.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuOptions(buttonNumber);
                game.menuElements.gameSettings.clickSound.play(game.menuElements.gameSettings.soundVolume);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    
    /** 
     * @param option
     */
    // Setlistener method to create

    private void menuOptions(int option) {
        switch (option) {
        case 1: // Enters to the game
            game.getScreen().dispose();
            game.setScreen(new GameScreen(game));
            break;

        case 2: // Enters to a help screen
            game.getScreen().dispose();
            game.setScreen(new HelpScreen(game));
            break;

        case 3: // Enters to a scores screen
            game.getScreen().dispose();
            game.setScreen(new ScoreScreen(game));
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
