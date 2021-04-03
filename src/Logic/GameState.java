package Logic;

import Logic.models.Ball;
import Logic.models.Block.Block;
import Logic.models.Board;

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

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Ball getBall() {
        return ball;
    }

    public Board getBoard() {
        return board;
    }
}
