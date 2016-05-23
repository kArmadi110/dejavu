package hu.unideb.inf.dejavu.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Az aktuális játékállást reprezentáló osztály.
 * 
 * @author gergo
 *
 */
public class Status {
	/**
	 * Felhasználó.
	 */
	User user;
	/**
	 * Pakli.
	 */
	Pack pack;
	/**
	 * Játékidő.
	 */
	StopWatch time;
	/**
	 * Játékméret.
	 */
	int dimension;
	/**
	 * A felfordított kártyák pozícióinak listája.
	 */
	public List<Position> upCards = new ArrayList<Position>();
	/**
	 * A lefordított kártyák pozícióinak listája.
	 */
	public List<Position> downCards = new ArrayList<Position>();

	/**
	 * 
	 * A játékállst reprezentáló osztály paraméterezett konstruktora.
	 * 
	 * @param user felhazsnáló
	 * @param pack pakli
	 * @param time játékidő
	 * @param dimension dimenzió
	 */
	public Status(User user, Pack pack, StopWatch time, int dimension) {
		super();
		this.user = user;
		this.pack = pack;
		this.time = time;
		this.dimension = dimension;
	}
/**
 * Visszaadja a felhasználót.
 * @return a felhasználó
 */
	public User getUser() {
		return user;
	}
/**
 * Beállítja a felhazsnálót.
 * @param user a kívánt felhazsnáló.
 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * Visszaadja a paklit.
	 * @return a pakli
	 */
	public Pack getPack() {
		return pack;
	}
	/**
	 * Beállítja a paklit.
	 * @param pack a kívánt paklit.
	 */
	public void setPack(Pack pack) {
		this.pack = pack;
	}
/**
 * Visszaadja a kívánt játékidőtidőt.
 * @return a játékidőtidő
 */
	public StopWatch getTime() {
		return time;
	}
/**
 * Beállítja a kívánt időt.
 * @param time a játékidő
 */
	public void setTime(StopWatch time) {
		this.time = time;
	}
/**
 * Visszaadja a játékméretet.
 * @return a játékméret
 */
	public int getDimension() {
		return dimension;
	}
/**
 * Beállítja a játékméretet.
 * @param dimension a kívánt játékméret
 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

}
