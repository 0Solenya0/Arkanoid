package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.Logic.GameState;
import Arkanoid.graphic.MainFrame;
import Arkanoid.graphic.models.GraphicalBall;
import Arkanoid.graphic.models.GraphicalBlock;
import Arkanoid.graphic.models.GraphicalPrize;
import Arkanoid.graphic.util.ImageLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoadPanel extends ExtendedPanel implements ActionListener {
    public static final int LOADPANELH = 700;

    ArrayList<JButton> saves = new ArrayList<>();
    ArrayList<String> savePath = new ArrayList<>();
    JLabel label;

    public LoadPanel(ActionListener exitActionListener) {
        super();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH, LOADPANELH);

        label = new JLabel("Click your save:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        label.setBounds(10, 10,380, 30);

        addButton(exitActionListener, ImageLoader.getImage("exit.png", 40, 40)
                , MainFrame.FRAME_WIDTH - 50, LoadPanel.LOADPANELH - 80, 40, 40);

        int cnt = Objects.requireNonNull(GameState.dataSRC.list()).length;
        for (int i = cnt; i >= 1; i--) {
            GameState gameState = new GameState();
            gameState.load(new File(GameState.dataSRC.getPath() + "/" + i + "/state"));
            if (gameState.isFinished())
                continue;
            JButton btn = addButton(this, gameState.name + " - " + gameState.getCreatedAt()
                    , 10,saves.size() * 60 + 60,390,50);
            saves.add(btn);
            savePath.add(GameState.dataSRC.getPath() + "/" + i + "/state");
        }

        this.add(label);
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
