package com.ourshipsgame.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Score {
    private float scoreValue;
    private float timeElapsed;
    private int shotsFired;
    private int shotsMissed;
    private int shotsHitted;
    private float accuracyRatio;
    private int shipsDestroyed;
    private int idNumber;
    private String PlayerName;
    private int combo;

    public Score(int id) {
        idNumber = id;
    }

    public void setPlayerName(String name) {
        PlayerName = name;
    }

    public void drawInfo(BitmapFont hudFont, SpriteBatch batch, float gameWidth_f, float gameHeight_f,
            int ThreeShipsLeft, int TwoShipsLeft, int OneShipsLeft, Texture[] shipIcons) {
        float xstart;
        float vFloat;
        int second;
        String time;
        switch (idNumber) {
        case 1:

            xstart = gameWidth_f / 2 - 300;

            hudFont.draw(batch, this.getPlayerName(), xstart, gameHeight_f - 10);

            vFloat = this.getTimeElapsed();
            second = (int) vFloat / 60;
            time = "Total time: " + second + "." + String.format("%.2f", vFloat - second * 60f);
            hudFont.draw(batch, time, xstart, gameHeight_f - 35);

            hudFont.draw(batch, "Accuracy: " + (int) (this.getAccuracyRatio() * 100) + "%", xstart + 150,
                    gameHeight_f - 35);

            hudFont.draw(batch, "Total score: " + this.getScoreValue(), xstart, gameHeight_f - 60);

            hudFont.draw(batch, "Combo: " + this.getCombo(), xstart + 150, gameHeight_f - 60);

            batch.draw(shipIcons[0], xstart, gameHeight_f - 110);
            hudFont.draw(batch, ThreeShipsLeft + "/3", xstart + shipIcons[0].getWidth(), gameHeight_f - 85);

            batch.draw(shipIcons[1], xstart + 50, gameHeight_f - 107.5f);
            hudFont.draw(batch, TwoShipsLeft + "/4", xstart + 50 + shipIcons[1].getWidth(), gameHeight_f - 85);

            batch.draw(shipIcons[2], xstart + 100, gameHeight_f - 105);
            hudFont.draw(batch, OneShipsLeft + "/5", xstart + 100 + shipIcons[2].getWidth(), gameHeight_f - 85);
            break;
        case 2:

            xstart = gameWidth_f / 2 + 170;

            hudFont.draw(batch, this.getPlayerName(), xstart, gameHeight_f - 10);

            vFloat = this.getTimeElapsed();
            second = (int) vFloat / 60;
            time = "Total time: " + second + "." + String.format("%.2f", vFloat - second * 60f);
            hudFont.draw(batch, time, xstart, gameHeight_f - 35);

            hudFont.draw(batch, "Accuracy: " + (int) (this.getAccuracyRatio() * 100) + "%", xstart - 150,
                    gameHeight_f - 35);

            hudFont.draw(batch, "Total score: " + this.getScoreValue(), xstart, gameHeight_f - 60);

            hudFont.draw(batch, "Combo: " + this.getCombo(), xstart - 150, gameHeight_f - 60);

            batch.draw(shipIcons[0], xstart - 5, gameHeight_f - 110);
            hudFont.draw(batch, ThreeShipsLeft + "/3", xstart + shipIcons[0].getWidth(), gameHeight_f - 85);

            batch.draw(shipIcons[1], xstart + 50 - 5, gameHeight_f - 107.5f);
            hudFont.draw(batch, TwoShipsLeft + "/4", xstart + 50 + shipIcons[1].getWidth(), gameHeight_f - 85);

            batch.draw(shipIcons[2], xstart + 100 - 5, gameHeight_f - 105);
            hudFont.draw(batch, OneShipsLeft + "/5", xstart + 100 + shipIcons[2].getWidth(), gameHeight_f - 85);
            break;
        }
    }

    public void update(int[][] PlayerShots) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                if (PlayerShots[i][j] != 0)
                    shotsFired++;
                if (PlayerShots[i][j] == -1)
                    shotsMissed++;
                else if (PlayerShots[i][j] == 1 || PlayerShots[i][j] == 2)
                    shotsHitted++;
            }
        if (shotsFired != 0)
            accuracyRatio = (float) shotsHitted / (float) shotsFired;
    }

    public void updateTime(float deltaTime) {
        timeElapsed += deltaTime;
    }

    public void addPointsForHit() {
        this.scoreValue += (50 * (1 + 0.1 * combo));
    }

    public void addPointsForDestroy(int sizeofShip) {
        shipsDestroyed++;
        if (sizeofShip == 3)
            this.scoreValue += (150 * (1 + 0.05 * combo));
        else if (sizeofShip == 2)
            this.scoreValue += (100 * (1 + 0.05 * combo));
        else
            this.scoreValue += (50 * (1 + 0.05 * combo));
    }

    public void increaseCombo() {
        this.combo++;
    }

    public void zeroCombo() {
        this.combo = 0;
    }

    /**
     * @return the scoreValue
     */
    public float getScoreValue() {
        return scoreValue;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return PlayerName;
    }

    /**
     * @return the timeElapsed
     */
    public float getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * @return the accuracyRatio
     */
    public float getAccuracyRatio() {
        return accuracyRatio;
    }

    /**
     * @return the combo
     */
    public int getCombo() {
        return combo;
    }

    /**
     * @return the shipsDestroyed
     */
    public int getShipsDestroyed() {
        return shipsDestroyed;
    }

    /**
     * @return the shotsMissed
     */
    public int getShotsMissed() {
        return shotsMissed;
    }

    /**
     * @return the idNumber
     */
    public int getIdNumber() {
        return idNumber;
    }
}
