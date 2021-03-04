package com.ourshipsgame.handlers;

public interface Constant {
    // Rozdzielczosc ekranu
    public final int GAME_WIDTH = 1920;
    public final int GAME_HEIGHT = 1080;
    public final float GAME_WIDTH_F = 1920.0f;
    public final float GAME_HEIGHT_F = 1080.0f;
    // Rozmiary kwadratów na planszy
    public final int BOX_WIDTH = 64;
    public final int BOX_HEIGHT = 64;
    public final float BOX_WIDTH_F = 64.0f;
    public final float BOX_HEIGHT_F = 64.0f;
    // Ilosc kwadratów na planszy wg osi
    public final int BOX_X_AXIS_NUMBER = 10;
    public final int BOX_Y_AXIS_NUMBER = 10;

    // Enumerator do słownego rozróżniania plansz -- przyda sie przy działaniu gry!
    public enum BOARD {
        FIRST, SECOND
    }

    // Opcje graficzne
    public boolean FULLSCREENMODE = false;
    public boolean VSYNCENABLED = false;
    public int FPSMAX = 60;
    public boolean UNDECORATED = false;
    public boolean ALLOWSOFTWAREMODE = true;
    // Opcje muzyczne , key bindingi etc
    public float MUSIC_LEVEL = 1f;
    public float SOUND_LEVEL = 1f;
    /*
     * Tablice do przechowywania różnych informacji o grze np rozmieszczenie
     * jednostek na planszy czy też ataków,trafień etc
     */
    public int[][] FirstBoardShips = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    public int[][] SecondBoardShips = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    public int[][] FirstPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    public int[][] SecondPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
}
