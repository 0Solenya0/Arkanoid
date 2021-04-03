package Logic.models;

import graphic.MainFrame;
import graphic.panels.GamePanel;

public class Ball {
    public static int defaultW = 15, defaultH = 15;

    private double xSpeed = 1, ySpeed = 1;
    private int x, y, w, h, lives;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        w = defaultW;
        h = defaultH;
        lives = 3;
    }

    public void Bounce() {
        if (x < 0 && xSpeed < 0)
            xSpeed *= -1;
        if (x + w > MainFrame.FRAME_WIDTH && xSpeed > 0)
            xSpeed *= -1;
        if (y < 0 && ySpeed < 0)
            ySpeed *= -1;
        if (y + h > GamePanel.defaultBoardH + 20 && ySpeed > 0) {
            ySpeed *= -1;
            lives--;
        }
    }

    public void move(double ms) {
        x += ms * xSpeed / 10;
        y += ms * ySpeed / 10;
        Bounce();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
