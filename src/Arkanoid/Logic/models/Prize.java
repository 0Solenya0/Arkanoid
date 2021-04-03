package Arkanoid.Logic.models;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.graphic.GraphicalAgent;
import Arkanoid.graphic.panels.GamePanel;

import java.util.Timer;
import java.util.TimerTask;

public class Prize extends Model {
    public static final int defaultWidth = 10, defaultHeight = 10;

    Timer timer;
    private int x, y, w = defaultWidth, h = defaultHeight;
    private PrizeType type;
    public Listener listener;

    public Prize(int x, int y, PrizeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
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

    public void fall(GameState state) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                y += 2;
                if (y > GamePanel.defaultBoardH) {
                    if (state.getBoard().getX() < x + w && x < state.getBoard().getX() + state.getBoard().getLength())
                        state.usePrize(type);
                    delete();
                }
            }
        }, 0, 1000 / GraphicalAgent.fps);
    }

    public void delete() {
        timer.cancel();
        timer.purge();
        if (listener != null)
            listener.listen(Events.DELETE.toString());
    }

    public enum PrizeType {
        SHRINKBOARD,
        EXPANDBOARD
    }

    public enum Events {
        DELETE
    }
}
