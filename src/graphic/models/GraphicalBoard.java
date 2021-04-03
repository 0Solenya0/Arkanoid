package graphic.models;

import java.awt.*;

public class GraphicalBoard extends GraphicalModel {
    private int x, y;

    public GraphicalBoard(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int x) {
        this.x = x;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.drawRect(x, y,100,20);
        g.fillRect(x, y, 100, 20);
    }
}
