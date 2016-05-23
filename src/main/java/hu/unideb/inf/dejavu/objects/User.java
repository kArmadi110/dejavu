package hu.unideb.inf.dejavu.objects;

/**
 * A felhasználót reprezentáló osztály.
 * 
 * @author gergo
 *
 */
public class User {
	/**
	 * Felhasználónév.
	 */
	String UserName;
	/**
	 * Jelszó.
	 */
	String Password;

	/**
	 * A felhasználót reprezentáló osztály paraméter nélküli konstruktora.
	 */
	public User() {
		UserName = "";
		Password = "";
	}

	/**
	 * A felhasználót reprezentáló osztály paraméterezett konstruktora.
	 * 
	 * @param userName
	 *            felhasználónév
	 * @param password
	 *            jelszó
	 */
	public User(String userName, String password) {
		super();
		UserName = userName;
		Password = password;
	}

	/**
	 * Visszaadja a felhasználónevet.
	 * 
	 * @return visszatér a felhazsnálónévvel
	 */
	public String getUserName() {
		return UserName;
	}

	/**
	 * Beállítja a felhasználónevet.
	 * 
	 * @param userName
	 *            a felhasználónév
	 */
	public void setUserName(String userName) {
		UserName = userName;
	}

	/**
	 * Visszatér a jelszóval.
	 * 
	 * @return visszatér a jelszóval
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * Beállítja a jelszót.
	 * 
	 * @param password
	 *            a jelszó.
	 */
	public void setPassword(String password) {
		Password = password;
	}

}
