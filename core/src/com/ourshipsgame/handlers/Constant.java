package com.ourshipsgame.handlers;

import org.lwjgl.util.vector.Vector2f;

public interface Constant {
    // Rozdzielczosc ekranu
    public int GAME_WIDTH = 1920;
    public int GAME_HEIGHT = 1080;
    public float GAME_WIDTH_F = 1920.0f;
    public float GAME_HEIGHT_F = 1080.0f;
    // Rozmiary kwadratów na planszy
    public final int BOX_WIDTH = 64;
    public final int BOX_HEIGHT = 64;
    public final float BOX_WIDTH_F = 64.0f;
    public final float BOX_HEIGHT_F = 64.0f;
    // Ilosc kwadratów na planszy wg osi
    public final int BOX_X_AXIS_NUMBER = 10;
    public final int BOX_Y_AXIS_NUMBER = 10;
    // Tile
    public final int BoardTilesInX = 20;
    public final int BoardTilesInY = 20;
    public final float BoardBoxToTile = 0.5f;
    // Współrzędne wieżyczek na statkach
    // 3
    public final Vector2f TurretsPos3[] = { new Vector2f(8, 56), new Vector2f(31, 59), new Vector2f(52, 56),
            new Vector2f(8, 73), new Vector2f(31, 75), new Vector2f(52, 73), new Vector2f(16, 109),
            new Vector2f(31, 107), new Vector2f(47, 109), new Vector2f(31, 130) };
    public final Vector2f TurretsPos2[] = { new Vector2f(31, 21), new Vector2f(31, 37), new Vector2f(31, 73),
            new Vector2f(31, 92) };
    public final Vector2f TurretsPos1[] = { new Vector2f(25, 32), new Vector2f(36, 32) };

    // Enumerator do słownego rozróżniania plansz -- przyda sie przy działaniu gry!
    public enum TURN {
        FIRST, SECOND
    }

    // Opcje graficzne
    public boolean FULLSCREENMODE = true;
    public boolean VSYNCENABLED = false;
    public int FPSMAX = 60;
    public boolean UNDECORATED = false;
    public boolean ALLOWSOFTWAREMODE = true;
    // Opcje muzyczne , key bindingi etc
    public float MUSIC_LEVEL = 1f;
    public float SOUND_LEVEL = 1f;

}
