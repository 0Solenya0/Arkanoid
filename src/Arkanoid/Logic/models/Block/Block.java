package Arkanoid.Logic.models.Block;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Model;

public abstract class Block extends Model {
    public static final int YSHIFT = 13, defaultWidth = 50, defaultHeight = 20;
    private int width = 50, height = 20;
    private int x, y;
    public Listener listener;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void ballHit();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void delete() {
        if (listener != null)
            listener.listen(Events.DELETE.toString());
    }

    public void shiftDown() {
        this.y += height + YSHIFT;
    }

    public enum Events {
        DELETE
    }
}
