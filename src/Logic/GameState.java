package Logic;

import Logic.models.Board.Board;

public class GameState {
    private Board board;

    public GameState() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }
}
