package models;

public class Turn {

    private final Game game;
    private final Player owner;
    private final int pitIndex;

    public Turn(Game game, Player owner, int pitIndex) {
        this.game = game;
        this.owner = owner;
        this.pitIndex = pitIndex;
    }

    public void proceed() {
        Player curPlayer = owner;
        CommonPit selectedPit = curPlayer.getPits().get(pitIndex);
        int stonesLeft = selectedPit.emptyPit();

        SowState state = new SowState(stonesLeft, false);
        int curIndex = pitIndex + 1;

        while (state.getLeavesQty() > 0) {
            state = sow(curPlayer, state.getLeavesQty(), curIndex, curPlayer.equals(owner));
            if (state.getLeavesQty() > 0) {
                curPlayer = selectNextPlayer(curPlayer);
                curIndex = 0;
            }
        }

        if (game.isOver()) {
            game.finish();
        } else {
            game.setActivePlayer(getActivePlayer(state));
        }
    }

    private Player selectNextPlayer(Player from) {
        int index = game.getPlayers().indexOf(from);
        return game.getPlayers().get((index + 1) % game.getPlayers().size());
    }

    private Player getActivePlayer(SowState sowState) {
        if (sowState.getLastInGravaHal()) {
            return game.getActivePlayer();
        } else {
            return selectNextPlayer(game.getActivePlayer());
        }
    }

    private SowState sow(Player player, int qtyToSow, int pitPos, boolean includeGravaHal) {
        boolean isLastInGravaHal = false;

        int pitToken = pitPos;
        while(qtyToSow > 0 && pitToken < player.getPits().size()) {
            qtyToSow = sowInCommonPit(player, qtyToSow, pitToken);
            pitToken++;
        }

        if (qtyToSow == 0 &&
                pitToken <= player.getPits().size() &&
                player.getPits().get(pitToken - 1).getLeavesQty() == 1 &&
                includeGravaHal) {
            stealStones(pitToken);

        } else if(qtyToSow > 0 && includeGravaHal) {
            qtyToSow = sowInGravaHal(player, qtyToSow);
            isLastInGravaHal = true;
        }

        return new SowState(qtyToSow, isLastInGravaHal);
    }

    private int sowInCommonPit(Player player, int qtyToSow, int pos) {
        Pit pit = player.getPits().get(pos);
        pit.setLeavesQty(pit.getLeavesQty() + 1);
        return qtyToSow - 1;
    }

    private void stealStones(int pitToken) {
        int stolenQty = 0;

        for (Player p : game.getPlayers()) {
            if (!p.equals(owner)) {
                stolenQty += p.getPits().get(pitToken - 1).emptyPit();
            }
        }

        int ownQty = owner.getPits().get(pitToken - 1).emptyPit();
        GravaHal ownerGravaHal = owner.getGravaHal();
        ownerGravaHal.setLeavesQty(ownerGravaHal.getLeavesQty() + ownQty + stolenQty);
    }

    private int sowInGravaHal(Player player, int qtyToSow) {
        GravaHal gravaHal = player.getGravaHal();
        gravaHal.setLeavesQty(gravaHal.getLeavesQty() + 1);
        return qtyToSow - 1;
    }
}
