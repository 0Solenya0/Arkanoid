package Arkanoid.graphic;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.Logic.Player;
import Arkanoid.graphic.panels.*;
import Arkanoid.Logic.LogicalAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class GraphicalAgent {
    public static final int fps = 24;
    LogicalAgent logicalAgent;
    MainFrame frame;
    MenuPanel menuPanel;
    GamePanel gamePanel;
    GetField getField;
    LoadPanel loadPanel;
    ScoreBoardPanel scoreBoardPanel;
    Timer timer;

    public GraphicalAgent() {
        frame = new MainFrame();
        menuPanel = new MenuPanel();
        timer = new Timer();
        showMenu();
    }

    public void showMenu() {
        frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT));
        menuPanel = new MenuPanel();
        menuPanel.addListener(new Listener() {
            @Override
            public void listen(String e) {
                frame.remove(menuPanel);
                if (MenuPanel.Events.valueOf(e) == MenuPanel.Events.PLAY)
                    askPlayer();
                if (MenuPanel.Events.valueOf(e) == MenuPanel.Events.LOAD)
                    loadGame();
                if (MenuPanel.Events.valueOf(e) == MenuPanel.Events.SCOREBOARD)
                    scoreboard();
            }
        });
        frame.add(menuPanel);
        frame.repaint();
    }

    public void askPlayer() {
        getField = new GetField("your name");
        frame.add(getField);
        frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, GetField.GETFIELDPANELH));
        frame.repaint();

        getField.addListener(s -> {
            getField.listeners.clear();
            frame.remove(getField);
            Player player = Player.getPlayerByName(s);
            logicalAgent = new LogicalAgent();
            logicalAgent.startGame(player);
            startGame();
        });
    }

    public void loadGame() {
        loadPanel = new LoadPanel();
        frame.add(loadPanel);
        frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, LoadPanel.LOADPANELH));
        frame.repaint();

        loadPanel.addListener(new Listener() {
            @Override
            public void listen(String s) {
                frame.remove(loadPanel);
                loadPanel.listeners.clear();
                logicalAgent = new LogicalAgent();
                logicalAgent.getGameState().load(new File(s));
                logicalAgent.startGame();
                startGame();
            }
        });
    }

    public void scoreboard() {
        scoreBoardPanel = new ScoreBoardPanel();
        frame.add(scoreBoardPanel);
        frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, ScoreBoardPanel.SCOREBOARDPANELH));
        frame.repaint();
    }

    public void startGame() {
        gamePanel = new GamePanel(actionEvent -> logicalAgent.pauseButtonClick(),
                actionEvent -> logicalAgent.restartButtonClick(),
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        logicalAgent.pauseGame();
                        int n;
                        if (logicalAgent.getGameState().isFirstSave())
                            n = 1;
                        else {
                            Object[] options = {"Override current save",
                                    "Make a new save"};
                            n = JOptionPane.showOptionDialog(frame,
                                    "How to save?",
                                    "Save Game",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[1]);
                        }

                        if (n == 1) {
                            MainFrame dialog = new MainFrame();
                            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            dialog.setSize(new Dimension(MainFrame.FRAME_WIDTH, GetField.GETFIELDPANELH));
                            getField = new GetField("save name");
                            dialog.add(getField);
                            getField.addListener(new Listener() {
                                @Override
                                public void listen(String s) {
                                    logicalAgent.saveGame(s, n);
                                    dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
                                }
                            });
                        }
                        else
                            logicalAgent.saveGame("", n);
                    }
                });
        frame.addKeyListener(logicalAgent);

        frame.setSize(new Dimension(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT));
        frame.add(gamePanel);
        frame.repaint();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Toolkit.getDefaultToolkit().sync(); //Fixes Linux Lag
                if (logicalAgent.isGameOver()) {
                    timer.cancel();
                    timer.purge();
                    timer = new Timer();
                    gamePanel.paintGameOver();
                    frame.repaint();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    frame.remove(gamePanel);
                    frame.removeKeyListener(logicalAgent);
                    frame.invalidate();
                    frame.validate();
                    showMenu();
                    return;
                }
                if (logicalAgent.isGameStarted())
                    updateState(logicalAgent.getGameState());
            }
        }, 0, 1000 / fps);
    }

    public void updateState(GameState gameState) {
        gamePanel.updateState(gameState);
    }
}
