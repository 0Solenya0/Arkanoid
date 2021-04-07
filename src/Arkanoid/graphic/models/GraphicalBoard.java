package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Model;
import Arkanoid.graphic.util.ImageLoader;

import java.awt.*;

public class GraphicalBoard extends GraphicalModel {

    Board board;
    private final int y;

    public GraphicalBoard(int y) {
        this.y = y;
    }

    public void updateState(Model model) {
        board = (Board) model;
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawImage(ImageLoader.getImage("red_board.png", board.getLength(), 20), board.getX(), y, null);
    }
}
