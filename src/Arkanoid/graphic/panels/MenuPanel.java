package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class MenuPanel extends ExtendedPanel implements ActionListener {

    private ArrayList<Listener> listeners;
    private JButton btnPlay, btnLoad, btnScoreboard;
    private JLabel title;

    public MenuPanel() {
        super();
        listeners = new ArrayList<>();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0,MainFrame.FRAME_WIDTH,MainFrame.FRAME_HEIGHT);

        btnPlay = new JButton("Play!");
        btnPlay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        btnPlay.setForeground(Color.WHITE);
        btnPlay.setBorderPainted(false);
        btnPlay.setFocusPainted(false);
        btnPlay.setContentAreaFilled(false);
        btnPlay.setBounds(100,350,200,50);
        btnPlay.addActionListener(this);

        btnLoad = new JButton("Load Game");
        btnLoad.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setBorderPainted(false);
        btnLoad.setFocusPainted(false);
        btnLoad.setContentAreaFilled(false);
        btnLoad.setBounds(100,450,200,50);
        btnLoad.addActionListener(this);

        btnScoreboard = new JButton("Scoreboard");
        btnScoreboard.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        btnScoreboard.setForeground(Color.WHITE);
        btnScoreboard.setBorderPainted(false);
        btnScoreboard.setFocusPainted(false);
        btnScoreboard.setContentAreaFilled(false);
        btnScoreboard.setBounds(100,550,200,50);
        btnScoreboard.addActionListener(this);

        title = new JLabel("Arkanoid");
        title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(125, 50, 200, 200);

        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            addImage(ImageLoader.getImage("star.png", 20, 20), random.nextInt(400),
                    random.nextInt(600), 20, 20);
        }

        this.add(btnScoreboard);
        this.add(btnPlay);
        this.add(btnLoad);
        this.add(title);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Listener listener: listeners) {
            if (e.getSource() == btnPlay)
                listener.listen(Events.PLAY.toString());
            if (e.getSource() == btnLoad)
                listener.listen(Events.LOAD.toString());
            if (e.getSource() == btnScoreboard)
                listener.listen(Events.SCOREBOARD.toString());
        }
    }

    public enum Events {
        PLAY,
        LOAD,
        SCOREBOARD
    }
}
