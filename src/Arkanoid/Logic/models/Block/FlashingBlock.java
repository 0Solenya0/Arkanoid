package Arkanoid.Logic.models.Block;

import Arkanoid.Logic.models.Task;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class FlashingBlock extends Block {
    public static final int flashPeriod = 1800;

    private final Task taskFlash = new Task(this::flash);

    public FlashingBlock(int x, int y) {
        super(x, y);
        taskFlash.renewTask(flashPeriod);
        taskFlash.pause();
    }

    public void flash() {
        isHitable = !isHitable;
        taskFlash.renewTask(flashPeriod);
        taskFlash.resume();
    }

    @Override
    public void pause() {
        taskFlash.pause();
    }

    @Override
    public void resume() {
        taskFlash.resume();
    }

    @Override
    public void ballHit() {
        taskFlash.pause();
        this.delete();
    }

    @Override
    public String serialize() {
        String res = getSerializeData() +
                taskFlash.serialize();
        return res;
    }

    @Override
    public void deserialize(Scanner scanner) {
        super.deserialize(scanner);
        taskFlash.deserialize(scanner);
    }
}
