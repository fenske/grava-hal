package tests;

import models.GravaHal;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GravaHalTest {

    GravaHal gravaHal;

    @Before
    public void setUp() {
        gravaHal = new GravaHal();
    }

    @Test
    public void testConstructor() {
        assertThat(gravaHal.isEmpty(), is(true));
        assertThat(gravaHal.getLeavesQty(), is(equalTo(0)));
    }
}
