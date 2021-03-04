package com.ourshipsgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Rectangle {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected Texture texture;
    protected Sprite sprite;

    public GameObject(String internalPath, float x, float y, boolean createSprite) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        if (createSprite)
            createSprite(texture);
    }

    public void moveTexture(float x) {
        this.x += x;
    }

    protected void createSprite(Texture texture) {
        this.sprite = new Sprite(texture);
        this.sprite.setX(this.x);
        this.sprite.setY(this.y);
    }

    protected Texture drawTexture() {
        return this.texture;
    }

    protected void drawSprite(SpriteBatch batch) {
        this.sprite.draw(batch);
    }
}
