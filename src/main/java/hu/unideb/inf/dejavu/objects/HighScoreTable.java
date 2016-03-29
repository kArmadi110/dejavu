package hu.unideb.inf.dejavu.objects;

import java.util.Collections;
import java.util.List;

public class HighScoreTable {
	List<Pair> table;

	public HighScoreTable(List<Pair> table) {
		super();
		this.table = table;
	}

	public List<Pair> getTable() {
		return table;
	}

	public void sort() {

		Collections.sort(table);

	}
}
