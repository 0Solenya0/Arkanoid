package Arkanoid.Logic;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.Logic.models.Block.WoddenBlock;
import Arkanoid.Logic.models.Prize;
import Arkanoid.graphic.panels.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LogicalAgent implements KeyListener {
    private GameState gameState;
    boolean isGameStarted, isGameOver, isPaused;
    int addRow = 30000;
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

        resumeGame();
    }

    public void resumeGame() {
        if (!isGameStarted)
            return;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkLogic();
                addRow -= 10;
            }
        }, 0, 10);
        gameState.start();
    }

    public void pauseGame() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        gameState.pause();
    }

    public void gameOver() {
        isGameStarted = false;
        isGameOver = true;
        timer.cancel();
        timer.purge();
        pauseGame();
        gameState.gameOver();
        gameState.getPlayer().setHighScore(gameState.getScore());
        gameState.getPlayer().save(new File(Player.dataSRC + "/" + gameState.getPlayer().id));
        gameState.save(new File(GameState.dataSRC + "/" + gameState.gameId + "/state"));
    }

    public void checkLogic() {
        if (isGameStarted && !isGameOver) {
            if (addRow <= 0) {
                gameState.addBlockRow();
                addRow = 30000;
            }
            ArrayList<Block> blocks = new ArrayList<>(gameState.getBlocks());
            for (Block block: blocks) {
                int h = block.getY() + block.getHeight();
                if (h >= GamePanel.defaultBoardH) {
                    gameOver();
                    return;
                }
            }
            checkBallLogic();
            checkPrizeLogic();
        }
    }

    public void checkBallLogic() {
        for (Ball ball: gameState.getBalls()) {
            ArrayList<Block> blocks = new ArrayList<>(gameState.getBlocks());
            for (Block block: blocks) {
                if (block.isHitable() && ball.bounceIfCollide(block)) {
                    block.ballHit();
                    if (ball.isOnFire() && block instanceof WoddenBlock)
                        block.ballHit();
                }
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

    public void pauseButtonClick() {
        isPaused = !isPaused;
        if (isPaused)
            pauseGame();
        else
            resumeGame();
    }

    public void saveGame() {
        gameState.getPlayer().save(new File(Player.dataSRC.getPath(), String.valueOf(gameState.getPlayer().id)));
        gameState.save(new File(GameState.dataSRC.getPath(), gameState.gameId + "/state"));
    }

    public void restartButtonClick() {
        pauseGame();
        addRow = 30000;
        int id = gameState.gameId;
        Player player = gameState.getPlayer();
        gameState = new GameState();
        gameState.gameId = id;
        startGame(player);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (isPaused)
            return;
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

    public boolean isGameOver() {
        return isGameOver;
    }
}
