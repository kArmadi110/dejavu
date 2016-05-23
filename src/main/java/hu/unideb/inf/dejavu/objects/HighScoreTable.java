package hu.unideb.inf.dejavu.objects;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * Az eredménytáblát reprezentáló objektum.
 * 
 * @author gergo
 *
 */
public class HighScoreTable {
	/**
	 * Rekordokat tartalmazó lista.
	 */
	List<HighScoreRecord> table;

	/**
	 * Az eredménytáblát reprezentáló objektum.
	 * 
	 * @param table
	 *            rekordokat tartalmazó lista
	 */
	public HighScoreTable(List<HighScoreRecord> table) {
		super();
		this.table = table;
	}

	/**
	 * Visszaadja a rekordok listáját.
	 * 
	 * @return a rekordok listája
	 */
	public List<HighScoreRecord> getTable() {
		return table;
	}

	/**
	 * Rendezés idő szerint.
	 * 
	 * @param dim
	 *            játékméret
	 */
	public void sortByTime(String dim) {
		int dimension = Integer.parseInt(dim.substring(0, 1));
		table = table.stream().sorted((o1, o2) -> o1.getTime().compareTo(o2.getTime()))
				.filter(p -> p.getDimension() == dimension).collect(Collectors.toList());
	}

	/**
	 * Rendezés kattintásszám szerint.
	 * 
	 * @param dim
	 *            játékméret
	 */
	public void sortByClick(String dim) {
		int dimension = Integer.parseInt(dim.substring(0, 1));
		table = table.stream().sorted((o1, o2) -> {
			return o1.getClicks() - o2.getClicks();
		}).filter(p -> p.getDimension() == dimension).collect(Collectors.toList());
	}
}
