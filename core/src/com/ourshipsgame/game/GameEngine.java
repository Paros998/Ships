package com.ourshipsgame.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ourshipsgame.handlers.Constant;
import com.ourshipsgame.objects.ShootParticleEffect;

import org.lwjgl.util.vector.Vector2f;

public abstract class GameEngine extends ScreenAdapter implements Constant {
    // Board class
    protected class Board {
        protected int[][] ShipsPlaced = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
        // Vector index is a ship index
        // x and y are dimensions in ShipsPlaced where is the ship beginning
        protected Vector2[] BoardShipsPos;
        protected int BoardNumber;

        protected Board(int numberOfShips, int BoardNumber) {
            this.BoardShipsPos = new Vector2[numberOfShips];
            this.BoardNumber = BoardNumber;
        }

        protected void placeShipOnBoard(int numberOfShips) {
            GameObject actualShip;
            for (int i = 0; i < numberOfShips; i++) {

                if (BoardNumber == 1)
                    actualShip = FirstBoardShipsSprites[i];
                else if (BoardNumber == 2)
                    actualShip = SecondBoardShipsSprites[i];
                else
                    return;

                int rotation = actualShip.rotation;
                int xPos = (int) ((actualShip.getX() - FirstBoardStart.x) / BOX_WIDTH_F);
                int yPos = (int) ((actualShip.getY() - FirstBoardStart.y) / BOX_HEIGHT_F);
                BoardShipsPos[i].x = xPos;
                BoardShipsPos[i].y = yPos;
                switch (actualShip.size) {
                case 3:
                    if (rotation == 0) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos][yPos + 1] = 1;
                        ShipsPlaced[xPos][yPos + 2] = 1;
                    } else if (rotation == 1) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos + 1][yPos] = 1;
                        ShipsPlaced[xPos + 2][yPos] = 1;
                    } else if (rotation == 2) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos][yPos - 1] = 1;
                        ShipsPlaced[xPos][yPos - 2] = 1;
                    } else {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos - 1][yPos - 1] = 1;
                        ShipsPlaced[xPos - 2][yPos - 2] = 1;
                    }
                    break;
                case 2:
                    if (rotation == 0) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos][yPos + 1] = 1;
                    } else if (rotation == 1) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos + 1][yPos] = 1;
                    } else if (rotation == 2) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos][yPos - 1] = 1;
                    } else {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos - 1][yPos - 1] = 1;
                    }
                    break;
                case 1:
                    ShipsPlaced[xPos][yPos] = 1;
                    break;
                }
            }
        }
    }

    // Important vars
    protected Board firstBoard;
    protected Board secondBoard;
    protected int[][] FirstPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] SecondPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected String[] internalPaths = { "core/assets/turrets/ship_gun_red.png", "core/assets/turrets/ship_big_gun.png",
            "core/assets/turrets/ship_big_gun_dual.png", "core/assets/turrets/ship_gun_huge.png" };
    protected Texture turretTextures[] = new Texture[4];
    protected Texture BigShipTextures[] = new Texture[2];
    protected Texture MediumShipTextures[] = new Texture[2];
    protected Texture SmallShipTextures[] = new Texture[2];
    // Particles[0] - shooting texture
    protected Texture Particles[] = new Texture[2];
    protected Vector2f FirstBoardStart = new Vector2f(8 * BOX_WIDTH_F * BoardBoxToTile,
            8 * BOX_HEIGHT_F * BoardBoxToTile);
    protected Vector2f SecondBoardStart;
    protected int gameHeight = GAME_HEIGHT;
    protected int gameWidth = GAME_WIDTH;
    protected float gameHeight_f = GAME_HEIGHT_F;
    protected float gameWidth_f = GAME_WIDTH_F;
    // Sounds and music
    protected Sound rotateSound;
    protected Sound ShootSounds[];
    protected Sound WaterExplosionSounds[];
    protected Sound MetalExplosionSounds[];
    // Other vars
    protected int threeBoxShips = 3;
    protected int twoBoxShips = 4;
    protected int oneBoxShips = 5;
    protected int sum = threeBoxShips + twoBoxShips + oneBoxShips;
    // Important Objects
    protected GameObject FirstBoardShipsSprites[] = new GameObject[sum];
    protected GameObject SecondBoardShipsSprites[] = new GameObject[sum];
    protected ShootParticleEffect shootEffect[] = new ShootParticleEffect[sum];
    // more other vars
    protected int activeSpriteDrag = 99;
    protected float xSprite;
    protected float ySprite;
    protected float xDiff;
    protected float yDiff;

    // loading method
    protected void loadGameEngine(AssetManager manager) {
        manager.load(internalPaths[0], Texture.class);
        manager.load(internalPaths[1], Texture.class);
        manager.load(internalPaths[2], Texture.class);
        manager.load(internalPaths[3], Texture.class);
        manager.load("core/assets/oneship/three/threeshipModel.png", Texture.class);
        manager.load("core/assets/oneship/three/threeshipModelwaves.png", Texture.class);
        manager.load("core/assets/oneship/two/twoshipModel.png", Texture.class);
        manager.load("core/assets/oneship/two/twoshipModelwaves.png", Texture.class);
        manager.load("core/assets/oneship/one/oneshipModel.png", Texture.class);
        manager.load("core/assets/oneship/one/oneshipModelwaves.png", Texture.class);
        manager.load("core/assets/sounds/TurretRotation.mp3", Sound.class);
        manager.load("core/assets/animations/boom3.png", Texture.class);
    }

    // game methods below
    // Stage 1
    protected boolean preparation(boolean computerEnemy, AssetManager manager) {
        boolean done = false;
        turretTextures[0] = manager.get(internalPaths[0], Texture.class);
        turretTextures[1] = manager.get(internalPaths[1], Texture.class);
        turretTextures[2] = manager.get(internalPaths[2], Texture.class);
        turretTextures[3] = manager.get(internalPaths[3], Texture.class);
        BigShipTextures[0] = manager.get("core/assets/oneship/three/threeshipModel.png", Texture.class);
        BigShipTextures[1] = manager.get("core/assets/oneship/three/threeshipModelwaves.png", Texture.class);
        MediumShipTextures[0] = manager.get("core/assets/oneship/two/twoshipModel.png", Texture.class);
        MediumShipTextures[1] = manager.get("core/assets/oneship/two/twoshipModelwaves.png", Texture.class);
        SmallShipTextures[0] = manager.get("core/assets/oneship/one/oneshipModel.png", Texture.class);
        SmallShipTextures[1] = manager.get("core/assets/oneship/one/oneshipModelwaves.png", Texture.class);
        rotateSound = manager.get("core/assets/sounds/TurretRotation.mp3", Sound.class);
        Particles[0] = manager.get("core/assets/animations/boom3.png", Texture.class);

        firstBoard = new Board(sum, 1);
        secondBoard = new Board(sum, 2);
        for (int i = 0; i < BOX_X_AXIS_NUMBER; i++)
            for (int j = 0; j < BOX_Y_AXIS_NUMBER; j++) {
                firstBoard.ShipsPlaced[i][j] = secondBoard.ShipsPlaced[i][j] = FirstPlayerShotsDone[i][j] = SecondPlayerShotsDone[i][j] = 0;
            }

        for (int i = 0; i < sum; i++) {
            if (i <= 2) {
                FirstBoardShipsSprites[i] = new GameObject(BigShipTextures[0], BigShipTextures[1], turretTextures,
                        FirstBoardStart.x + (i * BOX_WIDTH_F) + 1, FirstBoardStart.y - 191, true, 3, new Vector2(5, 1));
                shootEffect[i] = new ShootParticleEffect(Particles[0], 0, 0, new Vector2(8, 8),
                        FirstBoardShipsSprites[i].turretsAmmount);
            } else if (i > 2 && i <= 6) {
                FirstBoardShipsSprites[i] = new GameObject(MediumShipTextures[0], MediumShipTextures[1], turretTextures,
                        FirstBoardStart.x + (i * BOX_WIDTH_F) + 1, FirstBoardStart.y - 127, true, 2, new Vector2(5, 1));
                shootEffect[i] = new ShootParticleEffect(Particles[0], 0, 0, new Vector2(8, 8),
                        FirstBoardShipsSprites[i].turretsAmmount);
            } else
                FirstBoardShipsSprites[i] = new GameObject(SmallShipTextures[0], SmallShipTextures[1], turretTextures,
                        FirstBoardStart.x + (i * BOX_WIDTH_F) + 1, FirstBoardStart.y - 63, true, 1, new Vector2(5, 1));
            shootEffect[i] = new ShootParticleEffect(Particles[0], 0, 0, new Vector2(8, 8),
                    FirstBoardShipsSprites[i].turretsAmmount);
        }
        for (int i = 0; i < sum; i++) {
            GameObject actualShip = FirstBoardShipsSprites[i];
            if (isShipPlacedGood(actualShip)) {
                actualShip.setGoodPlacement(true);
            } else
                actualShip.setGoodPlacement(false);

            if (actualShip.goodPlacement)
                actualShip.changeRectColour();
            else {
                actualShip.setPosition(actualShip.oldPos);
                actualShip.changeRectColour();
            }
        }

        done = true;
        return done;
    }

    // Stage 2 methods to place ships on board
    protected void touchDownSprite(int screenX, int screenY) {
        for (int i = 0; i < sum; i++) {
            if (FirstBoardShipsSprites[i].spriteContains(new Vector2(screenX, gameHeight_f - screenY))) {
                activeSpriteDrag = i;
            }
        }
    }

    protected void touchUpSprite() {
        if (activeSpriteDrag <= sum - 1 && activeSpriteDrag >= 0) {
            GameObject actualShip = FirstBoardShipsSprites[activeSpriteDrag];

            if (isShipPlacedGood(actualShip)) {
                actualShip.setGoodPlacement(true);
            } else
                actualShip.setGoodPlacement(false);

            if (actualShip.goodPlacement)
                actualShip.changeRectColour();
            else {
                actualShip.setPosition(actualShip.oldPos);
                actualShip.changeRectColour();
            }
        }
        activeSpriteDrag = 99;
    }

    protected void dragSprite(int screenX, int screenY) {
        if (activeSpriteDrag <= sum - 1 && activeSpriteDrag >= 0) {
            GameObject actualShip = FirstBoardShipsSprites[activeSpriteDrag];
            xSprite = actualShip.width / 2;
            ySprite = actualShip.height / 2;
            float box_size = 64f;

            float xChange = screenX - actualShip.x - xSprite;
            float yChange = gameHeight_f - screenY - actualShip.y - ySprite;

            if (xChange >= box_size && yChange >= box_size)
                actualShip.translate(new Vector2(box_size, box_size));

            else if (xChange <= -box_size && yChange <= -box_size)
                actualShip.translate(new Vector2(-box_size, -box_size));

            else if (xChange >= box_size && yChange <= -box_size)
                actualShip.translate(new Vector2(box_size, -box_size));

            else if (xChange <= -box_size && yChange >= box_size)
                actualShip.translate(new Vector2(-box_size, box_size));

            else if (xChange >= box_size)
                actualShip.translateX(box_size);

            else if (xChange <= -box_size)
                actualShip.translateX(-box_size);

            else if (yChange >= box_size)
                actualShip.translateY(box_size);

            else if (yChange <= -box_size)
                actualShip.translateY(-box_size);

        }
    }

    protected boolean isShipPlacedGood(GameObject actualShip) {
        // Checking if ship is dropped on good position not colliding with anything
        Rectangle board = new Rectangle(FirstBoardStart.x, FirstBoardStart.y, BOX_WIDTH_F * BOX_X_AXIS_NUMBER,
                BOX_HEIGHT_F * BOX_Y_AXIS_NUMBER);
        if (board.contains(actualShip.alligmentRectangle)) {
            for (int i = 0; i < sum; i++) {
                if (actualShip == FirstBoardShipsSprites[i])
                    continue;
                // Need change Work in progress But working great actually
                boolean actualShipRotatedVertically = actualShip.rotation % 2 == 1;
                boolean otherShipRotatedVertically = FirstBoardShipsSprites[i].rotation % 2 == 1;
                //
                if (actualShipRotatedVertically != otherShipRotatedVertically) {
                    if (actualShip.collide(FirstBoardShipsSprites[i].alligmentRectangle, true,
                            actualShipRotatedVertically))
                        return false;
                } else {
                    if (actualShip.collide(FirstBoardShipsSprites[i].alligmentRectangle))
                        return false;
                }
            }
            return true;
        } else
            return false;
    }

    protected void rotateActualShip() {
        FirstBoardShipsSprites[activeSpriteDrag].rotate90();
    }

    protected void drawStage2Text(BitmapFont font, SpriteBatch batch) {
        String text = "Place your ships within the board !";
        int len = text.length();
        font.draw(batch, text, (gameWidth_f - 200 - (43 * (len / 2))), gameHeight_f / 2 + 200);
        text = "Confirm it by clicking READY button !";
        len = text.length();
        font.draw(batch, text, (gameWidth_f - 180 - (43 * (len / 2))), gameHeight_f / 2 + 100);
        text = "Press R to rotate current ship!";
        len = text.length();
        font.draw(batch, text, (gameWidth_f - 230 - (43 * (len / 2))), gameHeight_f / 2);
    }

    protected boolean checkAllShips() {
        for (int i = 0; i < sum; i++) {
            if (isShipPlacedGood(FirstBoardShipsSprites[i]) == false)
                return false;
        }
        return true;
    }

    // Stage 3 later
    protected void rotateTurretsWithMouse(float screenX, float screenY) {
        screenY = gameHeight_f - screenY;
        float angle;

        for (int j = 0; j < sum; j++) {
            GameObject actualShip = FirstBoardShipsSprites[j];
            for (int i = 0; i < actualShip.turretsAmmount; i++) {
                Vector2f turretPos = actualShip.getVectorPos(i);
                angle = MathUtils.radiansToDegrees * MathUtils.atan2(screenX - turretPos.x, turretPos.y - screenY);
                if (angle < 0)
                    angle += 360;
                switch (actualShip.rotation) {
                case 0:
                    break;
                case 1:
                    angle += 90;
                    break;
                case 2:
                    angle += 180;
                    break;
                case 3:
                    angle += 270;
                    break;
                }
                actualShip.rotateTurret(angle, i);
            }

        }
    }

    protected boolean shoot() {
        for (int i = 0; i < sum; i++) {
            shootEffect[i].setPositions(FirstBoardShipsSprites[i]);
        }
        return true;
    }
}
