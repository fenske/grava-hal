package models;

public class SowState {

    private final int leavesQty;
    private final boolean isLastInGravaHal;

    public SowState(int leavesQty, boolean isLastInGravaHal) {
        this.leavesQty = leavesQty;
        this.isLastInGravaHal = isLastInGravaHal;
    }

    public int getLeavesQty() {
        return leavesQty;
    }

    public boolean getLastInGravaHal() {
        return isLastInGravaHal;
    }
}