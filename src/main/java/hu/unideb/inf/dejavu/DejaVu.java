package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.gui.Ground;

/**
 * A main metódust tartalmazó osztály.
 * 
 * A {@code DejaVu} osztály tartalmazza a játékban lévő objektumokat.
 * 
 * @author iam346
 *
 */
public class DejaVu {
	/**
	 * Az aktuális ablak.
	 */
	public static Ground ground;

	/**
	 * A program main metóduse.
	 * 
	 * @param args
	 *            Argumentumok.
	 */
	public static void main(String[] args) {
		DataB.connect();

		ground = new Ground();
		ground.setVisible(true);

	}

}
