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
import Arkanoid.graphic.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends ExtendedPanel {
    public static final int defaultBoardH = 450;

    GraphicalBoard board;
    ArrayList<GraphicalBall> balls;
    ArrayList<GraphicalBlock> graphicalBlocks;
    ArrayList<GraphicalPrize> graphicalPrizes;
    JLabel score;

    public GamePanel(ActionListener pauseActionListener,
                     ActionListener restartActionListener,
                     ActionListener saveActionListener,
                     ActionListener exitActionListener) {
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);

        score = new JLabel("0");
        score.setForeground(Color.WHITE);
        score.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        score.setBounds(180, 500,380, 30);
        this.add(score);

        addButton(pauseActionListener, ImageLoader.getImage("pause.png", 40, 40)
                , 5, MainFrame.FRAME_HEIGHT - 80, 40, 40);
        addButton(restartActionListener, ImageLoader.getImage("restart.png", 40, 40)
                ,50, MainFrame.FRAME_HEIGHT - 80, 40, 40);
        addButton(saveActionListener, ImageLoader.getImage("save.png", 40, 40)
                , 95, MainFrame.FRAME_HEIGHT - 80, 40, 40);
        addButton(exitActionListener, ImageLoader.getImage("exit.png", 40, 40)
                ,MainFrame.FRAME_WIDTH - 50, MainFrame.FRAME_HEIGHT - 80, 40, 40);

        board = new GraphicalBoard(defaultBoardH);
        balls = new ArrayList<>();
        graphicalPrizes = new ArrayList<>();
        graphicalBlocks = new ArrayList<>();
    }

    public void paintGameOver() {
        JLabel label = new JLabel("GAME OVER");
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
        label.setForeground(Color.RED);
        label.setBounds(90, 100, 400, 200);
        this.add(label);
        this.repaint();
    }

    public void updateState(GameState state) {
        score.setText(String.valueOf(state.getScore()));
        board.updateState(state.getBoard());
        balls = new ArrayList<>();
        ArrayList<Ball> gameStateBalls = new ArrayList<>(state.getBalls());
        for (Ball ball: gameStateBalls) {
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
        images.clear();
        for (int i = 0; i < state.getPlayerLives(); i++)
            addImage(ImageLoader.getImage("heart.png", 50, 50), i * 60, 550, 50, 50);
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
