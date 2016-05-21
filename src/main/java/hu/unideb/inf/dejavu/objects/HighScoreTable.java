package hu.unideb.inf.dejavu.objects;

import java.util.List;
import java.util.stream.Collectors;

public class HighScoreTable {
	List<HighScoreRecord> table;

	public HighScoreTable(List<HighScoreRecord> table) {
		super();
		this.table = table;
	}

	public List<HighScoreRecord> getTable() {
		return table;
	}

	public void sortByTime(String dim) {
		int dimension = Integer.parseInt(dim.substring(0, 1));
		table = table.stream().sorted((o1, o2) -> o1.getTime().compareTo(o2.getTime()))
				.filter(p -> p.getDimension() == dimension).collect(Collectors.toList());
	}

	public void sortByClick(String dim) {
		int dimension = Integer.parseInt(dim.substring(0, 1));
		table = table.stream().sorted((o1, o2) -> {
			return o1.getClicks() - o2.getClicks();
		}).filter(p -> p.getDimension() == dimension).collect(Collectors.toList());
	}
}
