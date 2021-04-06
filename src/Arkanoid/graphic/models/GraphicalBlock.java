package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Block.*;
import Arkanoid.Logic.models.Model;

import java.awt.*;

public class GraphicalBlock extends GraphicalModel {

    private int x, y, w, h, t;
    private boolean isVisible;

    public GraphicalBlock() {

    }

    @Override
    public void paint(Graphics2D g) {
        if (isVisible) {
            if (t == 0)
                g.setColor(Color.ORANGE);
            else if (t == 1)
                g.setColor(Color.CYAN);
            else if (t == 2)
                return;
            else if (t == 3)
                g.setColor(Color.PINK);
            else if (t == 4)
                g.setColor(Color.GREEN);
            g.drawRect(x, y, w, h);
            g.fillRect(x, y, w, h);
        }
    }

    @Override
    public void updateState(Model model) {
        Block block = (Block) model;
        x = block.getX();
        y = block.getY();
        w = block.getWidth();
        h = block.getHeight();
        isVisible = block.isHitable();
        if (block instanceof WoddenBlock)
            t = 0;
        if (block instanceof GlassBlock)
            t = 1;
        if (block instanceof InvisibleBlock)
            t = 2;
        if (block instanceof FlashingBlock)
            t = 3;
        if (block instanceof PrizeBlock)
            t = 4;
    }
}
