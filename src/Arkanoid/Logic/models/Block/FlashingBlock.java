package Arkanoid.Logic.models.Block;

import java.util.Timer;
import java.util.TimerTask;

public class FlashingBlock extends Block {
    public static final int flashPeriod = 1800;

    Timer timer;

    public FlashingBlock(int x, int y) {
        super(x, y);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                isHitable = !isHitable;
            }
        }, 0, flashPeriod);
    }

    @Override
    public void ballHit() {
        this.delete();
        timer.cancel();
        timer.purge();
    }
}
