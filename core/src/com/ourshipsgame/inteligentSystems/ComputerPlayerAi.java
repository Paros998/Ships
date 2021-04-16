package com.ourshipsgame.inteligentSystems;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.ourshipsgame.game.GameObject;

import org.lwjgl.util.vector.Vector2f;

public class ComputerPlayerAi {
    private Random random;
    private boolean hittedNdestroyed;
    private boolean missed;
    private boolean hitted;
    private Vector2[] LastHitPositions;
    private Vector2 TargetPos;
    private int[] direction;
    private float attackTime = 1f;
    private float actualTime;
    private int index, dirIndex;
    private int[][] SecondPlayerShotsDone;

    /**
     * @return float
     */
    public float getX() {
        return TargetPos.x;
    }

    /**
     * @return float
     */
    public float getY() {
        return TargetPos.y;
    }

    /**
     * @param missed
     * @param hitted
     * @param destroyed
     * @param ShotsDone
     * @param ships
     * @param firstboard
     * @param shipsAmmount
     */
    public void update(boolean missed, boolean hitted, boolean destroyed, int[][] ShotsDone, GameObject[] ships,
            Vector2f firstboard, int shipsAmmount) {
        this.missed = missed;
        this.hitted = hitted;
        this.hittedNdestroyed = destroyed;
        this.SecondPlayerShotsDone = ShotsDone;
        int i = 0;
        while (i < shipsAmmount) {
            if (ships[i].isDestroyed()) {
                int size, rotation, xpos, ypos;

                rotation = ships[i].getRotation();
                size = ships[i].getShipSize();
                xpos = (int) ((ships[i].x - firstboard.x) / 64.0f);
                ypos = (int) ((ships[i].y - firstboard.y) / 64.0f);

                if (rotation % 2 == 0) {
                    if (size == 3) {
                        SecondPlayerShotsDone[xpos][ypos] = 2;
                        SecondPlayerShotsDone[xpos][ypos + 1] = 2;
                        SecondPlayerShotsDone[xpos][ypos + 2] = 2;
                    } else if (size == 2) {
                        SecondPlayerShotsDone[xpos][ypos] = 2;
                        SecondPlayerShotsDone[xpos][ypos + 1] = 2;
                    } else {
                        SecondPlayerShotsDone[xpos][ypos] = 2;
                    }
                } else {
                    if (size == 3) {
                        SecondPlayerShotsDone[xpos][ypos] = 2;
                        SecondPlayerShotsDone[xpos + 1][ypos] = 2;
                        SecondPlayerShotsDone[xpos + 2][ypos] = 2;
                    } else if (size == 2) {
                        SecondPlayerShotsDone[xpos][ypos] = 2;
                        SecondPlayerShotsDone[xpos + 1][ypos] = 2;
                    } else {
                        SecondPlayerShotsDone[xpos][ypos] = 2;
                    }
                }
            }
            i++;
        }
    }

    public ComputerPlayerAi(int[][] ShotsDone) {

        this.SecondPlayerShotsDone = ShotsDone;
        LastHitPositions = new Vector2[8];
        direction = new int[4];
        TargetPos = new Vector2();
        random = new Random();
        missed = true;
        index = 0;
    }

    /**
     * @param deltaTime
     * @return boolean
     */
    public boolean attackEnemy(float deltaTime) {
        actualTime += deltaTime;
        if (actualTime < attackTime) {
            return false;
        } else {
            actualTime = 0f;
            if (hittedNdestroyed)
                HittedNdestroyed();
            else if (hitted)
                HittedAndNotDestroyed(false);
            else if (missed)
                Missed();
            return true;
        }
    }

