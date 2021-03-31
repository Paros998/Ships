package com.ourshipsgame.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ourshipsgame.Main;
import com.ourshipsgame.handlers.Constant;

public class GameSlider extends Slider implements Constant {
    private Main game;

    public GameSlider(float x, float y, float min, float max, float stepSize, boolean vertical, Skin skin,
            final Main game) {
        super(min, max, stepSize, vertical, skin);
        this.game = game;
        this.setX(x - this.getWidth() / 2);
        this.setY(y);
    }

    public GameSlider(float min, float max, float stepSize, boolean vertical, Skin skin) {
        super(min, max, stepSize, vertical, skin);
    }

    public void setSliderType(int option, final GameSettings settings) {
        final Slider sliderTmp = this;

        if(option == 1) {
            this.setVisualPercent(settings.sliderMusicPercent);
            this.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    settings.sliderMusicPercent = sliderTmp.getPercent();
                    settings.music.setVolume(sliderTmp.getPercent() / 5.0f);
                }
                
            });
        }

        else if(option == 2) {
            this.setVisualPercent(settings.sliderSoundPercent);
            this.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    settings.sliderSoundPercent = sliderTmp.getPercent();
                    settings.soundVolume = sliderTmp.getPercent() / 5.0f;
                }
                
            });
        }

    }

    public void setSliderType(int option) {
        final Slider sliderTmp = this;

        switch (option) {

        case 1:
            this.setVisualPercent(game.menuElements.gameSettings.sliderMusicPercent);
            this.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.menuElements.gameSettings.sliderMusicPercent = sliderTmp.getPercent();
                    game.menuElements.gameSettings.music.setVolume(sliderTmp.getPercent() / 5.0f);
                }
            });
            break;

        case 2:
            this.setVisualPercent(game.menuElements.gameSettings.sliderSoundPercent);
            this.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.menuElements.gameSettings.sliderSoundPercent = sliderTmp.getPercent();
                    game.menuElements.gameSettings.soundVolume = sliderTmp.getPercent() / 5.0f;
                }
            });
            break;
        }
    }

}
