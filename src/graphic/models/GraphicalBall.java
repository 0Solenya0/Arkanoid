package graphic.models;

import Logic.models.Ball;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class GraphicalBall extends GraphicalModel {

    private int x, y;
    Image img;

    public GraphicalBall(int x, int y) {
        try {
            img = ImageIO.read(new File("./resources/ball.png"));
            img = img.getScaledInstance(Ball.defaultW, Ball.defaultH, Image.SCALE_DEFAULT);
        }
        catch (Exception e) {
            System.out.println("wow");
        }
        this.x = x;
        this.y = y;
    }

    public void updateState(Ball ball) {
        this.x = (int) ball.getX();
        this.y = (int) ball.getY();
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawImage(img, x, y, null);
    }
}
