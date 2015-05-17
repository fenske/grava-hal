package models;

import javax.persistence.Entity;

@Entity
public class CommonPit extends Pit {

    private final int index;

    public int emptyPit() {
        int stonesCnt = leavesQty;
        leavesQty = 0;
        return stonesCnt;
    }

    public CommonPit(int index) {
        super(6);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
