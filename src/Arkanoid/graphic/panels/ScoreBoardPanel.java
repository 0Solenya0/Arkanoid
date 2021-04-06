package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.Logic.Player;
import Arkanoid.graphic.MainFrame;

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

    public ScoreBoardPanel() {
        super();
        listeners = new ArrayList<>();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH, SCOREBOARDPANELH);

        label = new JLabel("TOP players:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        label.setBounds(10, 10,380, 30);

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

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Listener> listeners1 = new ArrayList<>(listeners);
        for (Listener listener : listeners1)
            listener.listen(Events.BACK.toString());
    }

    public enum Events {
        BACK
    }
}
