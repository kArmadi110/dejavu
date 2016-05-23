package hu.unideb.inf.dejavu.objects;

/**
 * Díjakat megvalósító osztály.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + prize;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Achievement other = (Achievement) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (prize != other.prize)
			return false;
		return true;
	}
	
	

}
