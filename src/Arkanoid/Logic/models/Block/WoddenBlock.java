package Arkanoid.Logic.models.Block;

import java.util.Scanner;

public class WoddenBlock extends Block {

    int lives;

    public WoddenBlock(int x, int y) {
        super(x, y);
        lives = 2;
    }

    @Override
    public void ballHit() {
        lives--;
        if (lives == 0)
            this.delete();
    }

    @Override
    public String serialize() {
        return getSerializeData() + lives + "\n";
    }

    @Override
    public void deserialize(Scanner scanner) {
        super.deserialize(scanner);
        lives = scanner.nextInt();
    }
}
