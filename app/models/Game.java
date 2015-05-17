package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Game extends Model {

    @Id
    private String id;

    private String activePlayerName;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Player> players;

    public Game(List<Player> players) {
        this.players = players;
        activePlayerName = players.get(0).getName();
    }

    public void round(Turn turn) {
        turn.proceed();
    }

    public boolean isActivePit(String playerName, int pitIndex) {
        Player player = getPlayerByName(playerName);
        return playerName.equals(activePlayerName) && player.isNotEmpty(pitIndex);
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
        return new ArrayList<>(players);
    }

    public String getActivePlayerName() {
        return activePlayerName;
    }

    public void setActivePlayerName(String activePlayerName) {
        this.activePlayerName = activePlayerName;
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
