package tests;

import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class GameTest {

    private Game game;
    List<Player> players;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Ivanov"));
        players.add(new Player("Petrov"));
        game = new Game(players);
    }

    @Test
    public void testConstructor() {
        assertThat(game.getPlayers().size(), is(equalTo(2)));
        assertThat(game.getActivePlayer().getName(), is(equalTo("Ivanov")));
    }

    @Test
    public void testIsActive() {
        for (Player player : game.getPlayers()) {
            game.setActivePlayer(player);
            for (CommonPit pit : player.getPits()) {
                assertThat(game.isActivePit(player, pit.getIndex()), is(true));
            }
        }
        Player chosenPlayer = game.getPlayers().get(0);
        CommonPit selectedPit = chosenPlayer.getPits().get(0);
        Turn turn = new Turn(game, chosenPlayer.getName(), selectedPit.getIndex());
        assertThat(game.isActivePit(chosenPlayer, selectedPit.getIndex()), is(false));
    }

    @Test
    public void testGetPlayerByName() {
        assertThat(game.getPlayers().get(0), is(equalTo(game.getPlayerByName("Ivanov"))));
        assertThat(game.getPlayers().get(1), is(equalTo(game.getPlayerByName("Petrov"))));
        assertThat(game.getPlayerByName("Sidorov"), is(nullValue()));
    }

    @Test
    public void testFinish() {
        game.finish();
        for (Player player : game.getPlayers()) {
            for (CommonPit pit : player.getPits()) {
                assertThat(pit.getLeavesQty(), is(equalTo(0)));
            }
            assertThat(player.getGravaHal().getLeavesQty(), is(equalTo(36)));
        }
    }

    @Test
    public void testIsOver() {
        assertThat(game.isOver(), is(false));
        game.finish();
        assertThat(game.isOver(), is(true));
    }

}
