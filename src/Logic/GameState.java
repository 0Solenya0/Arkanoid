package Logic;

import Logic.models.Ball;
import Logic.models.Board;

public class GameState {
    private Board board;
    private Ball ball;

    public GameState() {
        board = new Board();
        ball = new Ball(board.getX() + board.getLength() / 2, 425);
    }

    public Ball getBall() {
        return ball;
    }

    public Board getBoard() {
        return board;
    }
}
