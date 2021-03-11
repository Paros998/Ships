package com.ourshipsgame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ourshipsgame.handlers.Constant;
import org.lwjgl.util.vector.Vector2f;

public abstract class GameEngine extends ScreenAdapter implements Constant {
    // Important vars
    protected int[][] FirstBoardShipsPos = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] SecondBoardShipsPos = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] FirstPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] SecondPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected Vector2f FirstBoardStart = new Vector2f(8 * BOX_WIDTH_F * BoardBoxToTile,
            8 * BOX_HEIGHT_F * BoardBoxToTile);
    protected Vector2f SecondBoardStart;
    protected int gameHeight = GAME_HEIGHT;
    protected int gameWidth = GAME_WIDTH;
    protected float gameHeight_f = GAME_HEIGHT_F;
    protected float gameWidth_f = GAME_WIDTH_F;
    // Other vars
    protected int threeBoxShips = 3;
    protected int twoBoxShips = 4;
    protected int oneBoxShips = 5;
    protected int sum = threeBoxShips + twoBoxShips + oneBoxShips;
    // Important Objects
    protected GameObject FirstBoardShipsSprites[] = new GameObject[sum];
    protected GameObject SecondBoardShipsSprites[] = new GameObject[sum];
    // more other vars
    protected int activeSpriteDrag = 99;
    protected float xSprite;
    protected float ySprite;
    protected float xDiff;
    protected float yDiff;

    // game methods below
    // Stage 1
    protected boolean preparation(boolean computerEnemy) {
        boolean done = false;
        for (int i = 0; i < BOX_X_AXIS_NUMBER; i++)
            for (int j = 0; j < BOX_Y_AXIS_NUMBER; j++) {
                FirstBoardShipsPos[i][j] = SecondBoardShipsPos[i][j] = FirstPlayerShotsDone[i][j] = SecondPlayerShotsDone[i][j] = 0;
            }

        for (int i = 0; i < sum; i++) {
            if (i <= 2) {
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/three/threeshipModel.png",
                        "core/assets/oneship/three/threeshipModelwaves.png", FirstBoardStart.x + (i * BOX_WIDTH_F) + 1,
                        FirstBoardStart.y + 1, true, 3, new Vector2(5, 1));
            } else if (i > 2 && i <= 6) {
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/two/twoshipModel.png",
                        "core/assets/oneship/two/twoshipModelwaves.png", FirstBoardStart.x + (i * BOX_WIDTH_F) + 1,
                        FirstBoardStart.y + 1, true, 2, new Vector2(5, 1));
            } else
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/one/oneshipModel.png",
                        "core/assets/oneship/one/oneshipModelwaves.png", FirstBoardStart.x + (i * BOX_WIDTH_F) + 1,
                        FirstBoardStart.y + 1, true, 1, new Vector2(5, 1));
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
                // System.out.println(activeSpriteDrag);
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
    // Stage 3 later
}
