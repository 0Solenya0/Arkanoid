package Logic;

import Logic.models.Block.Block;
import graphic.GraphicalAgent;

import java.util.ArrayList;

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
        if (isGameStarted) {
            gameState.getBall().move(ms);
            for (Block block: gameState.getBlocks())
                gameState.getBall().handleBlockCollision(block);
        }
    }

    public GameState getGameState() {
        return gameState;
    }
}
