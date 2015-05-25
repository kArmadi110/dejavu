package hu.unideb.inf.dejavu.objects;

import static org.junit.Assert.*;
import org.junit.Test;

public class PositionTest {

	@Test
	public void testEquals() {
		Position pos1 = new Position(1, 1, 2);
		Position pos2 = new Position(1, 1, 2);
		Position pos3 = new Position(1, 1, 3);

		assertEquals(true, pos1.equals(pos2));
		assertEquals(false, pos2.equals(pos3));
	}

	@Test
	public void testSetPos() {
		Position pos1 = new Position(1, 1, 2);
		Position pos2 = new Position(5, 5, 5);
		pos2.setPos(1, 1, 2);
		assertEquals(pos2, pos1);
	}

	@Test
	public void testGetFirst() {
		Position pos1 = new Position(1, 1, 2);
		assertEquals(1, pos1.getFirst());
		Position pos2 = new Position(2, 1, 2);
		assertEquals(2, pos2.getFirst());
		Position pos3 = new Position(3, 1, 2);
		assertEquals(3, pos3.getFirst());
		Position pos4 = new Position(4, 1, 2);
		assertEquals(4, pos4.getFirst());
	}

	@Test
	public void testGetSecond() {
		Position pos1 = new Position(1, 1, 2);
		assertEquals(1, pos1.getSecond());
		Position pos2 = new Position(1, 2, 2);
		assertEquals(2, pos2.getSecond());
		Position pos3 = new Position(1, 3, 2);
		assertEquals(3, pos3.getSecond());
		Position pos4 = new Position(1, 4, 2);
		assertEquals(4, pos4.getSecond());
	}
}
