package Logic;

import graphic.GraphicalAgent;

public class LogicalAgent {
    private GameState gameState;
    boolean isGameStarted;
    public GraphicalAgent agent;

    public LogicalAgent() {
        isGameStarted = false;
        gameState = new GameState();
    }

    public void rightArrowPressed() {
        gameState.getBoard().changeLength(1);
        gameState.getBoard().move('R');
        agent.updateState(gameState);
    }

    public void leftArrowPressed() {
        gameState.getBoard().changeLength(-1);
        gameState.getBoard().move('L');
        agent.updateState(gameState);
    }

    public void startGame() {
        isGameStarted = true;
    }

    public GameState getGameState() {
        return gameState;
    }
}
