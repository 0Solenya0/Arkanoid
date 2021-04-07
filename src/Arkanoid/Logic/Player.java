package Arkanoid.Logic;

import Arkanoid.Logic.models.Savable;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Player implements Savable<Player> {
    public static File dataSRC = new File("./db/players/");
    public int id;
    private String name;
    private int highScore;

    public void setHighScore(int highScore) {
        this.highScore = Math.max(highScore, this.highScore);
    }

    public Player(String name) {
        this.name = name;
        id = Savable.getLastId(dataSRC) + 1;
        if (name.isBlank() || name.isEmpty()) {
            this.name = "Anonymous";
            id = 0;
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public String getName() {
        return name;
    }

    @Override
    public String serialize() {
        String res = id + "\n" +
                name + "\n" +
                highScore + "\n";
        return res;
    }

    @Override
    public void deserialize(Scanner serialized) {
        id = serialized.nextInt();
        name = serialized.next();
        highScore = serialized.nextInt();
    }

    public static Player getPlayerByName(String name) {
        Player player = new Player("");
        if (dataSRC.list() != null) {
            for (int i = 0; i <= Objects.requireNonNull(dataSRC.list()).length; i++) {
                if (new File(dataSRC.getPath() + "/" + i).exists()) {
                    player.load(new File(dataSRC.getPath() + "/" + i));
                    if (player.name.equals(name) || (name.isEmpty() && player.name.equals("Anonymous")))
                        return player;
                }
            }
        }
        player = new Player(name);
        return player;
    }
}
