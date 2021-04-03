package graphic;

import Logic.GameState;
import graphic.panels.GamePanel;
import graphic.panels.MenuPanel;
import Logic.LogicalAgent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicalAgent {
    LogicalAgent logicalAgent;
    MainFrame frame;
    MenuPanel menuPanel;
    GamePanel gamePanel;

    public GraphicalAgent(LogicalAgent logicalAgent) {
        this.logicalAgent = logicalAgent;
        frame = new MainFrame();
        menuPanel = new MenuPanel();
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

    public void updateState(GameState gameState) {
        gamePanel.updateState(gameState);
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
    }
}
