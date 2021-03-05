package com.ourshipsgame;

import com.badlogic.gdx.ScreenAdapter;

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
    // more other vars
    protected int activeSpriteDrag = 1;

}
