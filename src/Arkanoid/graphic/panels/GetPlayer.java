package Arkanoid.graphic.panels;

import Arkanoid.Listener;
import Arkanoid.graphic.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GetPlayer extends JPanel implements ActionListener {
    public static final int GETPLAYERPANELH = 220;

    public ArrayList<Listener> listeners;
    JButton submitBtn;
    JTextArea textArea;
    JLabel label;

    public GetPlayer() {
        super();
        listeners = new ArrayList<>();
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setBounds(0,0, MainFrame.FRAME_WIDTH, GETPLAYERPANELH);

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        submitBtn.setBounds(310 / 2, 130, 90, 30);

        label = new JLabel("Enter your username:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        label.setBounds(10, 10,380, 30);

        textArea = new JTextArea();
        textArea.setBounds(10, 50, 380, 30);
        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

        this.add(submitBtn);
        this.add(textArea);
        this.add(label);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Listener> listeners1 = new ArrayList<>(listeners);
        for (Listener listener: listeners1) {
            listener.listen(textArea.getText());
        }
    }
}
