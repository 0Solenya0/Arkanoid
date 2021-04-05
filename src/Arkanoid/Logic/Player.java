package Arkanoid.Logic;

import Arkanoid.Logic.models.Savable;

public class Player implements Savable<Player> {
    public int id;
    private String name;
    private int highScore, score;

    public Player(String name) {
        this.name = name;
        if (name.isBlank() || name.isEmpty())
            this.name = "Anonymous";
    }

    @Override
    public String serialize() {
        String res = id + "\n" +
                name + "\n" +
                highScore + "\n" +
                score + "\n";
        return res;
    }
}
