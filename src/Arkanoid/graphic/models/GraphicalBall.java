package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class GraphicalBall extends GraphicalModel {

    private int x, y;
    Image img;

    public GraphicalBall() {
        try {
            img = ImageIO.read(new File("./resources/ball.png"));
            img = img.getScaledInstance(Ball.defaultW, Ball.defaultH, Image.SCALE_DEFAULT);
        }
        catch (Exception e) {
            System.out.println("wow");
        }
    }

    public void updateState(Model model) {
        Ball ball = (Ball) model;
        this.x = (int) ball.getX();
        this.y = (int) ball.getY();
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawImage(img, x, y, null);
    }
}
