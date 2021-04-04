package Arkanoid.Logic.models;

import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.panels.GamePanel;

public class Board extends Model {
    public static int defaultLength = 120;

    final private Task taskNormalizeLength = new Task(this::setLengthToNormal);
    final private Task taskNormalizeConfuse = new Task(this::setConfusedToNormal);

    private int xSpeed = 8;
    private int x, length;
    boolean isConfused;

    public Board() {
        x = 0;
        length = defaultLength;
    }

    public void resume() {
        taskNormalizeLength.resume();
        taskNormalizeConfuse.resume();
    }

    public void pause() {
        taskNormalizeConfuse.pause();
        taskNormalizeLength.pause();
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

    public void Confuse() {
        isConfused = true;
        taskNormalizeConfuse.renewTask(5000);
    }

    public void usePrize(Prize.PrizeType prize) {
        int mid = x + length / 2;
        if (prize == Prize.PrizeType.EXPANDBOARD) {
            length = defaultLength * 4 / 3;
            x = mid - length / 2;
            normalize();
        }
        if (prize == Prize.PrizeType.SHRINKBOARD) {
            length = defaultLength * 2 / 3;
            x = mid - length / 2;
            normalize();
        }
        taskNormalizeLength.renewTask(6000);
    }

    public void setConfusedToNormal() {
        isConfused = false;
    }

    public void setLengthToNormal() {
        int mid = x + length / 2;
        length = defaultLength;
        x = mid - length / 2;
        normalize();
    }

    public int getLength() {
        return length;
    }

    public int getY() {
        return GamePanel.defaultBoardH;
    }

    public int getX() {
        return x;
    }
}
