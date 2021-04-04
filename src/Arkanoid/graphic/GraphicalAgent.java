package Arkanoid.graphic;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.Logic.Player;
import Arkanoid.graphic.panels.GamePanel;
import Arkanoid.graphic.panels.GetPlayer;
import Arkanoid.graphic.panels.MenuPanel;
import Arkanoid.Logic.LogicalAgent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class GraphicalAgent {
    public static final int fps = 24;
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
        GetPlayer getPlayer = new GetPlayer();
        frame.add(getPlayer);
        frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, GetPlayer.GETPLAYERPANELH));
        frame.repaint();

        getPlayer.addListener(s -> {
            frame.remove(getPlayer);
            //TO DO : GET OR CREATE THE PLAYER
            Player tmp = new Player(s);

            frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT));
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

            logicalAgent.startGame(tmp);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Toolkit.getDefaultToolkit().sync(); //Fixes Linux Lag
                    logicalAgent.timePassed(1000 / fps);
                    updateState(logicalAgent.getGameState());
                }
            }, 0, 1000 / fps);
        });
    }
}
