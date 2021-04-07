package Arkanoid.Logic;

import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.Logic.models.Block.WoddenBlock;
import Arkanoid.Logic.models.Prize;
import Arkanoid.Logic.models.Savable;
import Arkanoid.graphic.panels.GamePanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LogicalAgent implements KeyListener {
    private GameState gameState;
    boolean isGameStarted, isGameOver, isPaused;
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

    public void startGame() {
        isGameStarted = true;

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
                gameState.addRow -= 20;
                gameState.age += 20;
            }
        }, 0, 20);
        gameState.start();
    }

    public void pauseGame() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        gameState.pause();
    }

    public void gameOver(boolean save) {
        isGameStarted = false;
        isGameOver = true;
        timer.cancel();
        timer.purge();
        pauseGame();
        gameState.gameOver();
        gameState.getPlayer().setHighScore(gameState.getScore());
        if (save) {
            gameState.getPlayer().save(new File(Player.dataSRC + "/" + gameState.getPlayer().id));
            gameState.save(new File(GameState.dataSRC + "/" + gameState.gameId + "/state"));
        }
    }

    public void checkLogic() {
        if (isGameStarted && !isGameOver) {
            if (gameState.addRow <= 0) {
                gameState.addBlockRow();
                gameState.addScore(100);
                gameState.addRow = 30000;
            }
            ArrayList<Block> blocks = new ArrayList<>(gameState.getBlocks());
            for (Block block: blocks) {
                int h = block.getY() + block.getHeight();
                if (h >= GamePanel.defaultBoardH) {
                    gameOver(true);
                    return;
                }
            }
            checkBallLogic();
            checkPrizeLogic();
        }
    }

    public void checkBallLogic() {
        ArrayList<Ball> balls1 = new ArrayList<>(gameState.getBalls());
        for (Ball ball: balls1) {
            ball.updateBaseSpeed(gameState.age);
            ArrayList<Block> blocks = new ArrayList<>(gameState.getBlocks());
            for (Block block: blocks) {
                if (block.isHitable() && ball.bounceIfCollide(block)) {
                    gameState.addScore(10);
                    block.ballHit();
                    if (ball.isOnFire() && block instanceof WoddenBlock) {
                        block.ballHit();
                        gameState.addScore(10);
                    }
                }
            }
            double len = gameState.getBoard().getLength(), x = gameState.getBoard().getX();
            if (ball.getY() + Ball.defaultH > GamePanel.defaultBoardH) {
                   if (x <= ball.getX() + Ball.defaultW && ball.getX() <= x + len) {
                       double mid = x + len / 2;
                       double alpha = Math.atan(Math.abs(ball.getxSpeed() / ball.getySpeed()));
                       double beta = alpha / (len / 2) * Math.abs(mid - ball.getX());
                       double reflect = Math.PI / 2 - beta;
                       if (Math.abs(mid - (ball.getX() + Ball.defaultW / 2)) > len * 3 / 8)
                           reflect = Math.PI / 4;
                       ball.setxSpeed(Math.cos(reflect) * ball.getxSpeed() / Math.abs(ball.getxSpeed()));
                       ball.setySpeed(-Math.sin(reflect));
                   }
                   else
                        ball.delete();
            }
        }
        if (gameState.getBalls().isEmpty()) {
            int l = gameState.getPlayerLives();
            l -= 1;
            if (l == 0)
                gameOver(true);
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
            if (prize.collideWithBoard(gameState.getBoard())) {
                gameState.usePrize(prize.getType());
                gameState.addScore(20);
            }
        }
    }

    public void pauseButtonClick() {

        isPaused = !isPaused;
        if (isPaused)
            pauseGame();
        else
            resumeGame();
    }

    public void saveGame(String name, int t) {
        if (t == 0 && gameState.isFirstSave()) {
            gameState.name = name;
        }
        else if (t == 1) {
            gameState.gameId = Savable.getLastId(GameState.dataSRC) + 1;
            gameState.name = name;
        }
        gameState.getPlayer().save(new File(Player.dataSRC.getPath(), String.valueOf(gameState.getPlayer().id)));
        gameState.save(new File(GameState.dataSRC.getPath(), gameState.gameId + "/state"));
    }

    public void restartButtonClick() {
        pauseGame();
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
