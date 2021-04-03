package Arkanoid.graphic.panels;

import Arkanoid.Logic.GameState;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.models.GraphicalBall;
import Arkanoid.graphic.models.GraphicalBlock;
import Arkanoid.graphic.models.GraphicalBoard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    public static final int defaultBoardH = 450;

    GraphicalBoard board;
    GraphicalBall ball;
    ArrayList<GraphicalBlock> graphicalBlocks;

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
        graphicalBlocks = new ArrayList<>();
        for (Block block: state.getBlocks()) {
            GraphicalBlock graphicalBlock = new GraphicalBlock();
            graphicalBlock.updateState(block);
            graphicalBlocks.add(graphicalBlock);
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        board.paint(g2d);
        ball.paint(g2d);
        for (GraphicalBlock graphicalBlock: graphicalBlocks)
            graphicalBlock.paint(g2d);
    }
}
