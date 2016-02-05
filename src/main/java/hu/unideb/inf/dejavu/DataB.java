package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.StopWatch;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@code DataB} osztály segítségével csatlakozhatunk az adatbázishoz.
 * 
 * @author iam346
 *
 */
public class DataB {
	/**
	 * A {@code DataB} osztály naplózója.
	 */
	private static Logger logger = LoggerFactory.getLogger(DejaVu.class);
	/**
	 * Az adatbázishoz való belépéshez használt fehasználónév.
	 */
	private static String USERNAME = "";
	/**
	 * Az adatbázishoz való belépéshez használt jelszó.
	 */
	private static String PASS = "";
	/**
	 * Az adatbázishoz való belépéshez használt URL.
	 */
	private static String URL = "";
	/**
	 * Az adatbáziskapcsolat.
	 */
	private static Connection connection = null;

	/**
	 * Az adatbázis kapcsolat létrehozása.
	 * 
	 * @return Igazzal tér vissza ha a kapcsolatot sikerült létrehozni,
	 *         egyébként hamissal.
	 * */
	public static boolean connect() {
		if (connection != null)

			return true;
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		} catch (SQLException e) {
			logger.error("A Drivert nem sikerült betölteni.");

			return false;
		}

		logger.debug("A driver sikeresen betöltődött.");

		try {
			Properties properties = new Properties();

			try {
				InputStream inputStream = DataB.class.getClassLoader()
						.getResourceAsStream("project.properties");

				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}

			USERNAME = properties.getProperty("username");
			PASS = properties.getProperty("password");
			URL = properties.getProperty("url");

			connection = DriverManager.getConnection(URL, USERNAME, PASS);
		} catch (SQLException e) {
			logger.error("A kapcsolatot nem sikerült létrehozni.");

			return false;
		} catch (NullPointerException e) {
			logger.error("A kapcsolatot nem sikerült létrehozni.");
			logger.info("Hozzon létre egy megfelelő project.properties filet");

			return false;
		}

		logger.debug("A kapcsolat sikeresen létrejött.");

