package Arkanoid.Logic.models.Block;

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

}
