package graphic;

import Logic.GameState;
import graphic.panels.GamePanel;
import graphic.panels.MenuPanel;
import Logic.LogicalAgent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class GraphicalAgent {
    public static final int fps = 30;
    LogicalAgent logicalAgent;
    MainFrame frame;
    MenuPanel menuPanel;
    GamePanel gamePanel;
    Timer timer;

    public GraphicalAgent(LogicalAgent logicalAgent) {
        this.logicalAgent = logicalAgent;
        frame = new MainFrame();
        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        timer = new Timer();
        showMenu();
    }

    public void updateState(GameState gameState) {
        gamePanel.updateState(gameState);
    }

    public void showMenu() {
        menuPanel.addListener(new Listener() {
            @Override
            public void listen(String e) {
                if (MenuPanel.Events.valueOf(e) == MenuPanel.Events.PLAY)
                    startGame();
            }
        });
        frame.add(menuPanel);
        frame.repaint();
    }

    public void startGame() {
        frame.remove(menuPanel);
        gamePanel = new GamePanel();
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == 68) // 'd' character
                    logicalAgent.rightArrowPressed();
                else if (keyEvent.getKeyCode() == 65) // 'a' character
                    logicalAgent.leftArrowPressed();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
        frame.add(gamePanel);
        frame.repaint();
        logicalAgent.startGame();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Toolkit.getDefaultToolkit().sync(); //Fixes Linux Lag
                logicalAgent.moveBall(1000 / fps);
                updateState(logicalAgent.getGameState());
            }
        }, 1000 / fps, 1000 / fps);
    }
}
