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

    public int getId() {
        return id;
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