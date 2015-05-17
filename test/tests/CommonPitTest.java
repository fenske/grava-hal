package tests;

import models.CommonPit;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommonPitTest {

    private CommonPit pit;
    private int pitIndex = 0;

    @Before
    public void setUp() {
        pit = new CommonPit(pitIndex);
    }

    @Test
    public void testConstructor() {
        assertThat(pit.getLeavesQty(), is(equalTo(6)));
    }

    @Test
    public void testGetIndex() {
        assertThat(pit.getIndex(), is(equalTo(pitIndex)));
    }

    @Test
    public void testEmptyPit() {
        assertThat(pit.emptyPit(), is(equalTo(6)));
        assertThat(pit.isEmpty(), is(true));
        assertThat(pit.getLeavesQty(), is(equalTo(0)));
    }

    @Test
    public void testSetLeavesQty() {
        pit.setLeavesQty(10);
        assertThat(pit.getLeavesQty(), is(equalTo(10)));
    }

}
