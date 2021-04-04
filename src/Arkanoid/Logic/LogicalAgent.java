package Arkanoid.Logic;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.graphic.GraphicalAgent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LogicalAgent implements KeyListener {
    private GameState gameState;
    boolean isGameStarted;
    Timer timer;

    public LogicalAgent() {
        isGameStarted = false;
        gameState = new GameState();
        timer = new Timer();
    }

    public void startGame(Player player) {
        isGameStarted = true;

        gameState.initialSetup();
        gameState.setPlayer(player);
        gameState.start();
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameState.addBlockRow();
            }
        }, 0, 30000);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkLogic();
            }
        }, 0, 10);
    }

    public void checkLogic() {
        if (isGameStarted) {
            for (Ball ball: gameState.getBalls()) {
                for (int i = 0; i < gameState.getBlocks().size(); i++) {
                    int sz = gameState.getBlocks().size();
                    if (gameState.getBlocks().get(i).isHitable())
                        ball.handleBlockCollision(gameState.getBlocks().get(i));
                    if (sz > gameState.getBlocks().size())
                        i--;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 68) // 'd' character
            gameState.getBoard().move('R');
        else if (keyEvent.getKeyCode() == 65) // 'a' character
            gameState.getBoard().move('L');
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public GameState getGameState() {
        return gameState;
    }

}
