package Arkanoid.Logic.models;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.panels.GamePanel;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Ball extends Model implements Savable<Ball> {
    public static int defaultW = 15, defaultH = 15;

    private final Task taskNormalizeSpeed = new Task(this::setSpeedToNormal);
    private final Task taskNormalizeFire = new Task(this::setOnFireToNormal);

    private double baseMag = 1;
    Timer timerMovement = new Timer();
    private double xSpeed = Math.sqrt(2) / 2, ySpeed = -Math.sqrt(2) / 2, Mag = 1.4;
    private double prvx, prvy, x, y;
    public Listener listener;
    private int w, h;
    private boolean isOnFire;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        prvx = x;
        prvy = y;
        w = defaultW;
        h = defaultH;
    }

    public void updateBaseSpeed(int age) {
        baseMag = 1;
        while (age > 0) {
            age -= 15000;
            baseMag *= 1.1;
        }
    }

    public boolean bounceIfCollide(Block b) {
        if ((b.getX() < x + w && x < b.getX() + b.getWidth()) && (b.getY() < y + h && y < b.getY() + b.getHeight())) {
            if (((ySpeed > 0 && y < b.getY() + b.getHeight()) || (ySpeed < 0 && y > b.getY())) &&
                    !(prvx > b.getX() + b.getWidth() || prvx + w < b.getX())) {
                if (!isOnFire)
                    ySpeed *= -1;
                return true;
            }
            else if ((xSpeed > 0 && x > b.getX()) || (xSpeed < 0 && x < b.getX() + b.getWidth())) {
                if (!isOnFire)
                    xSpeed *= -1;
                return true;
            }
        }
        return false;
    }

    public void Bounce() {
        if (x < 0 && xSpeed < 0)
            xSpeed *= -1;
        if (x + w > MainFrame.FRAME_WIDTH && xSpeed > 0)
            xSpeed *= -1;
        if (y < 0 && ySpeed < 0)
            ySpeed *= -1;
    }

    public void move(double ms) {
        prvx = x;
        prvy = y;
        x += ms * xSpeed * baseMag * Mag / 10;
        y += ms * ySpeed * baseMag * Mag / 10;
        Bounce();
    }

    public void start() {
        timerMovement.cancel();
        timerMovement.purge();
        timerMovement = new Timer();
        timerMovement.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                move(20);
            }
        }, 0, 20);
        taskNormalizeFire.resume();
        taskNormalizeSpeed.resume();
    }

    public void pause() {
        timerMovement.cancel();
        timerMovement.purge();
        taskNormalizeSpeed.pause();
        taskNormalizeFire.pause();
    }

    public void usePrize(Prize.PrizeType prize) {
        if (prize.equals(Prize.PrizeType.FASTBALL)) {
            setSpeedToNormal();
            Mag *= 2;
            taskNormalizeSpeed.renewTask(6000);
        }
        if (prize.equals(Prize.PrizeType.SLOWBALL)) {
            setSpeedToNormal();
            Mag *= 0.5;
            taskNormalizeSpeed.renewTask(4000);
        }
        if (prize.equals(Prize.PrizeType.FIREBALL)) {
            isOnFire = true;
            taskNormalizeFire.renewTask(2000);
        }
    }

    public void setOnFireToNormal() {
        isOnFire = false;
    }

    public void setSpeedToNormal() {
        Mag = 1.4;
    }

    public void delete() {
        timerMovement.cancel();
        timerMovement.purge();
        taskNormalizeSpeed.disable();
        if (listener != null)
            listener.listen(Events.DELETE.toString());
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    @Override
    public String serialize() {
        String res = xSpeed + " " + ySpeed + "\n" +
                prvx + " " + prvy + "\n" +
                x + " " + y + "\n" +
                w + " " + h + "\n" +
                isOnFire + "\n" +
                taskNormalizeFire.serialize() + "\n" +
                taskNormalizeSpeed.serialize() + "\n" +
                Mag + "\n";
        return res;
    }

    @Override
    public void deserialize(Scanner serialized) {
        xSpeed = serialized.nextDouble();
        ySpeed = serialized.nextDouble();
        prvx = serialized.nextDouble();
        prvy = serialized.nextDouble();
        x = serialized.nextDouble();
        y = serialized.nextDouble();
        w = serialized.nextInt();
        h = serialized.nextInt();
        isOnFire = serialized.nextBoolean();
        taskNormalizeFire.deserialize(serialized);
        taskNormalizeSpeed.deserialize(serialized);
        Mag = serialized.nextDouble();
    }

    public enum Events {
        DELETE
    }
}
