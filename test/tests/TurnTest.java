package tests;

import models.CommonPit;
import models.Game;
import models.Player;
import models.Turn;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TurnTest {

    private Turn turn;
    private Game game;

    @Before
    public void setUp() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Ivanov"));
        players.add(new Player("Petrov"));
        game = new Game(players);
        turn = new Turn(game, players.get(0).getName(), 0);
    }

    @Test
    public void testProceed() {
        turn.proceed();
        List<CommonPit> pits = game.getPlayers().get(0).getPits();
        for (int i = 0; i < pits.size(); i++) {
            int testQty;
            if (i == 0) {
                testQty = 0;
            } else {
                testQty = 7;
            }
            CommonPit pit = pits.get(i);
            assertThat(pit.getLeavesQty(), is(equalTo(testQty)));
        }
        for (CommonPit pit : game.getPlayers().get(1).getPits()) {
            assertThat(pit.getLeavesQty(), is(equalTo(6)));
        }
    }
}
