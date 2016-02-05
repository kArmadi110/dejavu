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
	 * A logika
	 */
	public static Game game;
	/**
	 * A program main metódusa.
	 * 
	 * @param args
	 *            Parancssori argumentumok.
	 */
	public static void main(String[] args) {
		DataB.connect();
		
		game = new Game();
		
		ground = new Ground();
		ground.setVisible(true);

	}

}
