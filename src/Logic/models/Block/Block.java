package Logic.models.Block;

public abstract class Block {
    private int width = 50, height = 10;
    private int x, y;

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
}
