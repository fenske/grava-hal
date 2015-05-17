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
            state = curPlayer.sow(state.getLeavesQty(), curIndex, curPlayer == owner);
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
}
