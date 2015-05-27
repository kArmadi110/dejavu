package hu.unideb.inf.dejavu.objects;


import java.io.File;

/**
 * A {@code Kártya} osztály reprezentál egy kártyát.
 * 
 * Tartalmazza a kép elérési útját, amit a felhasználó választott, azt hogy
 * lehet e még rá kattintani, és a pozicióját a mátrixban.
 * 
 * @author iam346
 *
 */
public class Card {
	/**
	 * Az kép elérési útja amit a felhasználó választott.
	 */
	private File value;
	/**
	 * Igazságérték, mely azt mutatja, hogy a kártyára lehet e még kattintani.
	 */
	private boolean clicked;
	/**
	 * A kártya poziciója a mátrixban.
	 */
	private Position position;

	/**
	 * A {@code Card} osztály paraméter nélküli konstruktora, tesztelésnél alkalmazható.
	 */
	public Card() {
		position = new Position();
		clicked = true;
	}

	/**
	 * A {@code Card} osztály felparaméterezett konstruktora, amely a kártyák létrehozására
	 * és inicializálására szolgál.
	 * 
	 * @param value
	 *            A választott kép elérési útja.
	 * @param x
	 *            A mátrixbeli helyzet x koordinátája.
	 * @param y
	 *            A mátrixbeli helyzet y koordinátája.
	 * @param dimension
	 *            A mátrix dimenziója.
	 */
	public Card(File value, int x, int y, int dimension) {
		this.value = value;
		clicked = true;
		position = new Position(x, y, dimension);
	}

	/**
	 * A kártya poziciója állítható vele.
	 * 
	 * A kártya poziciója állítható vele, teszteléshez alkalmazandó.
	 * 
	 * @param x
	 *            a mátrixbeli helyzet x koordinátája.
	 * @param y
	 *            a mátrixbeli helyzet y koordinátája.
	 * @param dimension
	 *            a mátrix dimenziója.
	 */
	void setPosition(int x, int y, int dimension) {
		position.setPos(x, y, dimension);
	}

	/**
	 * A kártya kattinthatóságának állapota állítható vele.
	 * 
	 * @param b
	 *            Ha hamis akkor a kártya rögzített, nem lehet kattintani rá
	 *            többet, egyébként lehet.
	 */
	public void setClicked(boolean b) {
		clicked = b;
	}
	/**
	 * Visszaadja, a kártya kattinthatóságának állapotát.
	 * 
	 * @return A kártya kattinthatóságának állapota.
	 */

	public boolean isClicked() {
		return clicked;
	}
	/**
	 * Visszaadja, hogy a kártya pozicióját.
	 * 
	 * @return A kártya poziciója.
	 */
	public Position getPosition() {
		return position;
	}
	/**
	 * Visszaadja a kártyához kiválasztott kép elérési útvonalát.
	 * 
	 * @return A kártyához kiválasztott kép elérési útvonala.
	 */
	public File getValue() {
		return value;
	}

}