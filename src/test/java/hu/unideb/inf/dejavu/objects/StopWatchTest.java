package hu.unideb.inf.dejavu.objects;

import static org.junit.Assert.*;
import org.junit.Test;

public class StopWatchTest {

	@Test
	public void testFromString() {
		StopWatch t1 = new StopWatch();

		assertEquals(false, t1.fromString("102:000"));
		assertEquals(true, t1.fromString("12:00"));
		assertEquals("12:00", t1.toString());
	}

	@Test
	public void testUpdate() {
		StopWatch t1 = new StopWatch();

		assertEquals(false, t1.update());
		t1.start -= 60000;
		assertEquals(true,t1.update());
	}
}
