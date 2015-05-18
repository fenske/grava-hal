package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.Model;

@Entity
public class Game extends Model {

    @Id
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    private Player activePlayer;

    @ManyToMany(cascade = CascadeType.ALL)
    private final List<Player> players;

    public Game(List<Player> players) {
        this.players = Collections.unmodifiableList(players);
        activePlayer = players.get(0);
    }

    public void round(Turn turn) {
        turn.proceed();
    }

    public boolean isActivePit(Player player, int pitIndex) {
        return player.equals(activePlayer) && player.isNotEmpty(pitIndex);
    }

    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean isOver() {
        nextPlayer :
        for (Player player : players) {
            for (CommonPit pit : player.getPits()) {
                if (pit.getLeavesQty() != 0) {
                    continue nextPlayer;
                }
            }
            return true;
        }
        return false;
    }

    public void finish() {
        for (Player player : players) {
            for (CommonPit pit : player.getPits()) {
                if (pit.getLeavesQty() != 0) {
                    int leavesQty = pit.emptyPit();
                    int oldQty = player.getGravaHal().getLeavesQty();
                    player.getGravaHal().setLeavesQty(oldQty + leavesQty);
                }
            }
        }
    }
}
