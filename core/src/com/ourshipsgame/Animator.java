package com.ourshipsgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Animator {
    // Data
    private TextureRegion region;
    private Vector2 currentImage, imageCount;
    private TextureRegion[][] frames;
    private float switchTime, totalTime;

    // Constructor
    public Animator(Texture texture, Vector2 imageCount, float switchTime) {
        frames = new TextureRegion[(int) imageCount.y][(int) imageCount.x];
        region = new TextureRegion(texture);
        currentImage = new Vector2(0, 0);
        this.imageCount = imageCount;
        int frameWidth = (int) (region.getRegionWidth() / imageCount.x);
        int frameHeight = (int) (region.getRegionHeight() / imageCount.y);

        for (int j = 0; j < imageCount.y; j++)
            for (int i = 0; i < imageCount.x; i++)
                frames[j][i] = new TextureRegion(region, i * frameWidth, j * frameHeight, frameWidth, frameHeight);

        this.switchTime = switchTime;
        totalTime = 0;
    }

    // Public methods
    public void update() {

        totalTime += Gdx.graphics.getDeltaTime();

        if (totalTime >= switchTime) {
            totalTime -= switchTime;
            currentImage.x++;

            if (currentImage.x >= imageCount.x) {
                currentImage.x = 0;
                currentImage.y++;
            }
            if (imageCount.y <= currentImage.y) {
                currentImage.y = 0;
            }
        }
    }

    public void update(int row) {
        totalTime += Gdx.graphics.getDeltaTime();
        currentImage.y = row;
        if (totalTime >= switchTime) {
            totalTime -= switchTime;
            currentImage.x++;

            if (currentImage.x >= imageCount.x) {
                currentImage.x = 0;
            }
        }
    }

    public TextureRegion getCurrentFrame() {
        return frames[(int) currentImage.y][(int) currentImage.x];
    }

    public TextureRegion getIdleAnimation() {
        return frames[(int) currentImage.y][(int) (imageCount.x - 1)];
    }
}
