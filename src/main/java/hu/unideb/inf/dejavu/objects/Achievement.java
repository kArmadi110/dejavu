package hu.unideb.inf.dejavu.objects;

/**
 * Díjakat reprezentáló osztály.
 * 
 * @author gergo
 *
 */
public class Achievement {
	/**
	 * A díj leírása.
	 */
	private String description;
	/**
	 * A helyezés.
	 */
	private int prize;

	/**
	 * A díjat létrehozó paraméterezett konstruktor.
	 * 
	 * @param description
	 *            a leírás a díjhoz
	 * @param prize
	 *            a helyezés
	 */
	public Achievement(String description, int prize) {
		this.description = description;
		this.prize = prize;
	}

	/**
	 * Visszatér a díj leírásával.
	 * 
	 * @return Visszatér a Díj leírásával
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Beállítja a díj leírását.
	 * 
	 * @param description
	 *            beállítja a díj leírását
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Visszaadja a helyezést.
	 * 
	 * @return visszaadja a helyezést.
	 */
	public int getPrize() {
		return prize;
	}

	/**
	 * Beállítja a helyezést.
	 * 
	 * @param prize
	 *            a beállítandó helyezés
	 */
	public void setPrize(int prize) {
		this.prize = prize;
	}

}
