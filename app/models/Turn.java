package models;

public class Turn {

    private final Game game;
    private final String playerName;
    private final int pitIndex;

    public Turn(Game game, String playerName, int pitIndex) {
        this.game = game;
        this.playerName = playerName;
        this.pitIndex = pitIndex;
    }

    public void proceed() {
        Player owner = game.getPlayerByName(playerName);

        Player curPlayer = owner;
        CommonPit pit = curPlayer.getPits().get(pitIndex);
        int stonesLeft = pit.emptyPit();

        SowState state = new SowState(stonesLeft, true);
        int curIndex = pitIndex + 1;

        while (state.getLeavesQty() > 0) {
            state = sow(curPlayer, state.getLeavesQty(), curIndex, curPlayer == owner);
            if (state.getLeavesQty() > 0) {
                curPlayer = selectNextPlayer(curPlayer);
                curIndex = 0;
            }
        }

        if (game.isOver()) {
            game.finish();
        } else {
            game.setActivePlayerName(getActivePlayer(state));
        }

    }

    private Player selectNextPlayer(Player from) {
        int index = game.getPlayers().indexOf(from);
        return game.getPlayers().get((index + 1) % game.getPlayers().size());
    }

    private String getActivePlayer(SowState sowState) {
        if (sowState.getIncludeGravaHal()) {
            return game.getActivePlayerName();
        } else {
            Player curActivePlayer = game.getPlayerByName(game.getActivePlayerName());
            return selectNextPlayer(curActivePlayer).getName();
        }
    }

    private SowState sow(Player player, int qtyToSow, int pitPos, boolean includeGravaHal) {
        boolean wasLastInGravaHal = false;

        int pitToken = pitPos;
        while(qtyToSow > 0 && pitToken < player.getPits().size()) {
            qtyToSow = sowInCommonPit(player, qtyToSow, pitToken);
            pitToken++;
        }

        if (qtyToSow == 0 &&
            pitToken <= player.getPits().size() &&
            player.getPits().get(pitToken - 1).getLeavesQty() == 1 &&
            includeGravaHal) {
            int stolenQty = 0;
            for (Player p : game.getPlayers()) {
                if (!p.getName().equals(playerName)) {
                    stolenQty += p.getPits().get(pitToken - 1).emptyPit();
                }
            }
            Player owner = game.getPlayerByName(playerName);
            int ownQty = owner.getPits().get(pitToken - 1).emptyPit();
            GravaHal ownerGravaHal = owner.getGravaHal();
            ownerGravaHal.setLeavesQty(
                    ownerGravaHal.getLeavesQty() +
                            ownQty +
                            stolenQty
            );
        } else if(qtyToSow > 0 && includeGravaHal) {
            qtyToSow = sowInGravaHal(player, qtyToSow);
            wasLastInGravaHal = true;
        }
        return new SowState(qtyToSow, wasLastInGravaHal);
    }

    private int sowInGravaHal(Player player, int qtyToSow) {
        GravaHal gravaHal = player.getGravaHal();
        gravaHal.setLeavesQty(gravaHal.getLeavesQty() + 1);
        qtyToSow--;
        return qtyToSow;
    }

    private int sowInCommonPit(Player player, int qtyToSow, int pos) {
        Pit pit = player.getPits().get(pos);
        pit.setLeavesQty(pit.getLeavesQty() + 1);
        qtyToSow--;
        return qtyToSow;
    }


}
