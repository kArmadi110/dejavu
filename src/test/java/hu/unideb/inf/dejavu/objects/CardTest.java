package hu.unideb.inf.dejavu.objects;


import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

	@Test
	public void testIsClicked() {
		Card card1 = new Card();

		assertEquals(true, card1.isClicked());
		card1.setClicked(false);
		assertEquals(false, card1.isClicked());
	}

	@Test
	public void testSetClicked() {
		Card card1 = new Card();

		assertEquals(true, card1.isClicked());
		card1.setClicked(false);
		assertEquals(false, card1.isClicked());
	}

	@Test
	public void testSetPosition() {
		Card card1 = new Card();
		card1.setPosition(1, 1, 2);
		assertEquals(new Position(1, 1, 2), card1.getPosition());
	}

	@Test
	public void testGetPosition() {
		Card card1 = new Card();
		card1.setPosition(1, 1, 2);
		assertEquals(new Position(1, 1, 2), card1.getPosition());
	}

}
