package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Model;
import Arkanoid.Logic.models.Prize;
import Arkanoid.graphic.util.ImageLoader;

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
        Image img = null;
        int w = prize.getW(), h = prize.getH();
        switch (prize.getType()) {
            case FIREBALL:
                img = ImageLoader.getImage("prize/red.png", w, h);
                break;
            case MULTIBALL:
                img = ImageLoader.getImage("prize/blue.png", w, h);
                break;
            case SLOWBALL:
            case FASTBALL:
                img = ImageLoader.getImage("prize/yellow.png", w, h);
                break;
            case SHRINKBOARD:
            case EXPANDBOARD:
                img = ImageLoader.getImage("prize/gray.png", w, h);
                break;
            case CONFUSEBOARD:
                img = ImageLoader.getImage("prize/purple.png", w, h);
                break;
            case RANDOM:
                img = ImageLoader.getImage("prize/green.png", w, h);
                break;
        }
        g.drawImage(img, prize.getX(), prize.getY(), null);

    }
}
