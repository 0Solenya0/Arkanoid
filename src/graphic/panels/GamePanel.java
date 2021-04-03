package graphic.panels;

import Logic.GameState;
import graphic.MainFrame;
import graphic.models.GraphicalBall;
import graphic.models.GraphicalBoard;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public static final int defaultBoardH = 450;

    GraphicalBoard board;
    GraphicalBall ball;

    public GamePanel() {
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);

        board = new GraphicalBoard(0, defaultBoardH);
        ball = new GraphicalBall(100, 100);
    }

    public void updateState(GameState state) {
        board.updateState(state.getBoard());
        ball.updateState(state.getBall());
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        board.paint(g2d);
        ball.paint(g2d);
    }
}
