package com.ourshipsgame;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.ourshipsgame.handlers.Constant;

public class GameSlider extends Slider implements Constant {
    private Main game;

    public GameSlider(float x, float y, float min, float max, float stepSize, boolean vertical, Skin skin, final Main game) {
        super(min, max, stepSize, vertical, skin);
        this.game = game;
        this.setX(x - this.getWidth() / 2);
        this.setY(y);
    }

    public void setSliderType(int option) {
        final Slider sliderTmp = this;

        switch(option) {

        case 1:
            this.setVisualPercent(game.menuElements.sliderMusicPercent);
            this.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.menuElements.sliderMusicPercent = sliderTmp.getPercent();
                    game.menuElements.music.setVolume(sliderTmp.getPercent() / 5.0f);
                }
            });
            break;

        case 2:
            this.setVisualPercent(game.menuElements.sliderSoundPercent);
            this.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.menuElements.sliderSoundPercent = sliderTmp.getPercent();
                    game.menuElements.soundVolume = sliderTmp.getPercent() / 5.0f;
                }
            });
            break;
        }
    }
    
}
