package graphic.panels;

import Logic.GameState;
import Logic.models.Board.Board;
import graphic.Listener;
import graphic.MainFrame;
import graphic.models.GraphicalBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    GraphicalBoard board;

    public GamePanel() {
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);

        board = new GraphicalBoard(0, 450);
    }

    public void updateState(GameState state) {
        board.update(state.getBoard().getX());
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        board.paint((Graphics2D) g);
    }
}
