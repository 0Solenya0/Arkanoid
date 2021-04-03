package Arkanoid.Logic.models.Block;

public class GlassBlock extends Block {

    public GlassBlock(int x, int y) {
        super(x, y);
    }

    @Override
    public void ballHit() {
        this.delete();
    }
}
