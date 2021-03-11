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
    protected Texture textureWave;
    protected Sprite sprite;
    protected Sprite spriteWave;
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

    public GameObject(String internalPath, float x, float y, boolean createSprite, boolean createAnimator,
            Vector2 vector) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();

        if (createAnimator)
            animator = new Animator(texture, vector, 0.25f);
        if (createSprite)
            createSprite(texture);
    }

    public GameObject(String internalPath, String internalPath2, float x, float y, boolean createSprite, int sizeofShip,
            Vector2 vector2) {
        this.texture = new Texture(internalPath);
        this.textureWave = new Texture(internalPath2);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        if (createSprite) {
            createSprite(texture, sizeofShip);
            createSpriteWave(textureWave);
            this.animator = new Animator(textureWave, vector2, 0.14f);
        }
    }

    public void updateAnimation() {
        animator.update(0);
        sprite.setRegion(animator.getCurrentFrame());
    }

    public void updateTexture() {
        this.animator.update(0);
        this.spriteWave.setRegion(this.animator.getCurrentFrame());
        this.spriteWave.setScale(1.5f, 1.5f);

        for (int i = 0; i < rotation; i++)
            this.spriteWave.rotate90(true);

        switch (rotation) {
        case 0: {
            if (size == 3)
                this.spriteWave.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 1.2f);
            else if (size == 2)
                this.spriteWave.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 1.3f);
            else
                this.spriteWave.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 1.5f);
            break;
        }
        case 1: {
            if (size == 3)
                this.spriteWave.setOrigin(sprite.getWidth() / 1.2f, sprite.getHeight() / 2f);
            else if (size == 2)
                this.spriteWave.setOrigin(sprite.getWidth() / 1.3f, sprite.getHeight() / 2f);
            else
                this.spriteWave.setOrigin(sprite.getWidth() / 1.5f, sprite.getHeight() / 2f);
            break;
        }
        case 2: {
            if (size == 3)
                this.spriteWave.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 4.5f);
            else if (size == 2)
                this.spriteWave.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 4.5f);
            else
                this.spriteWave.setOrigin(sprite.getWidth() / 1.7f, sprite.getHeight() / 4.5f);
            break;

        }
        case 3: {
            if (size == 3)
                this.spriteWave.setOrigin(sprite.getWidth() / 4.2f, sprite.getHeight() / 2f);
            else if (size == 2)
                this.spriteWave.setOrigin(sprite.getWidth() / 3.4f, sprite.getHeight() / 2f);
            else
                this.spriteWave.setOrigin(sprite.getWidth() / 2.9f, sprite.getHeight() / 2f);
            break;
        }

        }
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

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    protected void createSpriteWave(Texture texture) {
        this.spriteWave = new Sprite(texture);
        this.oldPos = new Vector2(x, y);
        this.alligmentRectangle = new Rectangle(x, y, width, height);
        this.rectColour = new Color(1, 0, 0, 1);
        this.spriteWave.setSize(width, height);
        this.spriteWave.setPosition(oldPos.x, oldPos.y);
        this.spriteWave.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 1.5f);
    }

    public Texture drawTexture() {
        return this.texture;
    }

    public void drawSprite(SpriteBatch batch) {
        if (spriteWave != null)
            this.spriteWave.draw(batch);
        this.sprite.draw(batch);
    }

    public void drawSprite(SpriteBatch batch, boolean drawRect, ShapeRenderer sr) {
        if (this.goodPlacement)
            changeRectColour();
        this.spriteWave.draw(batch);
        if (drawRect) {
            sr.rect(alligmentRectangle.x, alligmentRectangle.y, alligmentRectangle.width, alligmentRectangle.height,
                    rectColour, rectColour, rectColour, rectColour);
        }
        this.sprite.draw(batch);
    }

    public void setSpritePos(Vector2 vector2) {
        this.sprite.setPosition(vector2.x, vector2.y);
        if (spriteWave != null)
            this.spriteWave.setPosition(vector2.x, vector2.y);
        this.alligmentRectangle.setPosition(vector2);
        this.x = vector2.x;
        this.y = vector2.y;
    }

    public void translate(Vector2 vector2) {
        this.sprite.translate(vector2.x, vector2.y);
        if (spriteWave != null)
            this.spriteWave.translate(vector2.x, vector2.y);
        this.alligmentRectangle.setPosition(sprite.getX(), sprite.getY());
        this.x = sprite.getX();
        this.y = sprite.getY();

    }

    public void translateX(float x) {
        this.sprite.translateX(x);
        if (spriteWave != null)
            this.spriteWave.translateX(x);
        this.alligmentRectangle.setPosition(sprite.getX(), this.y);
        this.x = sprite.getX();

    }

    public void translateY(float y) {
        this.sprite.translateY(y);
        if (spriteWave != null)
            this.spriteWave.translateY(y);
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
        if (spriteWave != null) {
            spriteWave.rotate90(true);
            this.spriteWave.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 1.5f);
        }
        rotation++;
        if (rotation > 3)
            rotation = 0;
        float tmp = this.height;
        this.height = width;
        this.width = tmp;
        sprite.setSize(width, height);
        spriteWave.setSize(width, height);
        alligmentRectangle.setSize(width, height);
    }
}
