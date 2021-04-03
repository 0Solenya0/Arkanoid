package Arkanoid.Logic.models.Block;

import Arkanoid.Logic.models.Prize;

public class PrizeBlock extends Block {

    Prize.PrizeType prizeType;

    public PrizeBlock(int x, int y, Prize.PrizeType prizeType) {
        super(x, y);
        this.prizeType = prizeType;
    }

    @Override
    public void ballHit() {
        listener.listen(Events.ADDPRIZE + " " + prizeType.toString() + " " + (int) (this.x + (this.getWidth() - Prize.defaultWidth) / 2) + " " + this.y);
        this.delete();
    }
}
