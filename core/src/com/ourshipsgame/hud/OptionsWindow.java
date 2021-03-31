package com.ourshipsgame.hud;

import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ourshipsgame.game.GameSlider;
import com.ourshipsgame.handlers.Constant;

public class OptionsWindow extends Dialog implements Constant {

    // Fields
    private enum Actions {
        RESUME_GAME, OPTIONS, EXIT_GAME;
    }

    private Table layoutTable;
    public boolean turnedOn;
    private Hud hud;
    private OptionsWindow backReference = this;
    private GameSlider soundSlider, musicSlider;
    
    // Constructor
    public OptionsWindow(String windowName, Hud hud) {
        super(windowName, hud.getSkin());
        this.hud = hud;
        turnedOn = false;

        layoutTable = new Table();
        layoutTable.center();
        layoutTable.setFillParent(true);

        this.button("Resume Game", Actions.RESUME_GAME);
        this.button("Options", Actions.OPTIONS);
        this.button("Quit Game", Actions.EXIT_GAME);

        layoutTable.add(this).expandX().padBottom(10);
    }

    private void saveSettings() throws IOException {
        FileWriter savingPrintWriter;
        savingPrintWriter = new FileWriter("core/assets/files/settings.txt", false);
        savingPrintWriter.write(musicSlider.getPercent() + "\n" + soundSlider.getPercent());
        savingPrintWriter.close();
    }

    // Method
    @Override
    protected void result(final Object act) {
        Actions action = Actions.valueOf(act.toString());
        switch(action) {
            case RESUME_GAME:
                hud.gameSettings.playSound();
                this.hide();
                turnedOn = false;
                break;

            case OPTIONS:
                hud.gameSettings.playSound();
                new Dialog("Options", hud.getSkin()) {

                    {
                        soundSlider = new GameSlider(0, 100, 1, false, this.getSkin());
                        musicSlider = new GameSlider(0, 100, 1, false, this.getSkin());

                        musicSlider.setSliderType(1, hud.gameSettings);
                        soundSlider.setSliderType(2, hud.gameSettings);
                        
                        this.text("Music Volume");
                        this.row();
                        this.add(musicSlider).expandX().padLeft(20);
                        this.row();
                        this.text("SFX Volume");
                        this.add(soundSlider).expandX().padLeft(20);
                        this.row();
                        this.getCells().removeIndex(1);
                        this.add(this.getButtonTable());
                        this.button("Back");
                    }

                    @Override
                    protected void result(final Object act) {
                        hud.gameSettings.playSound();

                        try { saveSettings(); } 
                        catch (IOException e) { e.printStackTrace(); }
                        
                        backReference.show(hud.getStage());
                    }

                }.show(hud.getStage());
                break;
            
            case EXIT_GAME:
                hud.gameSettings.playSound();
                new Dialog("Confitm Exit", hud.getSkin()) {

                    {
                        button("Yes", "Yes");
                        button("No", "No");
                    }

                    @Override
                    protected void result(final Object act) {
                        if(act.toString() == "Yes")
                            Gdx.app.exit();
                        else {
                            hud.gameSettings.playSound();
                            backReference.show(hud.getStage());
                        }
                    }

                }.show(hud.getStage());
                break;
        }
    }
}
