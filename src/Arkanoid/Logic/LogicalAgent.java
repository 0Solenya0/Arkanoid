package Arkanoid.Logic;

import Arkanoid.Logic.models.Block.Block;

import java.util.ArrayList;

public class LogicalAgent {
    private GameState gameState;
    boolean isGameStarted;

    public LogicalAgent() {
        isGameStarted = false;
        gameState = new GameState();
        gameState.addBlockRow();
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

    public void timePassed(int ms) {
        if (isGameStarted) {
            gameState.getBall().move(ms);
            for (int i = 0; i < gameState.getBlocks().size(); i++) {
                int sz = gameState.getBlocks().size();
                gameState.getBall().handleBlockCollision(gameState.getBlocks().get(i));
                if (sz > gameState.getBlocks().size())
                    i--;
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }
}
