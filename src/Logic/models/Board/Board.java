package Logic.models.Board;

public class Board {
    private final int xSpeed = 8;
    private int x;

    public Board() {
        x = 0;
    }

    public void move(Character c) {
        switch (c) {
            case 'R':
                x += xSpeed;
                break;
            case 'L':
                x -= xSpeed;
                break;
        }
    }

    public int getX() {
        return x;
    }
}