		return true;
	}

	/**
	 * Az adatbázis kapcsolat bezárása.
	 * 
	 * @return Igazzal tér vissza ha sikerült bezárni a kapcsolatot, egyébként
	 *         hamissal
	 * */
	public static boolean close() {
		try {
			if (connection != null && !connection.isClosed()) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("A kapcsolat bezárása sikertelen.");
				}
			} else
				return true;

		} catch (SQLException e) {
			logger.error("A kapcsolat már be van zárva.");

			return false;
		}

		logger.debug("A kapcsolat sikeresen bezárult.");

		return true;

	}

	/**
	 * Létrehozza a USERS táblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült létrehozni a táblát,
	 *         egyébként hamissal.
	 */

	private static boolean createUsersTable() {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE USERS (NAME VARCHAR2(100),PASS VARCHAR2(30))";
			statement.executeQuery(query);
			statement.executeQuery("commit");
		} catch (SQLException e) {
			logger.error("Users tábla létrehozása sikertelen.");

			return false;
		}
		logger.debug("Users tábla sikeresen létrehozva.");

		return true;
	}

	/**
	 * Létrehozza a STATUS táblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült létrehozni a táblát,
	 *         egyébként hamissal.
	 */
	private static boolean createStatusTable() {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE STATUS(NAME VARCHAR2(100),CARD VARCHAR2(300),TIME VARCHAR2(8),"
					+ "CLICKED NUMBER(1),X NUMBER(5),Y NUMBER(5), DIM NUMBER(5))";
			statement.executeQuery(query);
			statement.executeQuery("commit");
		} catch (SQLException e) {
			logger.error("Status tábla létrehozása sikertelen.");

			return false;
		}
		logger.debug("Status tábla sikeresen létrehozva.");

		return true;
	}

	/**
	 * Létrehozza a HIGH_SCORES táblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült létrehozni a táblát,
	 *         egyébként hamissal.
	 */
	private static boolean createHighScoresTable() {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE HIGH_SCORES (NAME VARCHAR2(100),TIME VARCHAR(8))";
			statement.executeQuery(query);
			statement.executeQuery("commit");
		} catch (SQLException e) {
			logger.error("HighScores tábla létrehozása sikertelen.");

			return false;
		}

		logger.debug("HighScores tábla sikeresen létrehozva.");

		return true;
	}

	/**
	 * Visszaadja, hogy létezik e a tábla vagy sem.
	 * 
	 * @param table
	 *            A lekérdezni kívánt tábla.
	 * @return Igaz igazságértékkel tér vissza, ha létezik a tábla, egyébként
	 *         hamissal.
	 */

	private static boolean isTableExist(String table) {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, table, null);
			if (rs.next()) {
				logger.debug("A lekérdezés sikeres.");

				return true;
			} else {
				logger.debug("A lekérdezés sikeres.");

				return false;
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");

			return false;
		}

	}

	/**
	 * Jelenlegi játékállás elmentése.
	 * 
	 * A {@code saveStatus} metódus elmenti a jelenlegi játékállást, ha nincs
	 * létrehozva megfelelő tábla akkor készít.
	 * 
	 * @param name
	 *            A mentést végrehajtó felhasználó felhasználóneve.
	 * @param card
	 *            A menteni kívánt filenév.
	 * @param time
	 *            A menteni kívánt idő .
	 * @param clicked
	 *            A kattinthatóság értéke.
	 * @param x
	 *            A kártya mátrix beli X poziciója.
	 * @param y
	 *            A kártya mátrix beli Y poziciója.
	 * @param dim
	 *            A kártya mátrix dimenziója.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült elmenteni a
	 *         játékállást, egyébként hamissal.
	 * */
	public static boolean saveStatus(String name, String card, StopWatch time,
			boolean clicked, int x, int y, int dim) {
		if (!isTableExist("STATUS"))
			createStatusTable();

		try {
			Statement statement = connection.createStatement();
			String query = "INSERT INTO STATUS (NAME,CARD,TIME,CLICKED,X,Y,DIM) "
					+ "VALUES('"
					+ name
					+ "','"
					+ card
					+ "','"
					+ time.toString()
					+ "','"
					+ (clicked ? 1 : 0)
					+ "','"
					+ x
					+ "','" + y + "','" + dim + "')";
			statement.executeQuery(query);
			statement.executeQuery("commit");
		} catch (SQLException e) {
			logger.error("A mentés sikertelen");

			return false;
		}

		logger.debug("A mentés sikeres");

		return true;
	}

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
	 * */
	public static boolean updateHighScores(String sw, String name) {
		if (!isTableExist("HIGH_SCORES"))
			createHighScoresTable();

		try {
			Statement stm = connection.createStatement();

			String query = "INSERT INTO HIGH_SCORES (NAME,TIME)" + "VALUES('"
					+ name + "','" + sw + "')";
			stm.executeQuery(query);
			stm.executeQuery("commit");

		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");

			return false;
		}

		logger.info("A lekérdezés sikeres.");

		return true;
	}

	/**
	 * Visszaadja a STATUS táblában lementett időt.
	 * 
	 * @param name
	 *            A betölteni kívánt játékmentés tulajdonosának neve.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült betölteni a
	 *         menstést, egyébként hamissal.
	 * */
	public static String getTime(String name) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT TIME FROM STATUS WHERE NAME='" + name + "'";
			ResultSet result = statement.executeQuery(query);

			if (result.next()) {
				logger.debug("A lekérdezés sikeres.");

				return result.getString("TIME");
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return new String();
	}

	/**
	 * Visszaadja az eredménytábla adatait.
	 * 
	 * Visszaadja az eredménytábla adatait rendezés nélkül.
	 * 
	 * @return Az eredménytábla adatai.
	 * */
	public static Map<String, String> getHighScores() {
		Map<String, String> result = new TreeMap<String, String>();

		if (!isTableExist("HIGH_SCORES"))
			createHighScoresTable();
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, TIME FROM HIGH_SCORES";
			ResultSet r = statement.executeQuery(query);

			while (r.next()) {
				result.put(r.getString("TIME"), r.getString("NAME"));
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		logger.debug("A lekérdezés sikeres.");

		return result;
	}

	/**
	 * Visszaadja a kívánt játékmentést.
	 * 
	 * @param name
	 *            A betölteni kívánt játékmentés tulajdonosának neve.
	 * 
	 * @return Az {@code name} felhasználó lementett kártyáinak a helyzetével
	 *         tér vissza.
	 * */
	public static List<Card> loadStatus(String name) {
		List<Card> result = new ArrayList<Card>();

		if (!isStatusExist(name))
			return result;
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM STATUS WHERE NAME='" + name + "'";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				Card temp = new Card(new File(rs.getString("CARD")),
						rs.getInt("X"), rs.getInt("Y"), rs.getInt("DIM"));
				temp.setClicked((rs.getInt("CLICKED") == 1 ? true : false));
				result.add(temp);
				logger.debug("A lekérdezés sikeres.");
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return result;
	}

	/**
	 * Visszaadja, hogy létezik e már a kívánt felhasználónév.
	 * 
	 * Visszaadja, hogy létezik e már a kívánt felhasználónév, ha nincs USER
	 * tábla akkor létrehozza.
	 * 
	 * @param name
	 *            A ellenőrizendő felhasználónév.
	 * 
	 * @return Igaz ha létezik a felhasználónév, egyébként hamis.
	 * */
	public static boolean isUserExist(String name) {
		if (!isTableExist("USERS"))
			createUsersTable();
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM USERS WHERE NAME='" + name + "'";
			ResultSet result = statement.executeQuery(query);

			if (result.next()) {
				logger.debug("A lekérdezés sikeres.");
				return true;
			}

		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return false;
	}

	/**
	 * Visszaadja, hogy létezik e a falhasználónak játékmentése.
	 * 
	 * Visszaadja, hogy létezik e már a kívánt játékmentés, ha nincs STATUS
	 * tábla akkor létrehozza.
	 * 
	 * @param name
	 *            A ellenőrizendő felhasználónév.
	 * 
	 * @return Igaz ha létezik a játékmentés, egyébként hamis.
	 * */
	public static boolean isStatusExist(String name) {
		if (!isTableExist("STATUS"))
			createStatusTable();

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT NAME FROM STATUS WHERE NAME='" + name + "'";
			ResultSet result = statement.executeQuery(query);

			if (result.next()) {
				logger.debug("A lekérdezés sikeres.");

				return true;
			}

		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return false;
	}

	/**
	 * Törli a játékmentést.
	 * 
	 * @param name
	 *            A törlendő játékmentés tulajdonosának felhasználóneve.
	 * 
	 * @return Igaz ha sikeres a törlés, egyébként hamis.
	 * */
	public static boolean removeStatus(String name) {
		try {
			Statement statement = connection.createStatement();
			String query = "DELETE FROM STATUS WHERE NAME= '" + name + "'";
			statement.executeQuery(query);
			logger.debug("A törlés sikeres.");

			return true;
		} catch (SQLException e) {
			logger.error("A törlés sikertelen.");
		}

		return false;
	}

	/**
	 * Új profilt hoz létre.
	 * 
	 * Új profilt hoz létre, amennyiben nem létezik a felhasználónév. Ha nem
	 * létezik a USER tábla akkor létrehozza.
	 * 
	 * @param name
	 *            A felhasználó felhasználónév.
	 * @param pass
	 *            A felhasználó jelszava.
	 * 
	 * @return Igaz ha sikerült létrehozni a profilt, egyébként hamis.
	 **/
	public static boolean addProfile(String name, String pass) {
		if (!isTableExist("USERS"))
			createStatusTable();

		if (isUserExist(name)) {
			logger.error("A hozzáadás a táblához sikertelen.");
			return false;
		}

		try {
			Statement stm = connection.createStatement();

			String query = "INSERT INTO USERS (NAME,PASS)" + "VALUES('" + name
					+ "','" + pass + "')";
			stm.executeQuery("SELECT NAME FROM USERS WHERE NAME='" + name + "'");
			stm.executeQuery(query);
			stm.executeQuery("commit");

		} catch (SQLException e) {
			logger.error("A hozzáadás a táblához sikertelen.");

			return false;
		}
		logger.debug("A hozzáadás a táblához sikeres.");

		return true;
	}

	/**
	 * Betölti a profilt.
	 * 
	 * Név és jelszó alapján betölti a profilt.
	 * 
	 * @param name
	 *            A felhasználó felhasználónév.
	 * @param pass
	 *            A felhasználó jelszava.
	 * 
	 * @return Igazzal tér vissza, ha sikerült betölteni a profilt, egyébként
	 *         hamissal.
	 * */

	public static boolean loadProfile(String name, String pass) {
		if (!isTableExist("USERS"))
			createUsersTable();

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT PASS FROM USERS WHERE NAME='" + name + "'";
			ResultSet result = statement.executeQuery(query);
			if (result.next() && result.getString("PASS").equals(pass)) {
				logger.debug("A betöltés sikeres.");
				return true;
			}
		} catch (SQLException e) {
			logger.error("A betöltés sikertelen.");
		}

		return false;
	}
}
