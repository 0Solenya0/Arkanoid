package Arkanoid.Logic.models;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.graphic.GraphicalAgent;
import Arkanoid.graphic.panels.GamePanel;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Prize extends Model implements Savable<Prize> {
    public static final int defaultWidth = 20, defaultHeight = 20;

    Timer timer = new Timer();
    private int x, y, w = defaultWidth, h = defaultHeight;
    private PrizeType type;
    public Listener listener;

    public Prize(int x, int y, PrizeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public boolean collideWithBoard(Board board) {
        if (y > GamePanel.defaultBoardH) {
            delete();
            if (board.getX() < x + w && x < board.getX() + board.getLength())
                return true;
        }
        return false;
    }

    public void fall() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                y += 2;
            }
        }, 0, 30);
    }

    public void pause() {
        timer.cancel();
        timer.purge();
    }

    public void delete() {
        timer.cancel();
        timer.purge();
        if (listener != null)
            listener.listen(Events.DELETE.toString());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public PrizeType getType() {
        return type;
    }

    @Override
    public String serialize() {
        String res = x + " " + y + "\n" +
                w + " " + h + "\n" +
                type + "\n";
        return res;
    }

    @Override
    public void deserialize(Scanner serialized) {
        x = serialized.nextInt();
        y = serialized.nextInt();
        w = serialized.nextInt();
        h = serialized.nextInt();
        type = PrizeType.valueOf(serialized.next());
    }


    public enum PrizeType {
        SHRINKBOARD,
        EXPANDBOARD,
        CONFUSEBOARD,
        FASTBALL,
        SLOWBALL,
        MULTIBALL,
        FIREBALL,
        RANDOM
    }

    public enum Events {
        DELETE
    }
}
