package models;

public class SowState {

    private final int leavesQty;
    private final boolean includeGravaHal;

    public SowState(int inHand_, boolean inGravaHal_) {
        leavesQty = inHand_;
        includeGravaHal = inGravaHal_;
    }

    public int getLeavesQty() {
        return leavesQty; }

    public boolean getIncludeGravaHal() {
        return includeGravaHal; }
}