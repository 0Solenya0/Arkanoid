package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Model;
import Arkanoid.Logic.models.Prize;

import java.awt.*;

public class GraphicalPrize extends GraphicalModel {
    private Prize prize;

    public GraphicalPrize() {

    }

    public void updateState(Model model) {
        prize = (Prize) model;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.drawRect(prize.getX(), prize.getY(), prize.getW(), prize.getH());
        g.fillRect(prize.getX(), prize.getY(), prize.getW(), prize.getH());
    }
}
