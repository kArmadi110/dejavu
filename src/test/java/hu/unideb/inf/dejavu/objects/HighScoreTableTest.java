package hu.unideb.inf.dejavu.objects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HighScoreTableTest {
	@Test
	public void sortByTimeTest() {
		List<HighScoreRecord> hsr = new ArrayList<HighScoreRecord>();
		List<HighScoreRecord> result = new ArrayList<HighScoreRecord>();

		hsr.addAll(Arrays.asList(new HighScoreRecord("a", "00:04", 6, 2), 
				new HighScoreRecord("b", "00:04", 6, 4),
				new HighScoreRecord("c", "00:12", 12, 2), 
				new HighScoreRecord("d", "00:07", 12, 4),
				new HighScoreRecord("e", "00:05", 4, 2)));

		HighScoreTable hst = new HighScoreTable(hsr);
		hst.sortByTime("2");
		
		result=Arrays.asList(new HighScoreRecord("a", "00:04", 6, 2),
				new HighScoreRecord("e", "00:05", 4, 2),
				new HighScoreRecord("c", "00:12", 12, 2));
		assertEquals(hst.getTable(), result);

		hsr = Arrays.asList(new HighScoreRecord("a", "00:04", 6, 2), 
				new HighScoreRecord("b", "00:04", 6, 4),
				new HighScoreRecord("c", "00:12", 12, 2), 
				new HighScoreRecord("d", "00:07", 12, 4),
				new HighScoreRecord("e", "00:05", 4, 2));
		
		result = Arrays.asList(new HighScoreRecord("b", "00:04", 6, 4), new HighScoreRecord("d", "00:07", 12, 4));
		hst= new HighScoreTable(hsr);
		hst.sortByTime("4");
		assertEquals(hst.getTable(), result);
	}

	@Test
	public void sortByClickTest() {
		List<HighScoreRecord> hsr = new ArrayList<HighScoreRecord>();
		List<HighScoreRecord> result = new ArrayList<HighScoreRecord>();

		hsr.addAll(Arrays.asList(new HighScoreRecord("a", "00:04", 6, 2), 
				new HighScoreRecord("b", "00:04", 6, 4),
				new HighScoreRecord("c", "00:12", 12, 2), 
				new HighScoreRecord("d", "00:07", 12, 4),
				new HighScoreRecord("e", "00:05", 4, 2)));

		HighScoreTable hst = new HighScoreTable(hsr);
		hst.sortByClick("2");
		
		result=Arrays.asList(new HighScoreRecord("e", "00:05", 4, 2),
				new HighScoreRecord("a", "00:04", 6, 2),
				new HighScoreRecord("c", "00:12", 12, 2));
		assertEquals(hst.getTable(), result);

		
		result = Arrays.asList(new HighScoreRecord("b", "00:04", 6, 4), 
				new HighScoreRecord("d", "00:07", 12, 4));
		hst= new HighScoreTable(hsr);
		hst.sortByClick("4");
		assertEquals(hst.getTable(), result);
	}
}
