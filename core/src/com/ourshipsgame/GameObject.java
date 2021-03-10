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
    protected int rotation;
    protected Animator animator;

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

    public GameObject(String internalPath, float x, float y, boolean createSprite, int sizeofShip, Vector2 vector2) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth() / vector2.x;
        this.height = texture.getHeight() / vector2.y;
        if (createSprite) {
            createSprite(texture, sizeofShip);
            this.animator = new Animator(texture, vector2, 0.1f);
        }
    }

    public void updateTexture() {
        this.animator.update(0);
        this.sprite.setRegion(this.animator.getCurrentFrame());
        for (int i = 0; i < rotation; i++)
            this.sprite.rotate90(true);
    }

    public void moveTexture(float x) {
        this.x += x;
    }

    protected void createSprite(Texture texture) {
        this.sprite = new Sprite(texture);
        this.oldPos = new Vector2(x, y);
        this.alligmentRectangle = new Rectangle(x, y, width, height);
        this.rectColour = new Color(1, 0, 0, 1);
        this.sprite.setSize(width, height);
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
        this.sprite.setSize(width, height);
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
        this.x = vector2.x;
        this.y = vector2.y;
    }

    public void translate(Vector2 vector2) {
        this.sprite.translate(vector2.x, vector2.y);
        this.alligmentRectangle.setPosition(sprite.getX(), sprite.getY());
        this.x = sprite.getX();
        this.y = sprite.getY();

    }

    public void translateX(float x) {
        this.sprite.translateX(x);
        this.alligmentRectangle.setPosition(sprite.getX(), this.y);
        this.x = sprite.getX();

    }

    public void translateY(float y) {
        this.sprite.translateY(y);
        this.alligmentRectangle.setPosition(this.x, sprite.getY());
        this.y = sprite.getY();
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
        float bx = otherRectangle.x;
        float by = otherRectangle.y;
        float bx2 = otherRectangle.width + otherRectangle.x;
        float by2 = otherRectangle.height + otherRectangle.y;
        float ax = alligmentRectangle.x;
        float ay = alligmentRectangle.y;
        float ax2 = alligmentRectangle.width + alligmentRectangle.x;
        float ay2 = alligmentRectangle.height + alligmentRectangle.y;

        if (alligmentRectangle.contains(new Vector2(bx, by)) || alligmentRectangle.contains(new Vector2(bx2, by))
                || alligmentRectangle.contains(new Vector2(bx, by2))
                || alligmentRectangle.contains(new Vector2(bx2, by2)) || otherRectangle.contains(new Vector2(ax, ay))
                || otherRectangle.contains(new Vector2(ax2, ay)) || otherRectangle.contains(new Vector2(ax, ay2))
                || otherRectangle.contains(new Vector2(ax2, ay2)))
            return true;
        else
            return false;
    }

    public boolean collide(Rectangle otherRectangle, boolean diffRotation, boolean actualShipRotatedVertically) {
        float bx = otherRectangle.x;
        float by = otherRectangle.y;
        float bx2 = otherRectangle.width + otherRectangle.x;
        float by2 = otherRectangle.height + otherRectangle.y;
        float ax = alligmentRectangle.x;
        float ay = alligmentRectangle.y;
        float ax2 = alligmentRectangle.width + alligmentRectangle.x;
        float ay2 = alligmentRectangle.height + alligmentRectangle.y;

        if (alligmentRectangle.contains(new Vector2(bx, by)) || alligmentRectangle.contains(new Vector2(bx2, by))
                || alligmentRectangle.contains(new Vector2(bx, by2))
                || alligmentRectangle.contains(new Vector2(bx2, by2)) || otherRectangle.contains(new Vector2(ax, ay))
                || otherRectangle.contains(new Vector2(ax2, ay)) || otherRectangle.contains(new Vector2(ax, ay2))
                || otherRectangle.contains(new Vector2(ax2, ay2)))
            return true;
        else {
            if (actualShipRotatedVertically) {
                if (ax2 < bx || ax > bx2)
                    return false;

                float i = bx += 5;
                while (i < bx2) {
                    if (otherRectangle.contains(new Vector2(i, ay)))
                        return true;
                    i += 5;
                }
                i = ay += 5;
                while (i < ay2) {
                    if (otherRectangle.contains(new Vector2(bx, i)))
                        return true;
                    i += 5;
                }
            } else {
                if (bx2 < ax || bx > ax2)
                    return false;
                float i = ax += 5;
                while (i < ax2) {
                    if (alligmentRectangle.contains(new Vector2(i, by)))
                        return true;
                    i += 5;
                }
                i = by += 5;
                while (i < by2) {
                    if (alligmentRectangle.contains(new Vector2(ax, i)))
                        return true;
                    i += 5;
                }
            }
            return false;
        }
    }

    public void rotate90() {
        sprite.rotate90(true);
        rotation++;
        if (rotation > 3)
            rotation = 0;
        float tmp = this.height;
        this.height = width;
        this.width = tmp;
        sprite.setSize(width, height);
        alligmentRectangle.setSize(width, height);
    }
}
