package Graphic.Frames;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    final int FRAME_WIDTH = 400;
    final int FRAME_HEIGHT = 600;

    public MainFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-FRAME_WIDTH/2, dim.height/2-FRAME_HEIGHT/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setResizable(false);
        this.setTitle("Arkanoid");
        this.setVisible(true);
    }

}
