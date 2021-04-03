package Graphic;

import Graphic.Frames.GameFrame;
import Graphic.Frames.MenuFrame;
import Logic.LogicalAgent;

public class GraphicalAgent {
    LogicalAgent logicalAgent;
    MenuFrame menuFrame;

    public GraphicalAgent(LogicalAgent logicalAgent) {
        this.logicalAgent = logicalAgent;
        menuFrame = new MenuFrame();
        menuFrame.addListener(new Listener() {
            @Override
            public void listen(String e) {
                if (MenuFrame.Events.valueOf(e) == MenuFrame.Events.PLAY)
                    startGame();
            }
        });
    }

    public void startGame() {
        GameFrame gameFrame = new GameFrame();
        menuFrame.dispose();
    }
}
