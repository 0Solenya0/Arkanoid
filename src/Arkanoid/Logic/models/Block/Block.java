package Arkanoid.Logic.models.Block;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Model;
import Arkanoid.Logic.models.Prize;
import Arkanoid.Logic.models.Savable;

import java.io.File;
import java.util.Scanner;

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
        int type = 0;
        if (this instanceof FlashingBlock)
            type = 1;
        else if (this instanceof InvisibleBlock)
            type = 2;
        else if (this instanceof GlassBlock)
            type = 3;
        else if (this instanceof PrizeBlock)
            type = 4;
        else if (this instanceof WoddenBlock)
            type = 5;
        String res = type + "\n" +
                x + " " + y + "\n" +
                isHitable + "\n" +
                width + " " + height + "\n";
        return res;
    }

    public static Block loadBlock(File path) {
        String str = Savable.loadSerializedString(path);
        Scanner scanner = new Scanner(str);
        int type = scanner.nextInt();
        switch (type) {
            case 1:
                FlashingBlock flashingBlock = new FlashingBlock(0, 0);
                flashingBlock.load(path);
                return flashingBlock;
            case 2:
                InvisibleBlock invisibleBlock = new InvisibleBlock(0, 0);
                invisibleBlock.load(path);
                return invisibleBlock;
            case 3:
                GlassBlock glassBlock = new GlassBlock(0, 0);
                glassBlock.load(path);
                return glassBlock;
            case 4:
                PrizeBlock prizeBlock = new PrizeBlock(0, 0, Prize.PrizeType.RANDOM);
                prizeBlock.load(path);
                return prizeBlock;
            case 5:
                WoddenBlock woddenBlock = new WoddenBlock(0, 0);
                woddenBlock.load(path);
                return woddenBlock;
        }
        return null;
    }

    @Override
    public void deserialize(Scanner scanner) {
        int t = scanner.nextInt();
        x = scanner.nextInt();
        y = scanner.nextInt();
        isHitable = scanner.nextBoolean();
        width = scanner.nextInt();
        height = scanner.nextInt();
    }

    public enum Events {
        DELETE,
        ADDPRIZE
    }
}
