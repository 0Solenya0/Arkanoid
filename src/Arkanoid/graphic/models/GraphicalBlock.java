package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.*;
import Arkanoid.Logic.models.Model;
import Arkanoid.graphic.util.ImageLoader;

import java.awt.*;

public class GraphicalBlock extends GraphicalModel {

    Block block;

    public GraphicalBlock() {

    }

    @Override
    public void paint(Graphics2D g) {
        if (block.isHitable()) {
            Image img = null;
            int w = Block.defaultWidth, h = Block.defaultHeight;
            if (block instanceof FlashingBlock)
                img = ImageLoader.getImage("block/purple_block.png", w, h);
            else if (block instanceof GlassBlock)
                img = ImageLoader.getImage("block/gray_block.png", w, h);
            else if (block instanceof PrizeBlock)
                img = ImageLoader.getImage("block/green_block.png", w, h);
            else if (block instanceof WoddenBlock)
                img = ImageLoader.getImage("block/yellow_block.png", w, h);
            g.drawImage(img, block.getX(), block.getY(), null);
        }
    }

    @Override
    public void updateState(Model model) {
        block = (Block) model;
    }
}
