package hu.unideb.inf.dejavu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.unideb.inf.dejavu.controller.Game;
import hu.unideb.inf.dejavu.objects.Achievement;
import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.HighScoreRecord;
import hu.unideb.inf.dejavu.objects.HighScoreTable;
import hu.unideb.inf.dejavu.objects.Pack;
import hu.unideb.inf.dejavu.objects.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

	@Test
	public void testGetCard() {
		Game game = new Game();
		game.mainStatus.setDimension(4);
		game.mainStatus.setPack(new Pack(new Card[4][4], 4));

		assertEquals(game.mainStatus.getPack().getCard(0, 0), game.getCard(0, 0));
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
	public void testMatrixSize() {// !
		Game game = new Game();

		List<Integer> matrix = game.matrixSize(17);

		for (int i : matrix) {
			assertEquals(true, i < Math.sqrt(17) && i % 2 == 0);
		}
	}

	@Test
	public void testIsEnd() {
		Game game = new Game();
		game.mainStatus.getPack().setCards(new Card[4][4]);
		;
		game.setDim(4);

		for (int i = 0; i < game.getDim(); i++)
			for (int j = 0; j < game.getDim(); j++)
				game.mainStatus.getPack().setCard(new Card(), i, j);

		assertEquals(false, game.isEnd());

		for (int i = 0; i < game.getDim(); i++)
			for (int j = 0; j < game.getDim(); j++)
				game.mainStatus.getPack().getCard(i, j).setClicked(false);

		assertEquals(true, game.isEnd());
	}

	@Test
	public void testIsPerfectName() {
		Game game = new Game();
		assertEquals(false, game.isPerfectName("iam346?"));
		assertEquals(true, game.isPerfectName("iam346"));
	}

	@Test
	public void getAchievementTest() {
		Game game = new Game();
		List<Achievement> result = new ArrayList<Achievement>();
		User a=new User("a","a");
		List<HighScoreRecord> hsr = new ArrayList<HighScoreRecord>();
		hsr.addAll(Arrays.asList(new HighScoreRecord("a", "00:04", 6, 2), 
				new HighScoreRecord("b", "00:04", 6, 4),
				new HighScoreRecord("a", "00:02", 8, 4),
				new HighScoreRecord("c", "00:12", 12, 2), 
				new HighScoreRecord("d", "00:07", 12, 4),
				new HighScoreRecord("a", "00:16", 2, 2)));

		HighScoreTable hstTime = new HighScoreTable(hsr);
		HighScoreTable hstClick = new HighScoreTable(hsr);
		hstTime.sortByTime("2x2");
		hstClick.sortByClick("2x2");
		
		result= Arrays.asList(new Achievement("2Idő", 1),new Achievement("2Kattintás", 1));
		
		assertEquals(game.getAchievement("2", hstTime, hstClick,a),result);
		
		hstTime = new HighScoreTable(hsr);
		hstClick = new HighScoreTable(hsr);
		hstTime.sortByTime("4x4");
		hstClick.sortByClick("4x4");
		
		result= Arrays.asList(new Achievement("4Idő", 1),new Achievement("4Kattintás", 2));

		assertEquals(game.getAchievement("4", hstTime, hstClick,a),result);

	}

}
