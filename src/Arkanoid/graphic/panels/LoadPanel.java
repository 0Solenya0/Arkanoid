package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.graphic.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class LoadPanel extends JPanel implements ActionListener {
    public static final int LOADPANELH = 700;

    public ArrayList<Listener> listeners;
    ArrayList<JButton> saves = new ArrayList<>();
    ArrayList<String> savePath = new ArrayList<>();
    JLabel label;

    public LoadPanel() {
        super();
        listeners = new ArrayList<>();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH, LOADPANELH);

        label = new JLabel("Click your save:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        label.setBounds(10, 10,380, 30);

        int cnt = Objects.requireNonNull(GameState.dataSRC.list()).length;
        for (int i = 1; i <= cnt; i++) {
            GameState gameState = new GameState();
            gameState.load(new File(GameState.dataSRC.getPath() + "/" + i + "/state"));
            if (gameState.isFinished())
                continue;
            JButton btnLoad;
            btnLoad = new JButton(gameState.name + " - " + gameState.getCreatedAt());
            btnLoad.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            btnLoad.setForeground(Color.WHITE);
            btnLoad.setBorderPainted(false);
            btnLoad.setFocusPainted(false);
            btnLoad.setContentAreaFilled(false);
            btnLoad.setBounds(10,saves.size() * 60 + 60,390,50);
            btnLoad.addActionListener(this);
            saves.add(btnLoad);
            savePath.add(GameState.dataSRC.getPath() + "/" + i + "/state");
            this.add(btnLoad);
        }

        this.add(label);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Listener> listeners1 = new ArrayList<>(listeners);
        for (int i = 0; i < saves.size(); i++) {
            if (actionEvent.getSource() == saves.get(i)) {
                for (Listener listener : listeners1)
                    listener.listen(savePath.get(i));
            }
        }
    }
}
