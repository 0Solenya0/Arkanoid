package Arkanoid.Logic;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.*;
import Arkanoid.Logic.models.Board;

import java.util.ArrayList;

public class GameState {
    private Board board;
    private Ball ball;
    private ArrayList<Block> blocks;

    public GameState() {
        blocks = new ArrayList<>();
        board = new Board();
        ball = new Ball(board.getX() + board.getLength() / 2, board.getY() - Ball.defaultH);
    }

    public void addBlockRow() {
        for (Block block: blocks)
            block.shiftDown();
        for (int i = 0; i < 6; i++) {
            Block block;
            block = new GlassBlock(12 + i * 15 + i * Block.defaultWidth, Block.YSHIFT);
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
                if (Block.Events.valueOf(s) == Block.Events.DELETE)
                    blocks.remove(block);
            }
        };
        blocks.add(block);
    }

    public Ball getBall() {
        return ball;
    }

    public Board getBoard() {
        return board;
    }
}
