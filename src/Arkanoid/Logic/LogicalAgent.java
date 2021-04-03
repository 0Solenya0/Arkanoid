package Arkanoid.Logic;

import Arkanoid.Logic.models.Block.Block;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LogicalAgent {
    private GameState gameState;
    boolean isGameStarted;
    Timer timer;

    public LogicalAgent() {
        isGameStarted = false;
        gameState = new GameState();
        for (int i = 0; i < 4; i++)
            gameState.addBlockRow();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameState.addBlockRow();
            }
        }, 0, 30000);
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
                if (gameState.getBlocks().get(i).isHitable())
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
