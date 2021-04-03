package graphic.panels;

import graphic.Listener;
import graphic.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu extends JPanel implements ActionListener {

    private ArrayList<Listener> listeners;
    private JButton btnPlay;
    private JLabel title;

    public Menu() {
        super();
        listeners = new ArrayList<>();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0,400,600);

        btnPlay = new JButton("Play!");
        btnPlay.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        btnPlay.setForeground(Color.WHITE);
        btnPlay.setBorderPainted(false);
        btnPlay.setFocusPainted(false);
        btnPlay.setContentAreaFilled(false);
        btnPlay.setBounds(100,350,200,50);
        btnPlay.addActionListener(this);

        title = new JLabel("Arkanoid");
        title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(125, 50, 200, 200);

        this.add(btnPlay);
        this.add(title);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(listeners.size());
        for (Listener listener: listeners) {
            if (e.getSource() == btnPlay)
                listener.listen(Events.PLAY.toString());
        }

    }

    public enum Events {
        PLAY
    }
}
