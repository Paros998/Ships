package com.ourshipsgame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.ourshipsgame.handlers.Constant;

import org.lwjgl.util.vector.Vector2f;

public abstract class GameEngine extends ScreenAdapter implements Constant {
    // Important vars
    protected int[][] FirstBoardShipsPos = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] SecondBoardShipsPos = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] FirstPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];
    protected int[][] SecondPlayerShotsDone = new int[BOX_X_AXIS_NUMBER][BOX_Y_AXIS_NUMBER];

    protected Vector2f FirstBoardStart = new Vector2f(83, 223);
    protected Vector2f SecondBoardStart = new Vector2f(1204, 223);
    // Other vars
    protected int threeBoxShips = 3;
    protected int twoBoxShips = 4;
    protected int oneBoxShips = 5;
    protected int sum = threeBoxShips + twoBoxShips + oneBoxShips;
    // Important Objects
    protected GameObject FirstBoardShipsSprites[] = new GameObject[sum];
    protected GameObject SecondBoardShipsSprites[] = new GameObject[sum];

    protected boolean preparation(boolean computerEnemy) {
        boolean done = false;
        for (int i = 0; i < BOX_X_AXIS_NUMBER; i++)
            for (int j = 0; j < BOX_Y_AXIS_NUMBER; j++) {
                FirstBoardShipsPos[i][j] = SecondBoardShipsPos[i][j] = FirstPlayerShotsDone[i][j] = SecondPlayerShotsDone[i][j] = 0;
            }

        for (int i = 0; i < sum; i++) {
            if (i <= 2) {
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/threeshipModel.png",
                        FirstBoardStart.x + (i * BOX_WIDTH_F), GAME_HEIGHT_F - FirstBoardStart.y, true);
            }
            if (i > 2 && i <= 6) {
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/threeshipModel.png",
                        FirstBoardStart.x + (i * BOX_WIDTH_F), GAME_HEIGHT_F - FirstBoardStart.y, true);
            } else
                FirstBoardShipsSprites[i] = new GameObject("core/assets/oneship/threeshipModel.png",
                        FirstBoardStart.x + (i * BOX_WIDTH_F), GAME_HEIGHT_F - FirstBoardStart.y, true);
        }

        DragAndDrop dnd = new DragAndDrop();

        return done;
    }

    protected boolean gameLoop() {
        return true;
    }

    protected boolean finalStage() {
        return true;
    }
}
