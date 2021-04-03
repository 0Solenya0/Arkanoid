package graphic.models;

import Logic.models.Board.Board;

import java.awt.*;

public class GraphicalBoard extends GraphicalModel {
    private int x;
    private final int y;

    public GraphicalBoard(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateState(Board board) {
        this.x = board.getX();
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRect(x, y,100,20);
        g.fillRect(x, y, 100, 20);
    }
}
