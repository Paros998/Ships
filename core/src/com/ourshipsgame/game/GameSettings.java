package com.ourshipsgame.game;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameSettings {
    public Game game;
    public Music music;
    public Sound clickSound;
    public float sliderMusicPercent, sliderSoundPercent;
    public float soundVolume, musicVolume;
    public long soundId; // Add table to create more sounds
    
    public GameSettings(Game game) {
        this.game = game;

        try {
            loadSettings();
        } /* loading settings from file */
        catch (IOException e) {
            e.printStackTrace();
        }
        // Music and sounds
        music = Gdx.audio.newMusic(Gdx.files.internal("core/assets/music/Epic Pirate Battle Theme/Blackmoor Tides Loop.wav"));
        music.setLooping(true);
        music.setVolume(musicVolume);
        music.play();

        clickSound = Gdx.audio.newSound(Gdx.files.internal("core/assets/sounds/menu-click.mp3"));
        clickSound.pause();
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

    public void playSound() {
        clickSound.play(soundVolume);
    }
    
}
