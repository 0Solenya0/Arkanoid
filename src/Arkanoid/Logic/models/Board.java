package Arkanoid.Logic.models;

import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.panels.GamePanel;

import java.util.Timer;
import java.util.TimerTask;

public class Board extends Model {
    public static int defaultLength = 120;

    Timer timer;
    private int xSpeed = 8;
    private int x, length;
    boolean isConfused;

    public Board() {
        timer = new Timer();
        x = 0;
        length = defaultLength;
    }

    public void normalize() {
        if (x + length > MainFrame.FRAME_WIDTH)
            x = MainFrame.FRAME_WIDTH - length;
        if (x < 0)
            x = 0;
    }

    public void move(Character c) {
        if (isConfused)
            xSpeed *= -1;
        switch (c) {
            case 'R':
                x += xSpeed;
                break;
            case 'L':
                x -= xSpeed;
                break;
        }
        if (isConfused)
            xSpeed *= -1;
        normalize();
    }

    public int getLength() {
        return length;
    }

    public void usePrize(Prize.PrizeType prize) {
        int mid = x + length / 2;
        length = defaultLength;
        x = mid - length / 2;
        timer.cancel();
        timer.purge();
        timer = new Timer();

        if (prize == Prize.PrizeType.EXPANDBOARD) {
            length = defaultLength * 4 / 3;
            x = mid - length / 2;
            normalize();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int mid2 = x + length / 2;
                    length = defaultLength;
                    x = mid2 - length / 2;
                    normalize();
                }
            }, 6000);
        }
        if (prize == Prize.PrizeType.SHRINKBOARD) {
            length = defaultLength * 2 / 3;
            x = mid - length / 2;
            normalize();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int mid2 = x + length / 2;
                    length = defaultLength;
                    x = mid2 - length / 2;
                    normalize();
                }
            }, 6000);
        }
    }

    public int getY() {
        return GamePanel.defaultBoardH;
    }

    public int getX() {
        return x;
    }

    public void setConfused(boolean confused) {
        isConfused = confused;
    }
}
