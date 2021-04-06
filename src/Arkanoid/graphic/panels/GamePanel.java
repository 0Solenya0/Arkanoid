package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.Block;
import Arkanoid.Logic.models.Prize;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.models.GraphicalBall;
import Arkanoid.graphic.models.GraphicalBlock;
import Arkanoid.graphic.models.GraphicalBoard;
import Arkanoid.graphic.models.GraphicalPrize;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GamePanel extends JPanel {
    public static final int defaultBoardH = 450;

    GraphicalBoard board;
    ArrayList<GraphicalBall> balls;
    ArrayList<GraphicalBlock> graphicalBlocks;
    ArrayList<GraphicalPrize> graphicalPrizes;

    Image pauseImg;
    Image restartImg;
    Image saveImg;

    public GamePanel(ActionListener pauseActionListener,
                     ActionListener restartActionListener,
                     ActionListener saveActionListener) {
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);

        configureButton(pauseActionListener, 5, MainFrame.FRAME_HEIGHT - 80, 40, 40);
        configureButton(restartActionListener, 50, MainFrame.FRAME_HEIGHT - 80, 40, 40);
        configureButton(saveActionListener, 95, MainFrame.FRAME_HEIGHT - 80, 40, 40);

        try {
            pauseImg = ImageIO.read(new File("./resources/pause.png"));
            pauseImg = pauseImg.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
            restartImg = ImageIO.read(new File("./resources/restart.png"));
            restartImg = restartImg.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
            saveImg = ImageIO.read(new File("./resources/save.png"));
            saveImg = saveImg.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        }
        catch (Exception e) { }

        board = new GraphicalBoard(0, defaultBoardH);
    }

    public void configureButton(ActionListener listener, int x, int y, int w, int h) {
        JButton btn = new JButton();
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBounds(x, y, w, h);
        btn.addActionListener(listener);
        btn.setFocusable(false);
        this.add(btn);
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
        g2d.drawImage(pauseImg, 5, MainFrame.FRAME_HEIGHT - 80, null);
        g2d.drawImage(restartImg, 50, MainFrame.FRAME_HEIGHT - 80, null);
        g2d.drawImage(saveImg, 95, MainFrame.FRAME_HEIGHT - 80, null);
    }
}
