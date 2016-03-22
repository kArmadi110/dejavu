package hu.unideb.inf.dejavu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.HighScoreTable;
import hu.unideb.inf.dejavu.objects.Pack;
import hu.unideb.inf.dejavu.objects.Position;
import hu.unideb.inf.dejavu.objects.Status;
import hu.unideb.inf.dejavu.objects.StopWatch;
import hu.unideb.inf.dejavu.objects.User;

/**
 * Játéklogikát megvalósító osztály. A {@code Game} osztály tartalmazza a
 * megjelenítendő kártyákat, azokról az információkat, innen kérdezhető le
 * továbbá az adatbázis.
 * 
 * @author iam346
 *
 */
public class Game {

	public Status mainStatus;

	/**
	 * Az osztály naplózója.
	 */

	private static Logger logger = LoggerFactory.getLogger(DejaVu.class);

	/**
	 * A {@code Game} osztály paraméter nélküli konstruktora.
	 * 
	 * Alapértelmezett értéket állít be {@code dimension} - nak és létrehoz egy
	 * {@code Stopwatch} objektumot.
	 */

	public Game() {
		mainStatus = new Status(new User(), new Pack(), new StopWatch(), 0);
	}

	/**
	 * Beállítjuk a kártyák alapértelmezett értékét.
	 * 
	 * Amennyiben nincs mentett játékállás akkor véletlenszerű sorrendet
	 * produkál, amennyiben van akkor a mentett játékállásnak megfelelőt.
	 * 
	 * @param cardNames
	 *            A kiválasztott kártyák nevét tartalmazza.
	 * 
	 */
	public void setCards(List<File> cardNames) {
		mainStatus.upCards.clear();
		mainStatus.downCards.clear();

		if (DejaVu.DB.isStatusExist(mainStatus.getUser())) {
			logger.info("Mentett játékállás betöltése");
			loadCards();
		} else if (isSetDim()) {
			logger.info("Új játékállás betöltése");
			mainStatus.setTime(new StopWatch());

			Rand randomTemp = new Rand(mainStatus.getDimension());
			mainStatus.setPack(new Pack(new Card[mainStatus.getDimension()][mainStatus.getDimension()],
					mainStatus.getDimension()));

			for (int i = 0; i < (mainStatus.getDimension() * mainStatus.getDimension()) / 2; i++) {
				addCard(cardNames.get(i), randomTemp.randomPos.get(i).getFirst(),
						randomTemp.randomPos.get(i).getSecond(), mainStatus.getDimension());
				randomTemp.randomPos.remove(i);

				addCard(cardNames.get(i), randomTemp.randomPos.get(i).getFirst(),
						randomTemp.randomPos.get(i).getSecond(), mainStatus.getDimension());
			}

		}

	}

	/**
	 * Kártyát ad a mátrixhoz az adott pozicióba.
	 * 
	 * @param file
	 *            A kiválasztott filenév.
	 * @param x
	 *            A mátrixbeli X koordináta.
	 * @param y
	 *            A mátrixbeli Y koordináta.
	 * @param dimension
	 *            A mátrixbeli dimenzió.
	 */
	private void addCard(File file, int x, int y, int dimension) {
		mainStatus.getPack().setCard(new Card(file, x, y, dimension), x, y);
	}

	/**
	 * Visszaadja az adott pozicióban szereplő kártyát.
	 * 
	 * @param row
	 *            A kívánt kártya sorszáma.
	 * 
	 * @param column
	 *            A kívánt kártya oszlopszáma.
	 * 
	 * @return Visszatér a kívánt kártyával.
	 * 
	 */
	public Card getCard(int row, int column) {
		if (row < mainStatus.getDimension() && column < mainStatus.getDimension()) {
			return mainStatus.getPack().getCard(row, column);
		}

		return null;
	}

	/**
	 * Visszaadja a lefordítandó kártyák pozicióinak listáját.
	 * 
	 * @return Visszatér a lefordított jártyák pozicióinak listájával.
	 */
	public List<Position> getDownCard() {

		return mainStatus.downCards;
	}

	/**
	 * Beállíthatjuk a kívánt dimenziót.
	 * 
	 * @param dimension
	 *            A kívánt dimenzió.
	 */
	public void setDim(int dimension) {
		logger.debug("Új dimenzió beállítva");
		mainStatus.upCards.clear();
		mainStatus.downCards.clear();
		mainStatus.setDimension(dimension);
	}

