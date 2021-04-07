package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.graphic.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class ExtendedPanel extends JPanel {

    ArrayList<Image> btnImages = new ArrayList<>();
    ArrayList<Pos> btnPositions = new ArrayList<>();

    public ArrayList<Listener> listeners;

    public ExtendedPanel() {
        super();
        listeners = new ArrayList<>();
    }

    public JButton addButton(ActionListener listener, Image image, int x, int y, int w, int h) {
        JButton btn = new JButton();
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBounds(x, y, w, h);
        btn.addActionListener(listener);
        btn.setFocusable(false);
        this.add(btn);
        btnImages.add(image);
        btnPositions.add(new Pos(x, y, w, h));
        return btn;
    }

    public JButton addButton(ActionListener listener, String text, int x, int y, int w, int h) {
        JButton btn = new JButton(text);
        btn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBounds(x, y, w, h);
        btn.addActionListener(listener);
        btn.setFocusable(false);
        this.add(btn);
        return btn;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < btnImages.size(); i++) {
            Pos pos = btnPositions.get(i);
            g.drawImage(btnImages.get(i), pos.x, pos.y, null);
        }
    }
}

class Pos {
    int x, y, w, h;

    public Pos(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}