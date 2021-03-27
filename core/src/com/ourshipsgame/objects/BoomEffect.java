package com.ourshipsgame.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import org.lwjgl.util.vector.Vector2f;

public class BoomEffect {
    private Sound sound;
    private Animator animator;
    private Texture texture;
    private Sprite sprite;
    private int radius;
    private Sprite[] sprites;

    public BoomEffect(Sound sound, Texture texture) {
        this.sound = sound;
        this.animator = new Animator(texture, new Vector2(4, 4), 1 / 16f);
        this.texture = texture;
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(64, 64);
        this.sprite.setOriginCenter();

    }

    public BoomEffect(Sound sound, Texture texture, boolean multiple) {
        this.sound = sound;
        this.animator = new Animator(texture, new Vector2(4, 4), 1 / 16f);
        this.texture = texture;
        this.sprites = new Sprite[1];
        this.sprites[0] = new Sprite(this.texture);
        this.sprites[0].setSize(64, 64);
        this.sprites[0].setOriginCenter();
    }

    public void setPos(Vector2f vector2f) {
        this.sprite.setPosition(vector2f.x - 32, vector2f.y - 32);
    }

    public void setPos(Vector2f vector2f, int rotation, int radius) {
        this.radius = radius;
        this.sprites = new Sprite[radius];
        for (int i = 0; i < radius; i++) {
            this.sprites[i] = new Sprite(this.texture);
            this.sprites[i].setSize(64, 64);
            this.sprites[i].setOriginCenter();
        }
        if (rotation % 2 == 0) {
            for (int i = 0; i < radius; i++)
                this.sprites[i].setPosition(vector2f.x + 32, vector2f.y + 32 + (i * 64));
        } else {
            for (int i = 0; i < radius; i++)
                this.sprites[i].setPosition(vector2f.x + 32 + (i * 64), vector2f.y + 32);
        }
    }

    public void updateAnimation() {
        this.animator.update();
        TextureRegion region = animator.getCurrentFrame();
        this.sprite.setRegion(region);
    }

    public void updateAnimation(boolean multiple) {
        this.animator.update();
        TextureRegion region = animator.getCurrentFrame();
        for (int i = 0; i < radius; i++)
            this.sprites[i].setRegion(region);
    }

    public void drawEffect(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void drawEffect(SpriteBatch batch, boolean multiple) {
        for (int i = 0; i < radius; i++)
            this.sprites[i].draw(batch);
    }

    public void playSound() {
        this.sound.play(0.5f);
    }

}
