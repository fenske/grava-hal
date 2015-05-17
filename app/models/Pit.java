package models;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

@MappedSuperclass
public class Pit extends Model {

    @Id
    protected String pitId;

    protected int leavesQty;

    protected Pit(int leavesQty) {
        this.leavesQty = leavesQty;
    }

    public void setLeavesQty(int leavesQty) {
        this.leavesQty = leavesQty;
    }

    public int getLeavesQty() {
        return leavesQty;
    }

    public boolean isEmpty() {
        return leavesQty == 0;
    }
}