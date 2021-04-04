package Arkanoid.Logic.models;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Task {
    private int age = 0, deadLine = 0;
    private Timer timer;
    private Runnable runnable;

    public Task(Runnable runnable) {
        this.runnable = runnable;
        timer = new Timer();
    }

    public void renewTask(int deadLine) {
        disable();
        this.age = 0;
        this.deadLine = deadLine;

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                taskFunction();
            }
        }, 0, 10);
    }

    public void taskFunction() {
        age += 10;
        if (age >= deadLine) {
            runnable.run();
            disable();
        }
    }

    public boolean isAlive() {
        return age < deadLine;
    }

    public void pause() {
        timer.cancel();
        timer.purge();
    }

    public void resume() {
        if (isAlive()) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    taskFunction();
                }
            }, 0, 10);
        }
    }

    public void disable() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        age = Integer.MAX_VALUE;
    }
}
