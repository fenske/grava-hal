package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class Player extends Model {

    @Id
    private int id;

    private final String name;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<CommonPit> pits;

    @OneToOne(cascade = CascadeType.ALL)
    private final GravaHal gravaHal;

    public Player(String name) {
        this.name = name;
        int playerLength = 6;

        pits = new ArrayList<>(playerLength);
        for(int i = 0; i < playerLength; i++) {
            CommonPit pit = new CommonPit(i);
            pits.add(pit);
        }

        gravaHal = new GravaHal();
    }

    public boolean isNotEmpty(int index) {
        return pits.get(index).getLeavesQty() > 0;
    }

    public SowState sow(int qtyToSow, int pitPos, boolean includeGravaHal) {
        boolean wasLastInGravaHal = false;

        while(qtyToSow > 0 && pitPos < pits.size()) {
            qtyToSow = sowInCommonPit(qtyToSow, pitPos);
            pitPos++;
        }

        if(qtyToSow > 0 && includeGravaHal) {
            qtyToSow = sowInGravaHal(qtyToSow);
            wasLastInGravaHal = true;
        }
        return new SowState(qtyToSow, wasLastInGravaHal);
    }

    private int sowInGravaHal(int qtyToSow) {
        gravaHal.setLeavesQty(gravaHal.getLeavesQty() + 1);
        qtyToSow--;
        return qtyToSow;
    }

    private int sowInCommonPit(int qtyToSow, int pos) {
        Pit pit = pits.get(pos);
        pit.setLeavesQty(pit.getLeavesQty() + 1);
        qtyToSow--;
        return qtyToSow;
    }

    public String getName() {
        return name;
    }

    public List<CommonPit> getPits() {
        return new ArrayList<>(pits);
    }

    public GravaHal getGravaHal() {
        return gravaHal;
    }
}