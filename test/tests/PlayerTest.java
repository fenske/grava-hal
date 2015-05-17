package tests;

import models.CommonPit;
import models.Player;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("ivanov");
    }

    @Before
    public void testConstructor() {
        for (CommonPit pit : player.getPits()) {
            assertThat(pit.getLeavesQty(), is(equalTo(6)));
        }
        assertThat(player.getGravaHal().getLeavesQty(), is(equalTo(0)));
    }
}
