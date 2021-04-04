package Arkanoid.Logic;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.Logic.models.Prize;
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

    public void gameOver() {
        isGameStarted = false;
    }

    public void checkLogic() {
        if (isGameStarted) {
            checkBallLogic();
            checkPrizeLogic();
        }
    }

    public void checkBallLogic() {
        for (Ball ball: gameState.getBalls()) {
            ArrayList<Block> blocks = new ArrayList<>(gameState.getBlocks());
            for (Block block: blocks) {
                if (block.isHitable() && ball.bounceIfCollide(block))
                    block.ballHit();
            }
        }
        if (gameState.getBalls().isEmpty()) {
            int l = gameState.getPlayerLives();
            l -= 1;
            if (l == 0)
                gameOver();
            else {
                gameState.newBall();
                gameState.start();
            }
            gameState.setPlayerLives(l);
        }
    }

    public void checkPrizeLogic() {
        ArrayList<Prize> prizes = new ArrayList<>(gameState.getPrizes());
        for (Prize prize: prizes) {
            if (prize.collideWithBoard(gameState.getBoard()))
                gameState.usePrize(prize.getType());
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
