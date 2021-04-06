package Arkanoid.graphic;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.Logic.Player;
import Arkanoid.graphic.panels.GamePanel;
import Arkanoid.graphic.panels.GetPlayer;
import Arkanoid.graphic.panels.MenuPanel;
import Arkanoid.Logic.LogicalAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            logicalAgent.startGame(tmp);


            gamePanel = new GamePanel(actionEvent -> logicalAgent.pauseButtonClick(),
                    actionEvent -> logicalAgent.restartButtonClick());
            frame.addKeyListener(logicalAgent);

            frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT));
            frame.add(gamePanel);
            frame.repaint();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Toolkit.getDefaultToolkit().sync(); //Fixes Linux Lag
                    if (logicalAgent.isGameStarted())
                        updateState(logicalAgent.getGameState());
                }
            }, 0, 1000 / fps);
        });
    }
}
