package Arkanoid.graphic.models;

import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Model;

import java.awt.*;

public class GraphicalBoard extends GraphicalModel {
    private int x, length;
    private final int y;

    public GraphicalBoard(int x, int y) {
        this.x = x;
        this.y = y;
        this.length = Board.defaultLength;
    }

    public void updateState(Model model) {
        Board board = (Board) model;
        this.x = board.getX();
        this.length = board.getLength();
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRect(x, y, length,20);
        g.fillRect(x, y, length, 20);
    }
}
