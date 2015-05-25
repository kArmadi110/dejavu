package hu.unideb.inf.dejavu;

import java.util.List;

import hu.unideb.inf.dejavu.objects.Card;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

	@Test
	public void testGetCard() {
		Game game = new Game();
		game.dimension = 4;
		game.cards = new Card[4][4];

		assertEquals(game.cards[0][0], game.getCard(0, 0));
	}

	@Test
	public void testSetDim() {
		Game game = new Game();
		assertEquals(0, game.getDim());
		game.setDim(4);
		assertEquals(4, game.getDim());
	}

	@Test
	public void testGetDim() {
		Game game = new Game();
		assertEquals(0, game.getDim());
		game.setDim(4);
		assertEquals(4, game.getDim());
	}

	@Test
	public void testIsSetDim() {
		Game game = new Game();
		assertEquals(false, game.isSetDim());
		game.setDim(4);
		assertEquals(true, game.isSetDim());
	}

	@Test
	public void testMatrixSize() {
		Game game = new Game();

		List<Integer> matrix = game.matrixSize(17);

		for (int i : matrix) {
			assertEquals(true, i < Math.sqrt(17) && i % 2 == 0);
		}
	}

	@Test
	public void testIsEnd() {
		Game game = new Game();
		game.cards = new Card[4][4];
		game.setDim(4);

		for (int i = 0; i < game.getDim(); i++)
			for (int j = 0; j < game.getDim(); j++)
				game.cards[i][j] = new Card();

		assertEquals(false, game.isEnd());

		for (int i = 0; i < game.getDim(); i++)
			for (int j = 0; j < game.getDim(); j++)
				game.cards[i][j].setClicked(false);

		assertEquals(true, game.isEnd());
	}

	@Test
	public void testIsPerfectName() {
		Game game = new Game();
		assertEquals(false, game.isPerfectName("iam346?"));
		assertEquals(true, game.isPerfectName("iam346"));
	}
}
