package Arkanoid.graphic.panels;

import Arkanoid.Logic.GameState;
import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.Logic.models.Prize;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.models.GraphicalBall;
import Arkanoid.graphic.models.GraphicalBlock;
import Arkanoid.graphic.models.GraphicalBoard;
import Arkanoid.graphic.models.GraphicalPrize;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GamePanel extends JPanel {
    public static final int defaultBoardH = 450;

    GraphicalBoard board;
    ArrayList<GraphicalBall> balls;
    ArrayList<GraphicalBlock> graphicalBlocks;
    ArrayList<GraphicalPrize> graphicalPrizes;

    public GamePanel() {
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);

        board = new GraphicalBoard(0, defaultBoardH);
    }

    public void updateState(GameState state) {
        board.updateState(state.getBoard());
        balls = new ArrayList<>();
        for (Ball ball: state.getBalls()) {
            GraphicalBall graphicalBall = new GraphicalBall();
            graphicalBall.updateState(ball);
            balls.add(graphicalBall);
        }
        graphicalBlocks = new ArrayList<>();
        for (Block block: state.getBlocks()) {
            GraphicalBlock graphicalBlock = new GraphicalBlock();
            graphicalBlock.updateState(block);
            graphicalBlocks.add(graphicalBlock);
        }
        graphicalPrizes = new ArrayList<>();
        for (Prize prize: state.getPrizes()) {
            GraphicalPrize graphicalPrize = new GraphicalPrize();
            graphicalPrize.updateState(prize);
            graphicalPrizes.add(graphicalPrize);
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        board.paint(g2d);
        for (GraphicalBall graphicalBall: balls)
            graphicalBall.paint(g2d);
        for (GraphicalBlock graphicalBlock: graphicalBlocks)
            graphicalBlock.paint(g2d);
        for (GraphicalPrize graphicalPrize: graphicalPrizes)
            graphicalPrize.paint(g2d);
    }
}
