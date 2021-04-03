package graphic;

import graphic.panels.Menu;
import Logic.LogicalAgent;

public class GraphicalAgent {
    LogicalAgent logicalAgent;
    MainFrame frame;
    Menu menuPanel;

    public GraphicalAgent(LogicalAgent logicalAgent) {
        this.logicalAgent = logicalAgent;
        frame = new MainFrame();
        menuPanel = new Menu();
        menuPanel.addListener(new Listener() {
            @Override
            public void listen(String e) {
                if (Menu.Events.valueOf(e) == Menu.Events.PLAY)
                    startGame();
            }
        });
        frame.add(menuPanel);
        frame.repaint();
    }

    public void startGame() {
        frame.remove(menuPanel);
        frame.repaint();
    }
}
