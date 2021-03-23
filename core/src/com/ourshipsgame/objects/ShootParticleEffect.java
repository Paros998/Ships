package com.ourshipsgame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ourshipsgame.game.GameObject;

///One Ship Effect only
public class ShootParticleEffect {
    protected Sprite[] sprites;
    protected Animator animator;
    protected Texture particleTexture;
    protected float x, y;
    protected int turretsAmmount;

    public ShootParticleEffect(Texture particleTexture, float x, float y, Vector2 vector2, int turretsAmmount) {
        this.particleTexture = particleTexture;
        this.sprites = new Sprite[turretsAmmount];
        this.x = x;
        this.y = y;
        this.animator = new Animator(this.particleTexture, vector2, 1 / 64f);
        this.turretsAmmount = turretsAmmount;
        for (int i = 0; i < turretsAmmount; i++) {
            this.sprites[i] = new Sprite(particleTexture);
            this.sprites[i].setScale(0.3f);
        }

    }

    public void setPositions(GameObject actualShip) {
        Sprite[] turrets = actualShip.getTurrets();
        int rotation = actualShip.getRotation();
        for (int i = 0; i < turretsAmmount; i++) {
            float x = turrets[i].getX();
            float y = turrets[i].getY();
            float angle = turrets[i].getRotation();

            switch (rotation) {
            case 0:
                break;
            case 1:
                angle -= 90;
                break;
            case 2:
                angle -= 180;
                break;
            case 3:
                angle -= 270;
                break;
            }
            if (angle < 0)
                angle += 360;

            if (angle >= 0 && angle <= 45) {
                x += 4;
                y -= 12;
            } else if (angle > 45 && angle <= 90) {
                x += 12;
                y -= 4;
            } else if (angle > 90 && angle <= 135) {
                x += 12;
                y += 4;
            } else if (angle > 135 && angle <= 180) {
                x += 4;
                y += 12;
            } else if (angle > 180 && angle <= 225) {
                x -= 4;
                y += 12;
            } else if (angle > 225 && angle <= 270) {
                x -= 12;
                y += 4;
            } else if (angle > 270 && angle <= 315) {
                x -= 12;
                y -= 4;
            } else if (angle > 315 && angle <= 360) {
                x -= 4;
                y -= 12;
            }
            if (angle >= 0 && angle < 90) {
                this.sprites[i].flip(false, true);
                this.sprites[i].setY(sprites[i].getY() - 50f);
            } else if (angle >= 90 && angle < 180)
                this.sprites[i].flip(false, false);
            else if (angle >= 180 && angle < 270)
                this.sprites[i].flip(true, false);
            else if (angle >= 270 && angle < 360) {
                this.sprites[i].flip(true, true);
                this.sprites[i].setY(sprites[i].getY() - 50f);
            }
            this.sprites[i].setPosition(x - 53, y - 30);
        }
    }

    public void updateAnimation(GameObject actualShip) {
        this.animator.update();
        Sprite[] turrets = actualShip.getTurrets();
        TextureRegion region = this.animator.getCurrentFrame();
        int rotation = actualShip.getRotation();
        for (int i = 0; i < turretsAmmount; i++) {
            this.sprites[i].setRegion(region);
            float angle = turrets[i].getRotation();

            switch (rotation) {
            case 0:
                break;
            case 1:
                angle -= 90;
                break;
            case 2:
                angle -= 180;
                break;
            case 3:
                angle -= 270;
                break;
            }

            if (angle < 0)
                angle += 360;

            if (angle >= 0 && angle < 90)
                this.sprites[i].flip(false, true);
            else if (angle >= 90 && angle < 180)
                this.sprites[i].flip(false, false);
            else if (angle >= 180 && angle < 270)
                this.sprites[i].flip(true, false);
            else if (angle >= 270 && angle < 360)
                this.sprites[i].flip(true, true);
        }
    }

    public void drawAnimation(SpriteBatch batch) {
        for (int i = 0; i < turretsAmmount; i++) {
            this.sprites[i].draw(batch);
        }
    }
}
