package Logic;

import graphic.GraphicalAgent;

public class LogicalAgent {
    private GameState gameState;
    boolean isGameStarted;

    public LogicalAgent() {
        isGameStarted = false;
        gameState = new GameState();
    }

    public void rightArrowPressed() {
        gameState.getBoard().move('R');
    }

    public void leftArrowPressed() {
        gameState.getBoard().move('L');
    }

    public void startGame() {
        isGameStarted = true;
    }

    public void moveBall(int ms) {
        if (isGameStarted)
            gameState.getBall().move(ms);
    }

    public GameState getGameState() {
        return gameState;
    }
}