	public void setDim(String dimString) {
		int dimension = Integer.parseInt(dimString.substring(0, 1));
		logger.debug("Új dimenzió beállítva");
		mainStatus.upCards.clear();
		mainStatus.downCards.clear();
		mainStatus.setDimension(dimension);
	}

	/**
	 * Visszaadja, hogy beállítottunk e már dimenziót.
	 * 
	 * @return Igazzal tér vissza, ha a dimension be van állítva.
	 */
	public boolean isSetDim() {

		return mainStatus.getDimension() > 0 ? true : false;
	}

	/**
	 * Visszaadja a dimenzió értékét.
	 * 
	 * @return A dimenzió értéke.
	 */
	public int getDim() {

		return mainStatus.getDimension();
	}

	/**
	 * Meghatározza a megfelelő mátrixméreteket.
	 * 
	 * Meghatározza, hogy milyen mátrixméretek lehetnek megfelelőek, és azt
	 * visszaadja egy listában, ha nincs megfelelő akkor üres listával tér
	 * vissza.
	 * 
	 * @param numberOfElement
	 *            a kártyák maximális elemszáma.
	 * @return A lehetséges dimenziók listájával tér vissza.
	 */
	public static List<Integer> matrixSize(int numberOfElement) {
		List<Integer> result = new ArrayList<Integer>();

		for (int i = 2 * numberOfElement; i > 1; i--)
			if (!result.contains((int) Math.sqrt(i)) && (int) Math.sqrt(i) % 2 == 0)
				result.add((int) Math.sqrt(i));

		logger.debug("Új mátrix méretek létrehozása sikeres.");

		return result;
	}

	/**
	 * Meghatározza, hogy a játékos nyer-e.
	 * 
	 * @return Igazzal tér vissza, ha minden kártyának sikeresen megleltük a
	 *         párját, a játéknak ilyenkor vége.
	 */

	public boolean isEnd() {
		for (int i = 0; i < mainStatus.getDimension(); i++)
			for (int j = 0; j < mainStatus.getDimension(); j++) {
				if (getCard(i, j).isClicked())
					return false;
			}

		return true;
	}

	/**
	 * Frissíti a visszafordítandó kártyák listáját.
	 * 
	 * Frissíti a visszafordítandó kártyák listáját. Minden kártyára való
	 * kattintáskor meg kell hívni, és a kattintás után a lefordítandó kártyákat
	 * lefordítani.
	 * 
	 * @param row
	 *            A kártya mátrixbeli X koordinátája.
	 * @param column
	 *            A kártya mátrixbeli Y koordinátája.
	 * 
	 * @return Igaz értékkel tér vossza, ha a kártyák felfordítva maradnak,
	 *         egyébként hamissal.
	 */
	public boolean updateCardStatus(int row, int column) {
		mainStatus.downCards.clear();

		if (mainStatus.getPack().getCard(row, column).isClicked()) {
			if (!mainStatus.upCards.contains(mainStatus.getPack().getCard(row, column).getPosition()))
				mainStatus.upCards.add(mainStatus.getPack().getCard(row, column).getPosition());

			if (mainStatus.upCards.size() == 2 && !mainStatus.upCards.get(0).equals(mainStatus.upCards.get(1))
					&& mainStatus.getPack()
							.getCard(mainStatus.upCards.get(0).getFirst(), mainStatus.upCards.get(0).getSecond())
							.getValue().equals(mainStatus.getPack().getCard(mainStatus.upCards.get(1).getFirst(),
									mainStatus.upCards.get(1).getSecond()).getValue())) {

				mainStatus.getPack()
						.getCard(mainStatus.upCards.get(0).getFirst(), mainStatus.upCards.get(0).getSecond())
						.setClicked(false);
				mainStatus.upCards.remove(0);

				mainStatus.getPack()
						.getCard(mainStatus.upCards.get(0).getFirst(), mainStatus.upCards.get(0).getSecond())
						.setClicked(false);
				mainStatus.upCards.remove(0);

				return true;

			} else if (mainStatus.upCards.size() > 2) {
				mainStatus.downCards.add(mainStatus.upCards.get(0));
				mainStatus.upCards.remove(0);
				mainStatus.downCards.add(mainStatus.upCards.get(0));
				mainStatus.upCards.remove(0);
				return false;
			}

		}

		return false;
	}

