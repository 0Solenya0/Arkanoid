package graphic;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int FRAME_WIDTH = 400;
    public static final int FRAME_HEIGHT = 600;

    public MainFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-FRAME_WIDTH/2, dim.height/2-FRAME_HEIGHT/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setResizable(false);
        this.setTitle("Arkanoid");
        this.setVisible(true);
        this.setLayout(null);
        this.setFocusable(true);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
    }

}