    private void Missed() {
        boolean hitsLeft = false;
        for (int j = 0; j < 10; j++)
            for (int k = 0; k < 10; k++)
                if (SecondPlayerShotsDone[j][k] == 1) {
                    hitsLeft = true;
                    for (int i = 0; i <= index; i++)
                        LastHitPositions[i] = null;
                    LastHitPositions[0] = new Vector2();
                    for (int i = 0; i < 4; i++)
                        direction[i] = 0;
                    index = 0;
                    dirIndex = 0;
                    LastHitPositions[index] = new Vector2(j, k);
                    if (j > 0 && j < 9) {
                        if (SecondPlayerShotsDone[j + 1][k] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j + 1, k);
                            direction[dirIndex] = 1;
                            dirIndex++;
                        } else if (SecondPlayerShotsDone[j - 1][k] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j - 1, k);
                            direction[dirIndex] = 3;
                            dirIndex++;
                        }
                    }
                    if (k > 0 && k < 9) {
                        if (SecondPlayerShotsDone[j][k + 1] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j, k + 1);
                            direction[dirIndex] = 0;
                            dirIndex++;
                        } else if (SecondPlayerShotsDone[j][k - 1] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j, k - 1);
                            direction[dirIndex] = 2;
                            dirIndex++;
                        }
                    }

                }
        if (!hitsLeft) {
            int x, y;
            x = random.nextInt(10);
            y = random.nextInt(10);
            while (SecondPlayerShotsDone[x][y] != 0) {
                x = random.nextInt(10);
                y = random.nextInt(10);
            }
            TargetPos.set(x, y);
        } else
            HittedAndNotDestroyed(hitsLeft);
    }

    private void HittedNdestroyed() {
        boolean hitsLeft = false;
        for (int j = 0; j < 10; j++)
            for (int k = 0; k < 10; k++)
                if (SecondPlayerShotsDone[j][k] == 1) {
                    hitsLeft = true;
                    for (int i = 0; i <= index; i++)
                        LastHitPositions[i] = null;
                    LastHitPositions[0] = new Vector2();
                    for (int i = 0; i < 4; i++)
                        direction[i] = 0;
                    index = 0;
                    dirIndex = 0;
                    LastHitPositions[index] = new Vector2(j, k);
                    if (j > 0 && j < 9) {
                        if (SecondPlayerShotsDone[j + 1][k] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j + 1, k);
                            direction[dirIndex] = 1;
                            dirIndex++;
                        } else if (SecondPlayerShotsDone[j - 1][k] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j - 1, k);
                            direction[dirIndex] = 3;
                            dirIndex++;
                        }
                    }
                    if (k > 0 && k < 9) {
                        if (SecondPlayerShotsDone[j][k + 1] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j, k + 1);
                            direction[dirIndex] = 0;
                            dirIndex++;
                        } else if (SecondPlayerShotsDone[j][k - 1] == 1) {
                            index++;
                            LastHitPositions[index] = new Vector2(j, k - 1);
                            direction[dirIndex] = 2;
                            dirIndex++;
                        }
                    }

                }
        if (!hitsLeft) {
            for (int i = 0; i <= index; i++)
                LastHitPositions[i] = null;
            LastHitPositions[0] = new Vector2();
            for (int i = 0; i < 4; i++)
                direction[i] = 0;
            index = 0;
            dirIndex = 0;
            Missed();
        } else
            HittedAndNotDestroyed(hitsLeft);
    }

    /**
     * @param foundAnotherhit
     */
    private void HittedAndNotDestroyed(boolean foundAnotherhit) {
        Vector2 NewPos;
        if (!foundAnotherhit) {
            float x = TargetPos.x;
            float y = TargetPos.y;
            NewPos = new Vector2(x, y);
            LastHitPositions[index] = new Vector2(x, y);
            index++;
        } else {
            float x = LastHitPositions[index].x;
            float y = LastHitPositions[index].y;
            NewPos = new Vector2(x, y);
            index++;
        }

        if (index == 1) {
            if (dirIndex == 0) {
                direction[dirIndex] = findNextSpot(1);
                if (direction[dirIndex] == 0) {
                    NewPos.y++;
                } else if (direction[dirIndex] == 1) {
                    NewPos.x++;
                } else if (direction[dirIndex] == 2) {
                    NewPos.y--;
                } else {
                    NewPos.x--;
                }
                dirIndex++;
            }
        } else if (index >= 2) {
            int dir = findNextSpot(2);
            if (dir >= 0) {
                direction[dirIndex] = dir;
                if (LastHitPositions[index - 2].x == LastHitPositions[index - 1].x) {
                    // shoting up
                    if (direction[dirIndex] % 2 == 0) {
                        // finding point up
                        if (LastHitPositions[index - 2].y < LastHitPositions[index - 1].y)
                            NewPos.y++;
                        else
                            NewPos.y = LastHitPositions[index - 2].y + 1;
                    } else if (direction[dirIndex] % 2 == 1) {
                        // finding point down
                        if (LastHitPositions[index - 2].y < LastHitPositions[index - 1].y)
                            NewPos.y = LastHitPositions[index - 2].y - 1;
                        else
                            NewPos.y--;
                    }
                } else if (LastHitPositions[index - 2].y == LastHitPositions[index - 1].y) {
                    if (direction[dirIndex] % 2 == 0) {
                        // right
                        if (LastHitPositions[index - 2].x < LastHitPositions[index - 1].x)
                            NewPos.x++;
                        else
                            NewPos.x = LastHitPositions[index - 2].x + 1;
                    } else if (direction[dirIndex] % 2 == 1) {
                        // left
                        if (LastHitPositions[index - 2].x < LastHitPositions[index - 1].x)
                            NewPos.x = LastHitPositions[index - 2].x - 1;
                        else
                            NewPos.x++;
                    }
                }
                dirIndex++;
            } else if (dir == -1 || dir == -2) {
                if (dir == -1)
                    dir = findNextSpot(-1);
                else if (dir == -2)
                    dir = findNextSpot(-2);
                direction[dirIndex] = dir;

                if (direction[dirIndex] == 0) {
                    NewPos.y++;
                } else if (direction[dirIndex] == 1) {
                    NewPos.x++;
                } else if (direction[dirIndex] == 2) {
                    NewPos.y--;
                } else {
                    NewPos.x--;
                }

                dirIndex++;
            }
        }
        TargetPos = NewPos;
    }

    /**
     * @param numberofHits
     * @return int
     */
    private int findNextSpot(int numberofHits) {
        int val = 0;
        Random ran = new Random();
        if (numberofHits == 1 || numberofHits == -1 || numberofHits == -2) {
            int dir = ran.nextInt(4);
            float x, y;
            x = y = 0;
            try {
                x = LastHitPositions[index - 1].x;
                y = LastHitPositions[index - 1].y;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index not 1 XD " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            Vector2 tmp = new Vector2(x, y);
            while (tmp.x > 9 || tmp.x < 0 || tmp.y > 9 || tmp.y < 0
                    || SecondPlayerShotsDone[(int) tmp.x][(int) tmp.y] != 0) {
                tmp.x = x;
                tmp.y = y;
                dir = ran.nextInt(4);
                if (dir == 0)
                    tmp.y++;
                else if (dir == 1)
                    tmp.x++;
                else if (dir == 2) {
                    tmp.y--;
                } else
                    tmp.x--;
            }
            val = dir;
        } else if (numberofHits >= 2) {
            int dir = ran.nextInt(2);
            float x, y, x2, y2;
            x = y = x2 = y2 = 0;
            try {
                x = LastHitPositions[index - 1].x;
                y = LastHitPositions[index - 1].y;
                x2 = LastHitPositions[index - 2].x;
                y2 = LastHitPositions[index - 2].y;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Index not 2 XD " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            Vector2 tmp = new Vector2(x, y);
            Vector2 tmp2 = new Vector2(x2, y2);
            Vector2 tmp3 = new Vector2();
            boolean cond1, cond2, cond3, cond4, cond11, cond22, cond33, cond44, allTrue;
            cond1 = cond2 = cond3 = cond4 = cond11 = cond22 = cond33 = cond44 = false;
            allTrue = true;
            if (tmp.x == tmp2.x) {
                tmp3.x = tmp2.x;
                if (dir == 0)
                    tmp3.y = tmp.y > tmp2.y ? tmp.y + 1 : tmp2.y + 1;
                else
                    tmp3.y = tmp.y < tmp2.y ? tmp.y - 1 : tmp2.y - 1;

                if (tmp.y - 1 < 0)
                    cond1 = true;
                if (tmp2.y - 1 < 0)
                    cond2 = true;
                if (tmp.y + 1 > 9)
                    cond3 = true;
                if (tmp2.y + 1 > 9)
                    cond4 = true;

                try {
                    if (!cond1)
                        cond11 = (SecondPlayerShotsDone[(int) tmp3.x][(int) tmp.y - 1] != 0);
                    if (!cond2)
                        cond22 = (SecondPlayerShotsDone[(int) tmp3.x][(int) tmp2.y - 1] != 0);
                    if (!cond3)
                        cond33 = (SecondPlayerShotsDone[(int) tmp3.x][(int) tmp.y + 1] != 0);
                    if (!cond4)
                        cond44 = (SecondPlayerShotsDone[(int) tmp3.x][(int) tmp2.y + 1] != 0);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sprawdzanie przy x = x wywaliło " + e.getLocalizedMessage());
                    e.printStackTrace();
                }

                if (!cond1)
                    allTrue = (allTrue && cond11);
                if (!cond2)
                    allTrue = (allTrue && cond22);
                if (!cond3)
                    allTrue = (allTrue && cond33);
                if (!cond4)
                    allTrue = (allTrue && cond44);

                if (allTrue)
                    return -1; // If there is no free pos in Y axis to shoot
                try {
                    do {
                        dir = ran.nextInt(2);
                        if (dir == 0)
                            tmp3.y = tmp.y > tmp2.y ? tmp.y + 1 : tmp2.y + 1;
                        else
                            tmp3.y = tmp.y < tmp2.y ? tmp.y - 1 : tmp2.y - 1;
                        while (tmp3.y > 9 || tmp3.y < 0) {
                            dir = ran.nextInt(2);
                            if (dir == 0)
                                tmp3.y = tmp.y > tmp2.y ? tmp.y + 1 : tmp2.y + 1;
                            else
                                tmp3.y = tmp.y < tmp2.y ? tmp.y - 1 : tmp2.y - 1;
                        }
                    } while (SecondPlayerShotsDone[(int) tmp3.x][(int) tmp3.y] != 0);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sprawdzanie przy while gdzie x = x wywaliło " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            } else if (tmp.y == tmp2.y) {
                tmp3.y = tmp2.y;
                if (dir == 0)
                    tmp3.x = tmp.x > tmp2.x ? tmp.x + 1 : tmp2.x + 1;
                else
                    tmp3.x = tmp.x < tmp2.x ? tmp.x - 1 : tmp2.x - 1;

                if (tmp.x + 1 > 9)
                    cond1 = true;
                if (tmp2.x + 1 > 9)
                    cond2 = true;
                if (tmp.x - 1 < 0)
                    cond3 = true;
                if (tmp2.x - 1 < 0)
                    cond4 = true;
                try {
                    if (!cond1)
                        cond11 = (SecondPlayerShotsDone[(int) tmp.x + 1][(int) tmp3.y] != 0);
                    if (!cond2)
                        cond22 = (SecondPlayerShotsDone[(int) tmp2.x + 1][(int) tmp3.y] != 0);
                    if (!cond3)
                        cond33 = (SecondPlayerShotsDone[(int) tmp2.x - 1][(int) tmp3.y] != 0);
                    if (!cond4)
                        cond44 = (SecondPlayerShotsDone[(int) tmp.x - 1][(int) tmp3.y] != 0);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sprawdzanie przy y = y wywaliło " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
                if (!cond1)
                    allTrue = (allTrue && cond11);
                if (!cond2)
                    allTrue = (allTrue && cond22);
                if (!cond3)
                    allTrue = (allTrue && cond33);
                if (!cond4)
                    allTrue = (allTrue && cond44);

                if (allTrue)
                    return -2; // If there is no free pos in X axis to shoot
                try {
                    do {
                        dir = ran.nextInt(2);
                        if (dir == 0)
                            tmp3.x = tmp.x > tmp2.x ? tmp.x + 1 : tmp2.x + 1;
                        else
                            tmp3.x = tmp.x < tmp2.x ? tmp.x - 1 : tmp2.x - 1;
                        while (tmp3.x > 9 || tmp3.x < 0) {
                            dir = ran.nextInt(2);
                            if (dir == 0)
                                tmp3.x = tmp.x > tmp2.x ? tmp.x + 1 : tmp2.x + 1;
                            else
                                tmp3.x = tmp.x < tmp2.x ? tmp.x - 1 : tmp2.x - 1;
                        }
                    } while (SecondPlayerShotsDone[(int) tmp3.x][(int) tmp3.y] != 0);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Sprawdzanie przy while gdzie y = y wywaliło " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
            val = dir;
        }
        return val;
    }
}
