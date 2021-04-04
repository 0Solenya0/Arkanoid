package Arkanoid.Logic;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.*;
import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Prize;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class GameState {
    private Player player;
    private Board board;
    private ArrayList<Ball> balls;
    private ArrayList<Block> blocks;
    private ArrayList<Prize> prizes;
    private int playerLives;

    public GameState() {
        blocks = new ArrayList<>();
        prizes = new ArrayList<>();
        balls = new ArrayList<>();
        board = new Board();
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
        for (Ball ball: balls1)
            ball.start();
        for (Prize prize: prizes1)
            prize.fall();
    }

    public void addBlockRow() {
        for (Block block: blocks)
            block.shiftDown();
        for (int i = 0; i < 6; i++) {
            Block block;
            block = new PrizeBlock(12 + i * 15 + i * Block.defaultWidth, Block.YSHIFT, Prize.PrizeType.MULTIBALL);
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
}
