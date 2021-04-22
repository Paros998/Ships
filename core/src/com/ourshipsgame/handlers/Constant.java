package com.ourshipsgame.handlers;

import org.lwjgl.util.vector.Vector2f;

/**
 * Interfejs przechowujący kilka stałych do obliczeń itp
 */
public interface Constant {
    // Rozdzielczosc ekranu
    /**
     * Ilość pikseli okna w poziomie
     */
    public int GAME_WIDTH = 1920;
    /**
     * Ilość pikseli okna w pionie
     */
    public int GAME_HEIGHT = 1080;
    /**
     * Ilość pikseli okna w poziomie
     */
    public float GAME_WIDTH_F = 1920.0f;
    /**
     * Ilość pikseli okna w pionie
     */
    public float GAME_HEIGHT_F = 1080.0f;
    // Rozmiary kwadratów na planszy
    /**
     * Szerokość jednej kratki
     */
    public final int BOX_WIDTH = 64;
    /**
     * Wysokość jednej kratki
     */
    public final int BOX_HEIGHT = 64;
    /**
     * Szerokość jednej kratki
     */
    public final float BOX_WIDTH_F = 64.0f;
    /**
     * Wysokość jednej kratki
     */
    public final float BOX_HEIGHT_F = 64.0f;
    // Ilosc kwadratów na planszy wg osi
    /**
     * Ilosc kwadratów na planszy wg osi X
     */
    public final int BOX_X_AXIS_NUMBER = 10;
    /**
     * Przelicznik kratek mapy tile do growej
     */
    public final float BoardBoxToTile = 0.5f;
    /**
     * Ilosc kwadratów na planszy wg osi Y
     */
    public final int BOX_Y_AXIS_NUMBER = 10;
    // Współrzędne wieżyczek na statkach
    /**
     * Pozycje wieżyczek na statku trój-polowym
     */
    public final Vector2f TurretsPos3[] = { new Vector2f(8, 56), new Vector2f(31, 59), new Vector2f(52, 56),
            new Vector2f(8, 73), new Vector2f(31, 75), new Vector2f(52, 73), new Vector2f(16, 109),
            new Vector2f(31, 107), new Vector2f(47, 109), new Vector2f(31, 130) };
    /**
     * Pozycje wieżyczek na statku dwu-polowym
     */
    public final Vector2f TurretsPos2[] = { new Vector2f(31, 21), new Vector2f(31, 37), new Vector2f(31, 73),
            new Vector2f(31, 92) };
    /**
     * Pozycje wieżyczek na statku jedno-polowym
     */
    public final Vector2f TurretsPos1[] = { new Vector2f(25, 32), new Vector2f(36, 32) };

    // Opcje graficzne
    /**
     * Zmienna przechowująca czy aplikacja ma być w trybie pełnoekranowym
     */
    public boolean FULLSCREENMODE = true;
    /**
     * Zmienna przechowująca włączenie Synchronizacji Pionowej
     */
    public boolean VSYNCENABLED = false;
    /**
     * Zmienna przechowująca maksymlną ilość klatek
     */
    public int FPSMAX = 999;
    /**
     * Zmienna przechowująca dodatkową opcję aplikacji
     */
    public boolean UNDECORATED = false;
    /**
     * Zmienna przechowująca dodatkową opcję aplikacji
     */
    public boolean ALLOWSOFTWAREMODE = true;

}
