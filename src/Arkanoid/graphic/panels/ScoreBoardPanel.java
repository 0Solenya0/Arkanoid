package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.Logic.Player;
import Arkanoid.graphic.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ScoreBoardPanel extends JPanel implements ActionListener {
    public static final int SCOREBOARDPANELH = 700;

    public ArrayList<Listener> listeners;
    JLabel label;

    Image exitImg;

    public ScoreBoardPanel(ActionListener exitActionListener) {
        super();
        listeners = new ArrayList<>();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH, SCOREBOARDPANELH);

        label = new JLabel("TOP players:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        label.setBounds(10, 10,380, 30);

        configureButton(exitActionListener, MainFrame.FRAME_WIDTH - 50, ScoreBoardPanel.SCOREBOARDPANELH - 80, 40, 40);

        try {
            exitImg = ImageIO.read(new File("./resources/exit.png"));
            exitImg = exitImg.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        int cnt = Objects.requireNonNull(Player.dataSRC.list()).length;
        int num = 0;
        for (int i = 0; i <= cnt; i++) {
            Player player = new Player("");
            if ((new File(Player.dataSRC.getPath() + "/" + i)).exists())
                player.load(new File(Player.dataSRC.getPath() + "/" + i));
            else
                continue;
            JLabel playerLabel;
            playerLabel = new JLabel(player.getName() + " - " + player.getHighScore());
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            playerLabel.setBounds(10, 10,380, 30);
            playerLabel.setBounds(10, num * 60 + 60,390,50);
            num++;
            this.add(playerLabel);
        }

        this.add(label);
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

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Listener> listeners1 = new ArrayList<>(listeners);
        for (Listener listener : listeners1)
            listener.listen(Events.BACK.toString());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(exitImg, MainFrame.FRAME_WIDTH - 50, SCOREBOARDPANELH - 80, null);
    }

    public enum Events {
        BACK
    }
}
