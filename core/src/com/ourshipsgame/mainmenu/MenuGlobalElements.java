package com.ourshipsgame.mainmenu;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ourshipsgame.game.GameObject;

public class MenuGlobalElements {

    public Game game;

    public GlyphLayout layout;
    public BitmapFont font;

    public GameObject menuTexture;
    public Skin skin;
    public Music music;
    public Sound clickSound;
    public float sliderMusicPercent, sliderSoundPercent;
    public float soundVolume, musicVolume;
    public long soundId; // Add table to create more sounds
    private int direction = 0;

    public MenuGlobalElements(Game game) {
        try {
            loadSettings();
        } /* loading settings from file */
        catch (IOException e) {
            e.printStackTrace();
        }

        // Music and sounds
        music = Gdx.audio
                .newMusic(Gdx.files.internal("core/assets/music/Epic Pirate Battle Theme/Blackmoor Tides Loop.wav"));
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();

        clickSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/sounds/menu-click.mp3"));
        clickSound.pause();

        this.game = game;
        menuTexture = new GameObject(new Texture("core/assets/backgroundtextures/paperTextOld.png"), 0, 0, true, false,
                null);
        skin = new Skin(Gdx.files.internal("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json"));

        // Text, font
        font = new BitmapFont(Gdx.files.internal("core/assets/buttons/skins/rusty-robot/raw/font-title-export.fnt"));
        layout = new GlyphLayout();
        layout.setText(font, "Ships Game");
    }

    // Menu methods
    public void moveMenu(float deltaTime) {
        if ((menuTexture.x <= 0) && (direction == 0)) {
            if (menuTexture.x <= -119)
                direction = 1;
            menuTexture.moveTexture(-20 * deltaTime);
        }

        if ((menuTexture.x >= -120) && (direction == 1)) {
            if (menuTexture.x >= -1)
                direction = 0;
            menuTexture.moveTexture(20 * deltaTime);
        }
    }

    private void loadSettings() throws IOException {
        File file = new File("core/assets/files/settings.txt");
        if (!file.exists()) {
            file.createNewFile(); // Creating player file with home details
            musicVolume = 0.2f;
            soundVolume = 0.2f;
            sliderSoundPercent = sliderMusicPercent = 100.0f;
        } else {
            Scanner scanner = new Scanner(file);

            sliderMusicPercent = Float.valueOf(scanner.nextLine().trim());
            sliderSoundPercent = Float.valueOf(scanner.next().trim());

            musicVolume = sliderMusicPercent / 5.0f;
            soundVolume = sliderSoundPercent / 5.0f;

            scanner.close();
        }
    }
}
