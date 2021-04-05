package Arkanoid.Logic;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.*;
import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Prize;
import Arkanoid.Logic.models.Savable;

import java.io.File;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class GameState implements Savable<GameState> {
    private Player player;
    private Board board;
    private ArrayList<Ball> balls;
    private ArrayList<Block> blocks;
    private ArrayList<Prize> prizes;
    private int playerLives;
    public int gameId;
    private LocalDateTime createdAt;

    public GameState() {
        blocks = new ArrayList<>();
        prizes = new ArrayList<>();
        balls = new ArrayList<>();
        board = new Board();
        createdAt = LocalDateTime.now();
    }

    public void initialSetup() {
        playerLives = 3;
        newBall();
        for (int i = 0; i < 4; i++)
            addBlockRow();
    }

    public Ball newBall() {
        Ball ball = new Ball(board.getX() + board.getLength() / 2, board.getY() - Ball.defaultH);
        addBall(ball);
        return ball;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void start() {
        ArrayList<Ball> balls1 = new ArrayList<>(balls);
        ArrayList<Prize> prizes1 = new ArrayList<>(prizes);
        ArrayList<Block> blocks1 = new ArrayList<>(blocks);
        for (Ball ball: balls1)
            ball.start();
        for (Prize prize: prizes1)
            prize.fall();
        for (Block block: blocks1)
            block.resume();
        board.resume();
    }

    public void pause() {
        ArrayList<Ball> balls1 = new ArrayList<>(balls);
        ArrayList<Prize> prizes1 = new ArrayList<>(prizes);
        ArrayList<Block> blocks1 = new ArrayList<>(blocks);
        for (Ball ball: balls1)
            ball.pause();
        for (Prize prize: prizes1)
            prize.pause();
        for (Block block: blocks1)
            block.pause();
        board.pause();
    }

    public void addBlockRow() {
        for (Block block: blocks)
            block.shiftDown();
        for (int i = 0; i < 6; i++) {
            Block block;
            block = new PrizeBlock(12 + i * 15 + i * Block.defaultWidth, Block.YSHIFT, Prize.PrizeType.RANDOM);
            addBlock(block);
        }
    }

    public void addBlock(Block block) {
        block.listener = new Listener() {
            @Override
            public void listen(String s) {
                Scanner scanner = new Scanner(s);
                String res = scanner.next();
                if (Block.Events.valueOf(res) == Block.Events.DELETE)
                    blocks.remove(block);
                else if (Block.Events.valueOf(res) == Block.Events.ADDPRIZE) {
                    String type = scanner.next();
                    int x = scanner.nextInt(), y = scanner.nextInt();
                    Prize prize = new Prize(x, y, Prize.PrizeType.valueOf(type));
                    addPrize(prize);
                    start();
                }
            }
        };
        blocks.add(block);
    }

    public void addPrize(Prize prize) {
        prize.listener = new Listener() {
            @Override
            public void listen(String s) {
                if (Prize.Events.valueOf(s) == Prize.Events.DELETE)
                    prizes.remove(prize);
            }
        };
        prizes.add(prize);
    }

    public void addBall(Ball ball) {
        ball.listener = new Listener() {
            @Override
            public void listen(String s) {
                if (Ball.Events.valueOf(s) == Ball.Events.DELETE)
                    balls.remove(ball);
            }
        };
        balls.add(ball);
    }

    public void usePrize(Prize.PrizeType prize) {
        System.out.println(prize.toString());
        switch (prize) {
            case EXPANDBOARD:
                board.usePrize(Prize.PrizeType.EXPANDBOARD);
                break;
            case SHRINKBOARD:
                board.usePrize(Prize.PrizeType.SHRINKBOARD);
                break;
            case CONFUSEBOARD:
                board.Confuse();
                break;
            case FASTBALL:
                for(Ball ball: balls)
                    ball.usePrize(Prize.PrizeType.FASTBALL);
                break;
            case SLOWBALL:
                for (Ball ball: balls)
                    ball.usePrize(Prize.PrizeType.SLOWBALL);
                break;
            case MULTIBALL:
                Ball ball1 = newBall();
                Ball ball2 = newBall();
                ball2.setxSpeed(-ball2.getxSpeed());
                start();
                break;
            case FIREBALL:
                ArrayList<Ball> balls1 = new ArrayList<>(balls);
                for (Ball ball: balls1)
                    ball.usePrize(prize);
                break;
            case RANDOM:
                int len = Prize.PrizeType.values().length;
                len -= 1;
                Random random = new Random();
                usePrize(Prize.PrizeType.values()[random.nextInt(len)]);
                break;
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public void setPlayerLives(int playerLives) {
        this.playerLives = playerLives;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String serialize() {
        String res = gameId + "\n" +
                createdAt + "\n" +
                playerLives + "\n" +
                player.id + "\n";
        return res;
    }

    @Override
    public void extraSave(File path) {
        board.save(new File(path.getPath() + "/board"));
        deleteDir(new File(path.getPath() + "/balls"));
        deleteDir(new File(path.getPath() + "/blocks"));
        deleteDir(new File(path.getPath() + "/prizes"));
        for (int i = 0; i < balls.size(); i++)
            balls.get(i).save(new File(path.getPath() + "/balls/" + i));
        for (int i = 0; i < blocks.size(); i++)
            blocks.get(i).save(new File(path.getPath() + "/blocks/" + i));
        for (int i = 0; i < prizes.size(); i++)
            prizes.get(i).save(new File(path.getPath() + "/prizes/" + i));
    }

    static void deleteDir(File path) {
        for(String p: Objects.requireNonNull(path.list())){
            File currentFile = new File(path.getPath(),p);
            currentFile.delete();
        }
    }
}
