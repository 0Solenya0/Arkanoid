package Logic.models;

import graphic.MainFrame;

public class Board {
    public static int defaultLength = 120;

    private int xSpeed = 8;
    private int x, length;
    boolean isConfused;

    public Board() {
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

    public void changeLength(int t) {
        int mid = x + length / 2;
        if (t <= -1)
            length = defaultLength * 2 / 3;
        if (t == 0)
            length = defaultLength;
        if (t >= 1)
            length = defaultLength * 4 / 3;
         x = mid - length / 2;
         normalize();
    }

    public int getX() {
        return x;
    }

    public void setConfused(boolean confused) {
        isConfused = confused;
    }
}
