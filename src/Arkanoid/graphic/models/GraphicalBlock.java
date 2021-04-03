package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Block.Block;
import Arkanoid.Logic.models.Model;

import java.awt.*;

public class GraphicalBlock extends GraphicalModel {

    int x, y, w, h;

    public GraphicalBlock() {

    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.drawRect(x, y, w, h);
        g.fillRect(x, y, w, h);
    }

    @Override
    public void updateState(Model model) {
        Block block = (Block) model;
        x = block.getX();
        y = block.getY();
        w = block.getWidth();
        h = block.getHeight();
    }
}
