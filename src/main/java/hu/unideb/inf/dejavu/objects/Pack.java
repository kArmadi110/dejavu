package hu.unideb.inf.dejavu.objects;

import java.util.List;

/**
 * A játéktér pakliját reprezentáló objektum.
 * 
 * @author gergo
 *
 */
public class Pack {
	/**
	 * Kártyák tömbje.
	 */
	Card[][] cards;
	/**
	 * A játékméret.
	 */
	int dimension;

	/**
	 * A paklit reprezentáló osztály paraméter nélküli konstruktora.
	 */
	public Pack() {
		dimension = 0;
	}

	/**
	 * A paklit reprezentáló osztály paraméterezett konstrukotra.
	 * 
	 * @param cards
	 *            Kártyák tömbje
	 * @param dimension
	 *            az aktuális játékméret.
	 */
	public Pack(Card[][] cards, int dimension) {
		super();
		this.cards = cards;
		this.dimension = dimension;
	}

	/**
	 * A paklit reprezentáló osztály paraméterezett konstrukotra.
	 * 
	 * @param cardList
	 *            Kártyák listája
	 * @param dimension
	 *            az aktuális játékméret.
	 */
	public Pack(List<Card> cardList, int dimension) {
		super();

		this.dimension = dimension;

		cards = new Card[dimension][dimension];

		for (int i = 0; i < cardList.size(); i++) {
			cards[cardList.get(i).getPosition().getFirst()][cardList.get(i).getPosition().getSecond()] = cardList
					.get(i);
		}

	}

	/**
	 * Eldönti, hogy megfelelőek e a kártyák elérési utjai.
	 * 
	 * @return igazzal tér vissza ha az elérési utak megfelelőek egyébként
	 *         hamissal
	 */
	public boolean isValid() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				if (!cards[i][j].getValue().exists() || cards[i][j].getValue().isDirectory()) {
					return false;
				}
		return true;

	}

	/**
	 * A kártyák tömbjével tér vissza.
	 * 
	 * @return a kártyák tömbje
	 */
	public Card[][] getCards() {
		return cards;
	}

	/**
	 * Beállítja a kártyákat.
	 * 
	 * @param cards
	 *            a beállítandó kártyák
	 */
	public void setCards(Card[][] cards) {
		this.cards = cards;
	}

	/**
	 * Visszaad egy kártyát.
	 * 
	 * @param x
	 *            a kártya sor koordinátája
	 * @param y
	 *            a kártya oszlop koordinátája
	 * @return a kívánt kártya
	 */
	public Card getCard(int x, int y) {
		return cards[x][y];
	}

	/**
	 * Beállít egy kártyát.
	 * 
	 * @param card
	 *            a beállítandó kártyas
	 * @param x
	 *            a kártya sor koordinátája
	 * @param y
	 *            a kártya oszlop koordinátája
	 */
	public void setCard(Card card, int x, int y) {
		this.cards[x][y] = card;
	}

	/**
	 * Visszaadja az adott játékméretet.
	 * 
	 * @return az adott játékméret
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * Beállítja az adott játékméretet.
	 * 
	 * @param dimension
	 *            a kívánt játékméret
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

}
