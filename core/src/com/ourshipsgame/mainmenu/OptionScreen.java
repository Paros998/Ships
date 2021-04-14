package com.ourshipsgame.mainmenu;

import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ourshipsgame.Main;
import com.ourshipsgame.game.GameSlider;
import com.ourshipsgame.handlers.Constant;
import com.ourshipsgame.hud.GameTextButton;

public class OptionScreen implements Screen, Constant {

    // Class Fields
    private Main game;
    private GameSlider musicSliderVolume, soundSilderVolume;
    private GameTextButton backButton;
    public Stage stage;
    public SpriteBatch batch;
    public TextField soundsVolumeText, musicVolumeText;

    // Constructor
    public OptionScreen(Main game) {
        this.game = game;
    }

    // Methods
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);

        // Texts
        soundsVolumeText = new TextField("SFX Volume", game.menuElements.skin);
        soundsVolumeText.clearListeners();
        soundsVolumeText.setPosition(GAME_WIDTH / 2 - soundsVolumeText.getWidth() / 2, GAME_HEIGHT / 2 + 180);

        musicVolumeText = new TextField("Music Volume", game.menuElements.skin);
        musicVolumeText.clearListeners();
        musicVolumeText.setPosition(GAME_WIDTH / 2 - soundsVolumeText.getWidth() / 2, GAME_HEIGHT / 2 - 20);

        // Sliders
        musicSliderVolume = new GameSlider(GAME_WIDTH / 2, GAME_HEIGHT / 2 - 100, 0, 100, 1, false,
                game.menuElements.skin, game);
        musicSliderVolume.setSliderType(1);

        soundSilderVolume = new GameSlider(GAME_WIDTH / 2, GAME_HEIGHT / 2 + 100, 0, 100, 1, false,
                game.menuElements.skin, game);
        soundSilderVolume.setSliderType(2);

        // Buttons
        backButton = new GameTextButton("Back", GAME_WIDTH / 2, GAME_HEIGHT / 2 - 300,
                game.menuElements.skin, 6, game);

        stage.addActor(backButton);
        stage.addActor(musicSliderVolume);
        stage.addActor(soundSilderVolume);
        stage.addActor(soundsVolumeText);
        stage.addActor(musicVolumeText);
    }

    private void update(float deltaTime) {
        game.menuElements.moveMenu(deltaTime);
        musicSliderVolume.setVisualPercent(game.menuElements.gameSettings.sliderMusicPercent);
        soundSilderVolume.setVisualPercent(game.menuElements.gameSettings.sliderSoundPercent);
        stage.act();
    }

    private void saveSettings() throws IOException {
        FileWriter savingPrintWriter;
        savingPrintWriter = new FileWriter("core/assets/files/settings.txt", false);
        savingPrintWriter.write(musicSliderVolume.getPercent() + "\n" + soundSilderVolume.getPercent());
        savingPrintWriter.close();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(1, 1, 1, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(game.menuElements.menuTexture.getTexture(), game.menuElements.menuTexture.x,
                game.menuElements.menuTexture.y);

        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        try {
            saveSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.dispose();
        batch.dispose();
    }

}
