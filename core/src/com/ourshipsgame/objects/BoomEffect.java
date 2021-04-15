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
        this.animator = new Animator(texture, new Vector2(4, 4), (1.0f / 16f) + 0.015f);
        this.texture = texture;
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(64, 64);
        this.sprite.setOriginCenter();

    }

    public BoomEffect(Sound sound, Texture texture, Vector2 kl, float switchTime) {
        this.sound = sound;
        this.animator = new Animator(texture, kl, switchTime + 0.015f);
        this.texture = texture;
        this.sprite = new Sprite(this.texture);
        this.sprite.setSize(64, 64);
        this.sprite.setOriginCenter();

    }

    public BoomEffect(Sound sound, Texture texture, boolean multiple) {
        this.sound = sound;
        this.animator = new Animator(texture, new Vector2(4, 4), (1.0f / 16f) + 0.015f);
        this.texture = texture;
        this.sprites = new Sprite[1];
        this.sprites[0] = new Sprite(this.texture);
        this.sprites[0].setSize(64, 64);
        this.sprites[0].setOriginCenter();
    }

    
    /** 
     * @return Vector2
     */
    public Vector2 getPos() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    
    /** 
     * @param vector2f
     */
    public void setPos(Vector2f vector2f) {
        this.sprite.setPosition(vector2f.x - 32, vector2f.y - 32);
    }

    
    /** 
     * @param vector2f
     * @param rotation
     * @param radius
     */
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
                this.sprites[i].setPosition(vector2f.x, vector2f.y + (i * 64));
        } else {
            for (int i = 0; i < radius; i++)
                this.sprites[i].setPosition(vector2f.x + (i * 64), vector2f.y);
        }
    }

    public void updateAnimation() {
        this.animator.update();
        TextureRegion region = animator.getCurrentFrame();
        this.sprite.setRegion(region);
    }

    
    /** 
     * @param multiple
     */
    public void updateAnimation(boolean multiple) {
        this.animator.update();
        TextureRegion region = animator.getCurrentFrame();
        for (int i = 0; i < radius; i++)
            this.sprites[i].setRegion(region);
    }

    
    /** 
     * @param batch
     */
    public void drawEffect(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    
    /** 
     * @param batch
     * @param multiple
     */
    public void drawEffect(SpriteBatch batch, boolean multiple) {
        for (int i = 0; i < radius; i++)
            this.sprites[i].draw(batch);
    }

    
    /** 
     * @param multiple
     * @param soundVolume
     * @return boolean
     */
    public boolean playSound(boolean multiple, float soundVolume) {
        for (int i = 0; i < radius; i++)
            this.sound.play(soundVolume * (1 + (i * 0.1f)));
        return false;
    }

    
    /** 
     * @param soundVolume
     */
    public void playSound(float soundVolume) {
        this.sound.play(soundVolume);
    }

    public void resetAnimation() {
        this.animator.setStartAnimation();
    }
}
