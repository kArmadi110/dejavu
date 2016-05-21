package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.HighScoreRecord;
import hu.unideb.inf.dejavu.objects.HighScoreTable;
import hu.unideb.inf.dejavu.objects.Pack;
import hu.unideb.inf.dejavu.objects.Status;
import hu.unideb.inf.dejavu.objects.StopWatch;
import hu.unideb.inf.dejavu.objects.User;

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
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@code DataB} osztály segítségével csatlakozhatunk az adatbázishoz.
 * 
 * @author iam346
 *
 */
public class DataB implements IData {
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

	public boolean connect() {
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
				InputStream inputStream = DataB.class.getClassLoader().getResourceAsStream("project.properties");

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

	public boolean close() {
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

	public boolean createUsersTable() {
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

	public boolean createStatusTable() {
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

	public boolean createHighScoresTable() {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE HIGH_SCORES (NAME VARCHAR2(100),TIME VARCHAR(8),CLICKS NUMBER(5),DIM NUMBER(5))";
			statement.executeQuery(query);
			statement.executeQuery("commit");
		} catch (SQLException e) {
			logger.error("HighScores tábla létrehozása sikertelen.");

			return false;
		}

		logger.debug("HighScores tábla sikeresen létrehozva.");

		return true;
	}

	public boolean isHighScoreTableExist() {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, "HIGH_SCORES", null);
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

	public boolean isStatusTableExist() {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, "STATUS", null);
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

	public boolean isUserTableExist() {
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			ResultSet rs = dbm.getTables(null, null, "USERS", null);
			if (rs.next()) {
				logger.debug("A lekérdezés sikeres.");

				return true;
			} else {
				logger.debug("A lekérdezés sikeres.");

				return false;
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");

		} catch (Exception e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return false;

	}

	public boolean saveStatus(Status status) {
		if (!isStatusTableExist())
			createStatusTable();

		try {
			Statement statement = connection.createStatement();

			for (int i = 0; i < status.getDimension(); i++)
				for (int j = 0; j < status.getDimension(); j++) {
					String query = "INSERT INTO STATUS (NAME,CARD,TIME,CLICKED,X,Y,DIM) " + "VALUES('"
							+ status.getUser().getUserName() + "','" + status.getPack().getCard(i, j).getValue() + "','"
							+ status.getTime().toString() + "','" + (status.getPack().getCard(i, j).isClicked() ? 1 : 0)
							+ "','" + status.getPack().getCard(i, j).getPosition().getFirst() + "','"
							+ status.getPack().getCard(i, j).getPosition().getSecond() + "','" + status.getDimension()
							+ "')";
					statement.executeQuery(query);
					statement.executeQuery("commit");
				}
		} catch (SQLException e) {
			logger.error("A mentés sikertelen");

			return false;
		}

		logger.debug("A mentés sikeres");

		return true;
	}

	public boolean updateHighScores(HighScoreRecord record) {
		if (!isHighScoreTableExist())
			createHighScoresTable();
		try {
			Statement stm = connection.createStatement();

			String query = "INSERT INTO HIGH_SCORES (NAME,TIME,CLICKS,DIM)" + "VALUES('" + record.getName() + "','"
					+ record.getTime() + "','" + record.getClicks() + "','" + record.getDimension() + "')";
			stm.executeQuery(query);
			stm.executeQuery("commit");

		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");

			return false;
		}

		logger.info("A lekérdezés sikeres.");

		return true;
	}

	public StopWatch getTime(User user) {
		StopWatch sw = new StopWatch();

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT TIME FROM STATUS WHERE NAME='" + user.getUserName() + "'";
			ResultSet result = statement.executeQuery(query);

			if (result.next()) {
				logger.debug("A lekérdezés sikeres.");
				sw.fromString(result.getString("TIME"));

				return sw;
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return sw;
	}

	public HighScoreTable getHighScores() {
		List<HighScoreRecord> result = new ArrayList<HighScoreRecord>();
		if (!isHighScoreTableExist())
			createHighScoresTable();
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, TIME,CLICKS,DIM FROM HIGH_SCORES";
			ResultSet r = statement.executeQuery(query);

			while (r.next()) {
				result.add(new HighScoreRecord(r.getString("NAME"), r.getString("TIME"), r.getInt("CLICKS"),
						r.getInt("DIM")));
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		logger.debug("A lekérdezés sikeres.");

		return new HighScoreTable(result);
	}

	public Status loadStatus(User user) {
		List<Card> result = new ArrayList<Card>();
		StopWatch time = getTime(user);
		int dimension = 0;

		if (!isStatusExist(user))
			return new Status(user, new Pack(result, dimension), time, dimension);
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM STATUS WHERE NAME='" + user.getUserName() + "'";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				dimension = rs.getInt("DIM");
				Card temp = new Card(new File(rs.getString("CARD")), rs.getInt("X"), rs.getInt("Y"), rs.getInt("DIM"));
				temp.setClicked((rs.getInt("CLICKED") == 1 ? true : false));
				result.add(temp);
				logger.debug("A lekérdezés sikeres.");
			}
		} catch (SQLException e) {
			logger.error("A lekérdezés sikertelen.");
		}

		return new Status(user, new Pack(result, dimension), time, dimension);
	}

	public boolean isUserExist(User user) {
		if (!isUserTableExist())
			createUsersTable();
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM USERS WHERE NAME='" + user.getUserName() + "'";
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

	public boolean isStatusExist(User user) {
		if (!isStatusTableExist())
			createStatusTable();

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT NAME FROM STATUS WHERE NAME='" + user.getUserName() + "'";
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

	public boolean removeStatus(User user) {
		try {
			Statement statement = connection.createStatement();
			String query = "DELETE FROM STATUS WHERE NAME= '" + user.getUserName() + "'";
			statement.executeQuery(query);
			logger.debug("A törlés sikeres.");

			return true;
		} catch (SQLException e) {
			logger.error("A törlés sikertelen.");
		}

		return false;
	}

	public boolean addProfile(User user) {
		if (!isUserTableExist())
			createStatusTable();

		if (isUserExist(user)) {
			logger.error("A hozzáadás a táblához sikertelen.");
			return false;
		}

		try {
			Statement stm = connection.createStatement();

			String query = "INSERT INTO USERS (NAME,PASS)" + "VALUES('" + user.getUserName() + "','"
					+ user.getPassword() + "')";
			stm.executeQuery("SELECT NAME FROM USERS WHERE NAME='" + user.getUserName() + "'");
			stm.executeQuery(query);
			stm.executeQuery("commit");

		} catch (SQLException e) {
			logger.error("A hozzáadás a táblához sikertelen.");

			return false;
		}
		logger.debug("A hozzáadás a táblához sikeres.");

		return true;
	}

	public boolean loadProfile(User user) {
		if (!isUserTableExist())
			createUsersTable();

		try {
			Statement statement = connection.createStatement();
			String query = "SELECT PASS FROM USERS WHERE NAME='" + user.getUserName() + "'";
			ResultSet result = statement.executeQuery(query);
			if (result.next() && result.getString("PASS").equals(user.getPassword())) {
				logger.debug("A betöltés sikeres.");
				return true;
			}
		} catch (SQLException e) {
			logger.error("A betöltés sikertelen.");
		}

		return false;
	}
}