	/**
	 * Visszaadja, hogy a kiválasztott fileok elérési útja megfelelő e.
	 * 
	 * @return Igazzal tér vissza, ha minden elérési út helyes.
	 */
	public boolean filesExist() {
		Status status = DejaVu.DB.loadStatus(mainStatus.getUser());

		if (status.getPack().isValid()) {
			removeStatus();
			return true;
		}

		return false;
	}

	/**
	 * Betölti a játékállást.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha kártyákat sikeresen
	 *         betöltöttük.
	 */
	public boolean loadCards() {
		Status status = DejaVu.DB.loadStatus(mainStatus.getUser());

		if (!filesExist())
			return false;

		setDim(status.getDimension());
		mainStatus.setPack(
				new Pack(new Card[mainStatus.getDimension()][mainStatus.getDimension()], mainStatus.getDimension()));

		removeStatus();

		return true;
	}

	/**
	 * Játékállás elmentésére alkalmas metódus.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült elmenteni a
	 *         játékállást.
	 */
	public boolean saveCards() {
		removeStatus();

		DejaVu.DB.saveStatus(mainStatus);

		return true;
	}

	/**
	 * Visszaadja, hogy tartozik e mentett játékállás a betöltött profilhoz.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha létezik mentett játékállás,
	 *         egyébként hamissal.
	 */
	public boolean isStatusExist() {

		return DejaVu.DB.isStatusExist(mainStatus.getUser());
	}

	/**
	 * Törli a mentett játékállást.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikeres a törlés, egyébként
	 *         hamissal.
	 */
	public boolean removeStatus() {

		return DejaVu.DB.removeStatus(mainStatus.getUser());
	}

	/**
	 * Frissíti az eredménytáblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikeres a frissítés,
	 *         egyébként hamissal.
	 */
	public boolean updateHighScores() {

		return DejaVu.DB.updateHighScores(mainStatus.getTime().toString(), mainStatus.getUser().getUserName());
	}

	/**
	 * Visszaadja az eredménytáblát.
	 * 
	 * @return visszaad egy rendezett TreeMap példányt.
	 */
	public HighScoreTable getHighScores() {
		HighScoreTable result = new HighScoreTable(new TreeMap<String, String>(Collections.reverseOrder()));
		result = DejaVu.DB.getHighScores();

		return result;
	}

	/**
	 * Meghatározza egy felhasználónévről vagy jelszóról, hogy helyes e vagy
	 * sem.
	 * 
	 * @param name
	 *            A választott név.
	 * @return Igaz igazságértékkel tér vissza, ha a választott név megfelelő,
	 *         egyébként hamissal.
	 */
	public boolean isPerfectName(String name) {
		if (name.contains("\"") || name.contains("*") || name.contains("'") || name.contains("#") || name.contains("@")
				|| name.contains("!") || name.contains("?")) {

			return false;
		}

		return true;
	}

	/**
	 * Új profilt ad az adatbázishoz.
	 * 
	 * @param name
	 *            A felhasználó elhasználóneve.
	 * @param pass
	 *            A felhazsnáló jelszava
	 * @return igaz igazságértékkel tér vissza, ha a hozzáadás sikeres,
	 *         egyébként hamissal.
	 */

	public boolean addProfile(String name, String pass) {
		if (!isPerfectName(name) || !isPerfectName(pass)) {
			return false;
		}

		if (DejaVu.DB.isUserExist(new User(name, pass))) {
			return false;
		}
		mainStatus.setUser(new User(name, pass));

		return DejaVu.DB.addProfile(mainStatus.getUser());
	}

	/**
	 * Betölt egy profilt.
	 * 
	 * @param name
	 *            A felhasználó elhasználóneve.
	 * @param pass
	 *            A felhazsnáló jelszava.
	 * @return Igaz igazságértékkel tér vissza, ha a felhasználónév és a jelszó
	 *         helyes.
	 */

	public boolean loadProfile(User user) {
		if (!isPerfectName(user.getUserName()) && !isPerfectName(user.getPassword())) {
			return false;
		}

		mainStatus.setUser(user);

		return DejaVu.DB.loadProfile(user);
	}

	/**
	 * Megszakítja az adatbázis kapcsolatot és kilép.
	 */
	public void exitGame() {
		DejaVu.DB.close();
		System.exit(0);
	}

}
