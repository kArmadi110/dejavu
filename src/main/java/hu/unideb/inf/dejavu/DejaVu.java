package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.gui.DejaVuGUI;

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
		
		javafx.application.Application.launch(DejaVuGUI.class);
	}

}
