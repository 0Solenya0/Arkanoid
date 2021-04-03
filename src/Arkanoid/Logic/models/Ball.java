package Arkanoid.Logic.models;

import Arkanoid.Logic.models.Block.Block;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.panels.GamePanel;

public class Ball extends Model {
    public static int defaultW = 15, defaultH = 15;

    private double xSpeed = 1, ySpeed = -1;
    private int prvx, prvy, x, y, w, h, lives;

    public void handleBlockCollision(Block b) {
        if ((b.getX() < x + w && x < b.getX() + b.getWidth()) && (b.getY() < y + h && y < b.getY() + b.getHeight())) {
            if (((ySpeed > 0 && y < b.getY() + b.getHeight()) || (ySpeed < 0 && y > b.getY())) &&
                    !(prvx > b.getX() + b.getWidth() || prvx + w < b.getX())) {
                ySpeed *= -1;
                b.ballHit();
            }
            else if ((xSpeed > 0 && x > b.getX()) || (xSpeed < 0 && x < b.getX() + b.getWidth())) {
                xSpeed *= -1;
                b.ballHit();
            }
        }
    }

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        prvx = x;
        prvy = y;
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
        prvx = x;
        prvy = y;
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
