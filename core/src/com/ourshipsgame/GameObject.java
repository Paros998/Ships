package com.ourshipsgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameObject extends Rectangle {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected Texture texture;
    protected Sprite sprite;
    protected int size;
    protected int[] destroyed;
    protected Vector2 oldPos;

    public GameObject(String internalPath, float x, float y, boolean createSprite, int sizeofShip) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        if (createSprite)
            createSprite(texture, sizeofShip);
    }

    public void moveTexture(float x) {
        this.x += x;
    }

    protected void createSprite(Texture texture, int size) {
        this.sprite = new Sprite(texture);
        this.size = size;
        this.destroyed = new int[size];
        this.oldPos = new Vector2(x, y);
        setSpritePos(this.oldPos);
    }

    public Texture drawTexture() {
        return this.texture;
    }

    public void drawSprite(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void setSpritePos(Vector2 vector2) {
        this.sprite.setPosition(vector2.x, vector2.y);
    }

    public boolean spriteContains(Vector2 point) {
        Rectangle rect = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        if (rect.contains(point))
            return true;
        return false;
    }
}
