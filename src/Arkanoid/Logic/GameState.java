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
    private Board board;
    private Ball ball;
    private ArrayList<Block> blocks;
    private ArrayList<Prize> prizes;

    public GameState() {
        blocks = new ArrayList<>();
        prizes = new ArrayList<>();
        board = new Board();
        ball = new Ball(board.getX() + board.getLength() / 2, board.getY() - Ball.defaultH);
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public void addBlockRow() {
        for (Block block: blocks)
            block.shiftDown();
        for (int i = 0; i < 6; i++) {
            Block block;
            block = new PrizeBlock(12 + i * 15 + i * Block.defaultWidth, Block.YSHIFT, Prize.PrizeType.EXPANDBOARD);
            addBlock(block);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
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
        prize.fall(this);
    }

    public Ball getBall() {
        return ball;
    }

    public Board getBoard() {
        return board;
    }

    public void usePrize(Prize.PrizeType prize) {
        switch (prize) {
            case EXPANDBOARD:
                board.usePrize(Prize.PrizeType.EXPANDBOARD);
                break;
            case SHRINKBOARD:
                board.usePrize(Prize.PrizeType.SHRINKBOARD);
                break;
        }
    }
}
