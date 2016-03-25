package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.objects.Status;
import hu.unideb.inf.dejavu.objects.StopWatch;
import hu.unideb.inf.dejavu.objects.User;
import hu.unideb.inf.dejavu.objects.HighScoreTable;

public interface IData {

	/**
	 * Az adatbázis kapcsolat létrehozása.
	 * 
	 * @return Igazzal tér vissza ha a kapcsolatot sikerült létrehozni,
	 *         egyébként hamissal.
	 */
	public boolean connect();

	/**
	 * Az adatbázis kapcsolat bezárása.
	 * 
	 * @return Igazzal tér vissza ha sikerült bezárni a kapcsolatot, egyébként
	 *         hamissal
	 */
	public boolean close();

	/**
	 * Létrehozza a USERS táblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült létrehozni a táblát,
	 *         egyébként hamissal.
	 */
	public boolean createUsersTable();

	/**
	 * Létrehozza a STATUS táblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült létrehozni a táblát,
	 *         egyébként hamissal.
	 */
	public boolean createStatusTable();

	/**
	 * Létrehozza a HIGH_SCORES táblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült létrehozni a táblát,
	 *         egyébként hamissal.
	 */
	public boolean createHighScoresTable();
	
	/**
	 * Visszaadja, hogy létezik e a HIGH_SCORE tábla vagy sem.
	 * 
	 * @param table
	 *            A lekérdezni kívánt tábla.
	 * @return Igaz igazságértékkel tér vissza, ha létezik a tábla, egyébként
	 *         hamissal.
	 */
	public boolean isHighScoreTableExist();

	/**
	 * Visszaadja, hogy létezik e a STATUS tábla vagy sem.
	 * 
	 * @param table
	 *            A lekérdezni kívánt tábla.
	 * @return Igaz igazságértékkel tér vissza, ha létezik a tábla, egyébként
	 *         hamissal.
	 */
	public boolean isStatusTableExist();
	
	/**
	 * Visszaadja, hogy létezik e a USER tábla vagy sem.
	 * 
	 * @param table
	 *            A lekérdezni kívánt tábla.
	 * @return Igaz igazságértékkel tér vissza, ha létezik a tábla, egyébként
	 *         hamissal.
	 */
	public boolean isUserTableExist();

	/**
	 * Jelenlegi játékállás elmentése.
	 * 
	 * A {@code saveStatus} metódus elmenti a jelenlegi játékállást, ha nincs
	 * létrehozva megfelelő tábla akkor készít.
	 * 
	 * @param status
	 *            A menteni kívánt státusz.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült elmenteni a
	 *         játékállást, egyébként hamissal.
	 */
	public boolean saveStatus(Status status);

	/**
	 * Eredménytábla frissítése.
	 * 
	 * A updateHighScores metódus frissíti az eredménytáblát, ha nincs
	 * létrehozva megfelelő tábla akkor készít.
	 * 
	 * @param sw
	 *            A menteni kívánt idő.
	 * 
	 * @param name
	 *            A menteni kívánt felhasználónév.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült a frissítés,
	 *         egyébként hamissal.
	 */
	public boolean updateHighScores(String sw, String name);

	/**
	 * Visszaadja a STATUS táblában lementett időt.
	 * 
	 * @param user
	 *            A betölteni kívánt játékmentés tulajdonosának neve.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült betölteni a
	 *         menstést, egyébként hamissal.
	 */
	public StopWatch getTime(User user);
	
	/**
	 * Visszaadja az eredménytábla adatait.
	 * 
	 * Visszaadja az eredménytábla adatait rendezés nélkül.
	 * 
	 * @return Az eredménytábla adatai.
	 */
	public HighScoreTable getHighScores();
	
	/**
	 * Visszaadja a kívánt státuszt.
	 * 
	 * @param user
	 *            A betölteni kívánt játékmentés tulajdonosa.
	 * 
	 * @return Az {@code name} A megfelelő státusszal tér vissza.
	 */
	public Status loadStatus(User user);

	/**
	 * Visszaadja, hogy létezik e már a kívánt felhasználónév.
	 * 
	 * Visszaadja, hogy létezik e már a kívánt felhasználónév, ha nincs USER
	 * tábla akkor létrehozza.
	 * 
	 * @param user
	 *            A ellenőrizendő felhasználó.
	 * 
	 * @return Igaz ha létezik a felhasználónév, egyébként hamis.
	 */
	public boolean isUserExist(User user);

	/**
	 * Visszaadja, hogy létezik e a falhasználónak játékmentése.
	 * 
	 * Visszaadja, hogy létezik e már a kívánt játékmentés, ha nincs STATUS
	 * tábla akkor létrehozza.
	 * 
	 * @param user
	 *            A ellenőrizendő felhasználó.
	 * 
	 * @return Igaz ha létezik a játékmentés, egyébként hamis.
	 */
	public boolean isStatusExist(User user);

	/**
	 * Törli a játékmentést.
	 * 
	 * @param user
	 *            A törlendő játékmentés tulajdonosa.
	 * 
	 * @return Igaz ha sikeres a törlés, egyébként hamis.
	 */
	public boolean removeStatus(User user);

	/**
	 * Új profilt hoz létre.
	 * 
	 * Új profilt hoz létre, amennyiben nem létezik a felhasználónév. Ha nem
	 * létezik a USER tábla akkor létrehozza.
	 * 
	 * @param user
	 *            A felhasználó.

	 * @return Igaz ha sikerült létrehozni a profilt, egyébként hamis.
	 **/
	public boolean addProfile(User user);

	/**
	 * Betölti a profilt.
	 * 
	 * Név és jelszó alapján betölti a profilt.
	 * 
	 * @param user
	 *            A felhasználó.
	 * 
	 * @return Igazzal tér vissza, ha sikerült betölteni a profilt, egyébként
	 *         hamissal.
	 */
	public boolean loadProfile(User user);
}
