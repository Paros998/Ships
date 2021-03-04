package com.ourshipsgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Rectangle {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected Texture texture;

    public GameObject(String internalPath, float x, float y) {
        texture = new Texture(internalPath);
        this.setX(x);
        this.setY(y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public void moveTexture(float x) {
        this.x += x;
    }

}
