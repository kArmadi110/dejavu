package hu.unideb.inf.dejavu.objects;

import java.util.TreeMap;

public class HighScoreTable {
	TreeMap<String, String> table;

	public HighScoreTable(TreeMap<String, String> table) {
		super();
		this.table = table;
	}

	public TreeMap<String, String> getTable() {
		return table;
	}
	
}
