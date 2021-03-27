package com.ourshipsgame.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
            for (int i = 0; i < numberOfShips; i++)
                this.BoardShipsPos[i] = new Vector2();
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
                BoardShipsPos[i] = new Vector2(xPos, yPos);
                switch (actualShip.size) {
                case 3:
                    if (rotation % 2 == 0) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos][yPos + 1] = 1;
                        ShipsPlaced[xPos][yPos + 2] = 1;
                    } else if (rotation % 2 == 1) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos + 1][yPos] = 1;
                        ShipsPlaced[xPos + 2][yPos] = 1;
                    }
                    break;
                case 2:
                    if (rotation % 2 == 0) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos][yPos + 1] = 1;
                    } else if (rotation % 2 == 1) {
                        ShipsPlaced[xPos][yPos] = 1;
                        ShipsPlaced[xPos + 1][yPos] = 1;
                    }
                    break;
                case 1:
                    ShipsPlaced[xPos][yPos] = 1;
                    break;
                }
            }
        }

        protected void hitShip(int xPos, int yPos) {
            GameObject actualShip;
            switch (BoardNumber) {
            case 1:
                for (int i = 0; i < sum; i++) {
                    actualShip = FirstBoardShipsSprites[i];
                    if (actualShip.rotation % 2 == 0) {
                        if (actualShip.size == 3) {
                            if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                            else if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y + 1 == yPos)
                                actualShip.destroyElement();
                            else if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y + 2 == yPos)
                                actualShip.destroyElement();
                        } else if (actualShip.size == 2) {
                            if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                            else if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y + 1 == yPos)
                                actualShip.destroyElement();
                        } else {
                            if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                        }
                    } else {
                        if (actualShip.size == 3) {
                            if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                            else if (BoardShipsPos[i].x + 1 == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                            else if (BoardShipsPos[i].x + 2 == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                        } else if (actualShip.size == 2) {
                            if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                            else if (BoardShipsPos[i].x + 1 == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                        } else {
                            if (BoardShipsPos[i].x == xPos && BoardShipsPos[i].y == yPos)
                                actualShip.destroyElement();
                        }
                    }
                    actualShip.checkDestroyment();
                    if (actualShip.shipDestroyed) {
                        if (actualShip.size == 3)
                            actualShip.changeDestroyTexture(BigShipTextures[2]);
                        else if (actualShip.size == 2)
                            actualShip.changeDestroyTexture(MediumShipTextures[2]);
                        else
                            actualShip.changeDestroyTexture(SmallShipTextures[2]);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < sum; i++) {

                }

                break;
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
    protected Texture BigShipTextures[] = new Texture[3];
    protected Texture MediumShipTextures[] = new Texture[3];
    protected Texture SmallShipTextures[] = new Texture[3];
    protected Cursor[] crosshairs = new Cursor[3];
    protected Cursor cursor;
    protected Pixmap[] crosshairPixmaps = new Pixmap[3];
    // Particles[0] - shooting texture
    protected Texture Particles[] = new Texture[2];
    protected Vector2f FirstBoardStart = new Vector2f(8 * BOX_WIDTH_F * BoardBoxToTile,
            8 * BOX_HEIGHT_F * BoardBoxToTile);
    protected Vector2f SecondBoardStart = new Vector2f(32 * BOX_WIDTH_F * BoardBoxToTile,
            8 * BOX_HEIGHT_F * BoardBoxToTile);
    protected int gameHeight = GAME_HEIGHT;
    protected int gameWidth = GAME_WIDTH;
    protected float gameHeight_f = GAME_HEIGHT_F;
    protected float gameWidth_f = GAME_WIDTH_F;
    // Sounds and music
    protected Sound rotateSound;
    protected Sound[] ShootSounds = new Sound[12];
    protected Sound WaterExplosionSounds;
    protected Sound MetalExplosionSounds;
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
    protected boolean rotateEnabled = false;
    protected boolean shootingEnabled = false;
    protected boolean hitted = false;
    protected Vector2 hitPos = new Vector2();
    protected Vector2 missPos = new Vector2();

    // loading method
    protected void loadGameEngine(AssetManager manager) {
        // turrets and ships textures
        manager.load(internalPaths[0], Texture.class);
        manager.load(internalPaths[1], Texture.class);
        manager.load(internalPaths[2], Texture.class);
        manager.load(internalPaths[3], Texture.class);
        manager.load("core/assets/oneship/three/threeshipModel.png", Texture.class);
        manager.load("core/assets/oneship/three/threeshipModelwaves.png", Texture.class);
        manager.load("core/assets/oneship/three/threeshipModelDestroyed.png", Texture.class);
        manager.load("core/assets/oneship/two/twoshipModel.png", Texture.class);
        manager.load("core/assets/oneship/two/twoshipModelwaves.png", Texture.class);
        manager.load("core/assets/oneship/two/twoshipModelDestroyed.png", Texture.class);
        manager.load("core/assets/oneship/one/oneshipModel.png", Texture.class);
        manager.load("core/assets/oneship/one/oneshipModelwaves.png", Texture.class);
        manager.load("core/assets/oneship/one/oneshipModelDestroyed.png", Texture.class);
        // Turret rotation sound
        manager.load("core/assets/sounds/TurretRotation.mp3", Sound.class);
        // Shoot effect
        manager.load("core/assets/animations/boom3.png", Texture.class);
        // Shoot sounds
        manager.load("core/assets/sounds/shoot/DeathFlash.mp3", Sound.class);
        manager.load("core/assets/sounds/shoot/explode.wav", Sound.class);
        manager.load("core/assets/sounds/shoot/explodemini.wav", Sound.class);
        manager.load("core/assets/sounds/shoot/ExplosionMetal.wav", Sound.class);
        manager.load("core/assets/sounds/shoot/ExplosionMetalGverb.wav", Sound.class);
        manager.load("core/assets/sounds/shoot/GunShot.wav", Sound.class);
        manager.load("core/assets/sounds/shoot/GunShotGverb.wav", Sound.class);
        manager.load("core/assets/sounds/shoot/BangLong.ogg", Sound.class);
        manager.load("core/assets/sounds/shoot/BangMid.ogg", Sound.class);
        manager.load("core/assets/sounds/shoot/BangSmall.ogg", Sound.class);
        manager.load("core/assets/sounds/shoot/rock_breaking.mp3", Sound.class);
        manager.load("core/assets/sounds/shoot/synthetic_explosion_1.mp3", Sound.class);
        // Crosshairs
        manager.load("core/assets/cursors/crosshairRed.png", Pixmap.class);
        manager.load("core/assets/cursors/crosshairGreen.png", Pixmap.class);
        manager.load("core/assets/ui/ui.hud/cursors/test.png", Pixmap.class);
    }

    protected void loadHudAssets(AssetManager manager) {
        manager.load("core/assets/buttons/skins/rusty-robot/skin/rusty-robot-ui.json", Skin.class);
        manager.load("core/assets/ui/CustomTopBar.bmp", Texture.class);
        manager.load("core/assets/ui/ui.hud/ui/global/modern/gear.png", Texture.class);
        manager.load("core/assets/ui/ui.hud/ui/global/modern/gear-press.png", Texture.class);
    }

    // game methods below
    // Stage 1
    protected boolean preparation(boolean computerEnemy, AssetManager manager) {
        boolean done = false;

        for (int i = 0; i < 4; i++)
            turretTextures[i] = manager.get(internalPaths[i], Texture.class);
        BigShipTextures[0] = manager.get("core/assets/oneship/three/threeshipModel.png", Texture.class);
        BigShipTextures[1] = manager.get("core/assets/oneship/three/threeshipModelwaves.png", Texture.class);
        BigShipTextures[2] = manager.get("core/assets/oneship/three/threeshipModelDestroyed.png", Texture.class);
        MediumShipTextures[0] = manager.get("core/assets/oneship/two/twoshipModel.png", Texture.class);
        MediumShipTextures[1] = manager.get("core/assets/oneship/two/twoshipModelwaves.png", Texture.class);
        MediumShipTextures[2] = manager.get("core/assets/oneship/two/twoshipModelDestroyed.png", Texture.class);
        SmallShipTextures[0] = manager.get("core/assets/oneship/one/oneshipModel.png", Texture.class);
        SmallShipTextures[1] = manager.get("core/assets/oneship/one/oneshipModelwaves.png", Texture.class);
        SmallShipTextures[2] = manager.get("core/assets/oneship/one/oneshipModelDestroyed.png", Texture.class);
        rotateSound = manager.get("core/assets/sounds/TurretRotation.mp3", Sound.class);
        Particles[0] = manager.get("core/assets/animations/boom3.png", Texture.class);
        ShootSounds[0] = manager.get("core/assets/sounds/shoot/DeathFlash.mp3", Sound.class);
        ShootSounds[1] = manager.get("core/assets/sounds/shoot/explode.wav", Sound.class);
        ShootSounds[2] = manager.get("core/assets/sounds/shoot/explodemini.wav", Sound.class);
        ShootSounds[3] = manager.get("core/assets/sounds/shoot/ExplosionMetal.wav", Sound.class);
        ShootSounds[4] = manager.get("core/assets/sounds/shoot/ExplosionMetalGverb.wav", Sound.class);
        ShootSounds[5] = manager.get("core/assets/sounds/shoot/GunShot.wav", Sound.class);
        ShootSounds[6] = manager.get("core/assets/sounds/shoot/GunShotGverb.wav", Sound.class);
        ShootSounds[7] = manager.get("core/assets/sounds/shoot/BangLong.ogg", Sound.class);
        ShootSounds[8] = manager.get("core/assets/sounds/shoot/BangMid.ogg", Sound.class);
        ShootSounds[9] = manager.get("core/assets/sounds/shoot/BangSmall.ogg", Sound.class);
        ShootSounds[10] = manager.get("core/assets/sounds/shoot/rock_breaking.mp3", Sound.class);
        ShootSounds[11] = manager.get("core/assets/sounds/shoot/synthetic_explosion_1.mp3", Sound.class);
        crosshairPixmaps[0] = manager.get("core/assets/cursors/crosshairRed.png", Pixmap.class);
        crosshairPixmaps[1] = manager.get("core/assets/cursors/crosshairGreen.png", Pixmap.class);
        crosshairPixmaps[2] = manager.get("core/assets/ui/ui.hud/cursors/test.png", Pixmap.class);

        int xHot = crosshairPixmaps[0].getWidth() / 2;
        int yHot = crosshairPixmaps[0].getHeight() / 2;
        crosshairs[0] = Gdx.graphics.newCursor(crosshairPixmaps[0], xHot, yHot);
        xHot = crosshairPixmaps[1].getWidth() / 2;
        yHot = crosshairPixmaps[1].getHeight() / 2;
        crosshairs[1] = Gdx.graphics.newCursor(crosshairPixmaps[1], xHot, yHot);
        xHot = 0;
        yHot = 0;
        crosshairs[2] = Gdx.graphics.newCursor(crosshairPixmaps[2], xHot, yHot);
        crosshairPixmaps[0].dispose();
        crosshairPixmaps[1].dispose();
        crosshairPixmaps[2].dispose();

        Gdx.graphics.setCursor(crosshairs[2]);

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

                SecondBoardShipsSprites[i] = new GameObject(BigShipTextures[0], BigShipTextures[1], turretTextures,
                        SecondBoardStart.x + (i * BOX_WIDTH_F) + 1, SecondBoardStart.y - 191, true, 3,
                        new Vector2(5, 1));
            } else if (i > 2 && i <= 6) {
                FirstBoardShipsSprites[i] = new GameObject(MediumShipTextures[0], MediumShipTextures[1], turretTextures,
                        FirstBoardStart.x + (i * BOX_WIDTH_F) + 1, FirstBoardStart.y - 127, true, 2, new Vector2(5, 1));

                shootEffect[i] = new ShootParticleEffect(Particles[0], 0, 0, new Vector2(8, 8),
                        FirstBoardShipsSprites[i].turretsAmmount);

                SecondBoardShipsSprites[i] = new GameObject(MediumShipTextures[0], MediumShipTextures[1],
                        turretTextures, SecondBoardStart.x + (i * BOX_WIDTH_F) + 1, SecondBoardStart.y - 127, true, 2,
                        new Vector2(5, 1));
            } else {
                FirstBoardShipsSprites[i] = new GameObject(SmallShipTextures[0], SmallShipTextures[1], turretTextures,
                        FirstBoardStart.x + (i * BOX_WIDTH_F) + 1, FirstBoardStart.y - 63, true, 1, new Vector2(5, 1));

                shootEffect[i] = new ShootParticleEffect(Particles[0], 0, 0, new Vector2(8, 8),
                        FirstBoardShipsSprites[i].turretsAmmount);

                SecondBoardShipsSprites[i] = new GameObject(SmallShipTextures[0], SmallShipTextures[1], turretTextures,
                        SecondBoardStart.x + (i * BOX_WIDTH_F) + 1, SecondBoardStart.y - 63, true, 1,
                        new Vector2(5, 1));
            }
        }

        // Generating random positions on enemy board and placing ships while enemy is a
        // computer
        if (computerEnemy) {
            for (int i = 0; i < sum; i++)
                secondBoard.BoardShipsPos[i] = new Vector2();
            generateAndPlaceShipsOnBoard(2, false);
        }

        for (int i = 0; i < sum; i++) {
            GameObject actualShip = FirstBoardShipsSprites[i];
            if (isShipPlacedGood(actualShip, 1)) {
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

    protected void generateAndPlaceShipsOnBoard(int BoardNumber, boolean resetPos) {
        GameObject actualShip;
        Board board;
        if (BoardNumber == 1)
            board = firstBoard;
        else
            board = secondBoard;
        if (resetPos) {
            for (int i = 0; i <= 9; i++)
                for (int j = 0; j <= 9; j++)
                    board.ShipsPlaced[i][j] = 0;
            for (int k = 0; k < sum; k++)
                board.BoardShipsPos[k] = new Vector2();
        }
        Random generator = new Random();
        int xPos, yPos;
        for (int i = 0; i < sum; i++) {
            if (BoardNumber == 1)
                actualShip = FirstBoardShipsSprites[i];
            else
                actualShip = SecondBoardShipsSprites[i];

            int rotation = generator.nextInt(4);
            for (int j = 0; j < rotation; j++)
                actualShip.rotate90();
            xPos = generator.nextInt(10);
            yPos = generator.nextInt(10);

            switch (actualShip.size) {
            case 3:
                if (actualShip.rotation % 2 == 0) {
                    while ((yPos + 2) > 9 || board.ShipsPlaced[xPos][yPos] == 1
                            || board.ShipsPlaced[xPos][yPos + 1] == 1 || board.ShipsPlaced[xPos][yPos + 2] == 1) {
                        xPos = generator.nextInt(10);
                        yPos = generator.nextInt(10);
                    }
                    board.BoardShipsPos[i].set(xPos, yPos);
                    board.ShipsPlaced[xPos][yPos] = 1;
                    board.ShipsPlaced[xPos][yPos + 1] = 1;
                    board.ShipsPlaced[xPos][yPos + 2] = 1;
                } else {
                    while ((xPos + 2) > 9 || board.ShipsPlaced[xPos][yPos] == 1
                            || board.ShipsPlaced[xPos + 1][yPos] == 1 || board.ShipsPlaced[xPos + 2][yPos] == 1) {
                        xPos = generator.nextInt(10);
                        yPos = generator.nextInt(10);
                    }
                    board.BoardShipsPos[i].set(xPos, yPos);
                    board.ShipsPlaced[xPos][yPos] = 1;
                    board.ShipsPlaced[xPos + 1][yPos] = 1;
                    board.ShipsPlaced[xPos + 2][yPos] = 1;
                }
                break;
            case 2:
                if (actualShip.rotation % 2 == 0) {
                    while ((yPos + 1) > 9 || board.ShipsPlaced[xPos][yPos] == 1
                            || board.ShipsPlaced[xPos][yPos + 1] == 1) {
                        xPos = generator.nextInt(10);
                        yPos = generator.nextInt(10);
                    }
                    board.BoardShipsPos[i].set(xPos, yPos);
                    board.ShipsPlaced[xPos][yPos] = 1;
                    board.ShipsPlaced[xPos][yPos + 1] = 1;
                } else {
                    while ((xPos + 1) > 9 || board.ShipsPlaced[xPos][yPos] == 1
                            || board.ShipsPlaced[xPos + 1][yPos] == 1) {
                        xPos = generator.nextInt(10);
                        yPos = generator.nextInt(10);
                    }
                    board.BoardShipsPos[i].set(xPos, yPos);
                    board.ShipsPlaced[xPos][yPos] = 1;
                    board.ShipsPlaced[xPos + 1][yPos] = 1;
                }
                break;
            case 1:
                while (board.ShipsPlaced[xPos][yPos] == 1) {
                    xPos = generator.nextInt(10);
                    yPos = generator.nextInt(10);
                }
                board.BoardShipsPos[i].set(xPos, yPos);
                board.ShipsPlaced[xPos][yPos] = 1;
                break;
            }

            if (BoardNumber == 1)
                actualShip.setSpritePos(new Vector2(xPos * BOX_WIDTH_F + FirstBoardStart.x + 1,
                        yPos * BOX_HEIGHT_F + FirstBoardStart.y + 1));
            else
                actualShip.setSpritePos(new Vector2(xPos * BOX_WIDTH_F + SecondBoardStart.x + 1,
                        yPos * BOX_HEIGHT_F + SecondBoardStart.y + 1));
            actualShip.placeTurretsAccordingly();

        }
        if (BoardNumber == 1)
            for (int i = 0; i < sum; i++)
                FirstBoardShipsSprites[i].goodPlacement = isShipPlacedGood(FirstBoardShipsSprites[i], 1);
        else
            for (int i = 0; i < sum; i++)
                SecondBoardShipsSprites[i].goodPlacement = isShipPlacedGood(SecondBoardShipsSprites[i], 2);
        generator = null;
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

            if (isShipPlacedGood(actualShip, 1)) {
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

    protected boolean isShipPlacedGood(GameObject actualShip, int boardNumber) {
        // Checking if ship is dropped on good position not colliding with anything
        if (boardNumber == 1) {
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
        } else {
            Rectangle board = new Rectangle(SecondBoardStart.x, SecondBoardStart.y, BOX_WIDTH_F * BOX_X_AXIS_NUMBER,
                    BOX_HEIGHT_F * BOX_Y_AXIS_NUMBER);
            if (board.contains(actualShip.alligmentRectangle)) {
                for (int i = 0; i < sum; i++) {
                    if (actualShip == SecondBoardShipsSprites[i])
                        continue;
                    // Need change Work in progress But working great actually
                    boolean actualShipRotatedVertically = actualShip.rotation % 2 == 1;
                    boolean otherShipRotatedVertically = SecondBoardShipsSprites[i].rotation % 2 == 1;
                    //
                    if (actualShipRotatedVertically != otherShipRotatedVertically) {
                        if (actualShip.collide(SecondBoardShipsSprites[i].alligmentRectangle, true,
                                actualShipRotatedVertically))
                            return false;
                    } else {
                        if (actualShip.collide(SecondBoardShipsSprites[i].alligmentRectangle))
                            return false;
                    }
                }
                return true;
            } else
                return false;
        }
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
            if (isShipPlacedGood(FirstBoardShipsSprites[i], 1) == false)
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
            if (actualShip.shipDestroyed == true)
                continue;
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

    protected void checkHit(int turn, int xPos, int yPos) {
        int tx = xPos, ty = yPos;
        switch (turn) {
        case 0:
            if (secondBoard.ShipsPlaced[xPos][yPos] == 1) {
                hitted = true;
                xPos *= BOX_WIDTH_F;
                xPos += SecondBoardStart.x + 32;
                yPos *= BOX_HEIGHT_F;
                yPos += SecondBoardStart.y + 32;
                hitPos.set(xPos, yPos);
                // HitEffect.setPosition(hitPos);
            } else {
                hitted = false;
                xPos *= BOX_WIDTH_F;
                xPos += SecondBoardStart.x + 32;
                yPos *= BOX_HEIGHT_F;
                yPos += SecondBoardStart.y + 32;
                missPos.set(xPos, yPos);
                // MissEffect.setPosition(missPos);
            }
            if (hitted) {
                secondBoard.hitShip(tx, ty);
            }

            break;
        case 1:
            if (firstBoard.ShipsPlaced[xPos][yPos] == 1) {
                hitted = true;
                xPos *= BOX_WIDTH_F;
                xPos += FirstBoardStart.x + 32;
                yPos *= BOX_HEIGHT_F;
                yPos += FirstBoardStart.y + 32;
                hitPos.set(xPos, yPos);
                // HitEffect.setPosition(hitPos);
            } else {
                hitted = false;
                xPos *= BOX_WIDTH_F;
                xPos += FirstBoardStart.x + 32;
                yPos *= BOX_HEIGHT_F;
                yPos += FirstBoardStart.y + 32;
                missPos.set(xPos, yPos);
                // MissEffect.setPosition(missPos);
            }
            if (hitted) {
                firstBoard.hitShip(tx, ty);
            }
            break;
        }
    }

    protected boolean shoot(int screenX, int screenY, int turn) {
        if (shootingEnabled) {
            int xPos, yPos;
            switch (turn) {
            case 0:
                for (int i = 0; i < sum; i++) {
                    if (FirstBoardShipsSprites[i].shipDestroyed)
                        continue;
                    shootEffect[i].setPositions(FirstBoardShipsSprites[i]);
                }
                screenY = (int) gameHeight_f - screenY;
                xPos = (int) ((screenX - SecondBoardStart.x) / BOX_WIDTH_F);
                yPos = (int) ((screenY - SecondBoardStart.y) / BOX_HEIGHT_F);

                FirstPlayerShotsDone[xPos][yPos] = 1;
                shootingEnabled = false;
                Gdx.graphics.setCursor(crosshairs[0]);
                checkHit(turn, xPos, yPos);
                break;
            case 1:
                screenY = (int) gameHeight_f - screenY;
                xPos = (int) ((screenX - SecondBoardStart.x) / BOX_WIDTH_F);
                yPos = (int) ((screenY - SecondBoardStart.y) / BOX_HEIGHT_F);

                SecondPlayerShotsDone[xPos][yPos] = 1;
                shootingEnabled = false;
                Gdx.graphics.setCursor(crosshairs[0]);
                checkHit(turn, xPos, yPos);
                break;
            }
            return true;
        }
        return false;
    }

    protected void checkEnemyBoard(int screenX, int screenY) {
        Rectangle board = new Rectangle(SecondBoardStart.x, SecondBoardStart.y, BOX_WIDTH_F * BOX_X_AXIS_NUMBER,
                BOX_HEIGHT_F * BOX_Y_AXIS_NUMBER);
        screenY = (int) gameHeight_f - screenY;
        if (board.contains(screenX, screenY)) {
            int xPos = (int) ((screenX - SecondBoardStart.x) / BOX_WIDTH_F);
            int yPos = (int) ((screenY - SecondBoardStart.y) / BOX_HEIGHT_F);
            if (xPos == 10 || yPos == 10)
                return;
            if (FirstPlayerShotsDone[xPos][yPos] == 1) {
                shootingEnabled = false;
                rotateEnabled = true;
                Gdx.graphics.setCursor(crosshairs[0]);
            } else {
                shootingEnabled = true;
                rotateEnabled = true;
                Gdx.graphics.setCursor(crosshairs[1]);
            }

        } else {
            Gdx.graphics.setCursor(crosshairs[2]);
            shootingEnabled = false;
            rotateEnabled = false;
        }
    }
}
