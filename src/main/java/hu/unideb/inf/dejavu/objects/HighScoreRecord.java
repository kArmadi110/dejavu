package hu.unideb.inf.dejavu.objects;

/**
 * Egy eredménytábla sort ábrázoló objektum.
 * 
 * @author gergo
 *
 */
public class HighScoreRecord {
	/**
	 * Az eredménytáblában szereplő név.
	 */
	String name;
	/**
	 * Az eredménytáblában szereplő idő.
	 */
	String time;
	/**
	 * Az eredménytáblában szereplő kattintásszám.
	 */
	int clicks = 0;
	/**
	 * Az játékméret.
	 */
	int dimension = 0;

	/**
	 * 
	 * Az eredménytábla paraméterezett konstruktora.
	 * 
	 * @param name
	 *            Az eredménytáblában szereplő név.
	 * @param time
	 *            Az eredménytáblában szereplő idő.
	 * @param clicks
	 *            Az eredménytáblában szereplő kattintásszám.
	 * @param dimension
	 *            Az játékméret.
	 */
	public HighScoreRecord(String name, String time, int clicks, int dimension) {
		super();
		this.name = name;
		this.time = time;
		this.clicks = clicks;
		this.dimension = dimension;
	}

	/**
	 * Visszadja a felhasználónevet.
	 * 
	 * @return visszaadja a felhazsnálónevet
	 */
	public String getName() {
		return name;
	}

	/**
	 * Beállítja a felhasználónevet.
	 * 
	 * @param name
	 *            a beállítandó felhazsnálónév
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * visszaadja a játékidőt.
	 * 
	 * @return a játékidő
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Beállítja a játékidőt.
	 * 
	 * @param time
	 *            a beállítandó játékidő
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Visszaadja a kattintások számát.
	 * 
	 * @return A kattintások száma
	 */
	public int getClicks() {
		return clicks;
	}

	/**
	 * Beállítja a kattintások számát.
	 * 
	 * @param clicks
	 *            a beállítandó kattintásszám
	 */
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	/**
	 * Visszaadja a játékméretet.
	 * 
	 * @return a játékméret
	 */
	public int getDimension() {
		return dimension;
	}

	/**
	 * Beállítja a játékméretet.
	 * 
	 * @param dimension
	 *            a játékméret
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clicks;
		result = prime * result + dimension;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		HighScoreRecord other = (HighScoreRecord) obj;
		if (clicks != other.clicks)
			return false;
		if (dimension != other.dimension)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	

}
