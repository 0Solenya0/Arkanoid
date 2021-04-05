package Arkanoid.Logic.models.Block;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Model;
import Arkanoid.Logic.models.Savable;

public abstract class Block extends Model implements Savable<Block> {
    public static final int YSHIFT = 13, defaultWidth = 50, defaultHeight = 20;
    protected int width = 50, height = 20;
    protected int x, y;
    protected boolean isHitable;
    public Listener listener;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        isHitable = true;
    }

    public boolean isHitable() {
        return isHitable;
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

    public void pause() { }

    public void resume() { }

    public String getSerializeData() {
        String res = x + " " + y + "\n" +
                isHitable + "\n" +
                width + " " + height + "\n";
        return res;
    }

    public enum Events {
        DELETE,
        ADDPRIZE
    }
}
