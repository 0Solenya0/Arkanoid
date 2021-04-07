package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Model;
import Arkanoid.graphic.util.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class GraphicalBall extends GraphicalModel {

    Ball ball;

    public GraphicalBall() {
    }

    public void updateState(Model model) {
        ball = (Ball) model;
    }

    @Override
    public void paint(Graphics2D g) {
        if (ball.isOnFire())
            g.drawImage(ImageLoader.getImage("ball/gray_ball.png", Ball.defaultW, Ball.defaultH), ball.getX(), ball.getY(), null);
        else
            g.drawImage(ImageLoader.getImage("ball/blue_ball.png", Ball.defaultW, Ball.defaultH), ball.getX(), ball.getY(), null);
    }
}
