package com.ourshipsgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    protected boolean goodPlacement;
    protected boolean shipDestroyed;
    protected Rectangle alligmentRectangle;
    protected Color rectColour;

    public GameObject(String internalPath, float x, float y) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public GameObject(String internalPath, float x, float y, boolean createSprite) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        if (createSprite)
            createSprite(texture);
    }

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

    protected void createSprite(Texture texture) {
        this.sprite = new Sprite(texture);
        this.oldPos = new Vector2(x, y);
        this.alligmentRectangle = new Rectangle(x, y, width, height);
        this.rectColour = new Color(1, 0, 0, 1);
        setSpritePos(this.oldPos);
    }

    protected void createSprite(Texture texture, int size) {
        this.sprite = new Sprite(texture);
        this.alligmentRectangle = new Rectangle(x, y, width, height);
        this.rectColour = new Color(1, 0, 0, 1);
        this.size = size;
        this.destroyed = new int[size];
        this.shipDestroyed = false;
        for (int i = 0; i < size; i++)
            destroyed[i] = 0;
        this.oldPos = new Vector2(x, y);
        setSpritePos(this.oldPos);
    }

    public Texture drawTexture() {
        return this.texture;
    }

    public void drawSprite(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void drawSprite(SpriteBatch batch, boolean drawRect, ShapeRenderer sr) {
        if (this.goodPlacement)
            changeRectColour();
        if (drawRect) {
            sr.rect(alligmentRectangle.x, alligmentRectangle.y, alligmentRectangle.width, alligmentRectangle.height,
                    rectColour, rectColour, rectColour, rectColour);
        }
        this.sprite.draw(batch);
    }

    public void setSpritePos(Vector2 vector2) {
        this.sprite.setPosition(vector2.x, vector2.y);
        this.alligmentRectangle.setPosition(vector2);
    }

    public boolean spriteContains(Vector2 point) {
        if (this.alligmentRectangle.contains(point))
            return true;
        return false;
    }

    public void changeRectColour() {
        if (goodPlacement)
            rectColour = Color.GREEN;
        else if (!goodPlacement)
            rectColour = Color.RED;
    }

    public void setGoodPlacement(boolean isIt) {
        this.goodPlacement = isIt;
    }

    public boolean isDestroyed() {
        int destroyed = 0;
        for (int i = 0; i < size; i++)
            if (this.destroyed[i] == 1)
                destroyed++;

        if (destroyed == size)
            return true;
        return false;
    }

    public boolean collide(Rectangle otherRectangle) {

        return false;
    }
}
