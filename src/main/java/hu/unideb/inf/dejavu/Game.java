package hu.unideb.inf.dejavu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.dejavu.objects.Achievement;
import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.HighScoreRecord;
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
			loadStatus();
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

	/**
	 * Beállíthatjuk a kívánt dimenziót.
	 * 
	 * @param dimString
	 *            A kívánt dimenzió nXn formában.
	 */
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

			return true;
		}

		removeStatus();

		return false;
	}

	/**
	 * Betölti a játékállást.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha kártyákat sikeresen
	 *         betöltöttük.
	 */
	public boolean loadStatus() {
		Status status = DejaVu.DB.loadStatus(mainStatus.getUser());

		if (!filesExist()) {
			removeStatus();
			return false;
		}

		mainStatus = status;

		removeStatus();

		return true;
	}

	/**
	 * Játékállás elmentésére alkalmas metódus.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikerült elmenteni a
	 *         játékállást.
	 */
	public boolean saveStatus() {
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
	 * @param clicks 
	 * 				a kattintások száma
	 * @param dimension
	 * 				a dimenzió
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikeres a frissítés,
	 *         egyébként hamissal.
	 */
	public boolean updateHighScores(int clicks, int dimension) {
		return DejaVu.DB.updateHighScores(new HighScoreRecord(mainStatus.getUser().getUserName(),
				mainStatus.getTime().toString(), clicks, dimension));
	}

	/**
	 * Visszaadja az eredménytáblát.
	 * 
	 * * @param dim
	 * 				a dimenzió
	 * @return visszaad egy rendezett TreeMap példányt.
	 */
	public HighScoreTable getHighScoresByTime(String dim) {

		HighScoreTable result = DejaVu.DB.getHighScores();
		result.sortByTime(dim);
		return result;
	}

	public HighScoreTable getHighScoresByClicks(String dim) {

		HighScoreTable result = DejaVu.DB.getHighScores();
		result.sortByClick(dim);
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
	 * @param user
	 *            A felhasználó.
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

	private List<Achievement> getAchievement(String dim) {
		List<Achievement> result = new ArrayList<Achievement>();
		HighScoreTable highScoreByTime = getHighScoresByTime(dim);

		if (!highScoreByTime.getTable().isEmpty()
				&& highScoreByTime.getTable().get(0).getName().equals(mainStatus.getUser().getUserName()))
			result.add(new Achievement(dim + "Idő", 1));
		else if (highScoreByTime.getTable().size()>1
				&& highScoreByTime.getTable().get(1).getName().equals(mainStatus.getUser().getUserName()))
			result.add(new Achievement(dim + "Idő", 2));
		else if (highScoreByTime.getTable().size()>2
				&& highScoreByTime.getTable().get(2).getName().equals(mainStatus.getUser().getUserName()))
			result.add(new Achievement(dim + "Idő", 3));

		highScoreByTime = getHighScoresByClicks(dim);

		if (!highScoreByTime.getTable().isEmpty()
				&& highScoreByTime.getTable().get(0).getName().equals(mainStatus.getUser().getUserName()))
			result.add(new Achievement(dim + "Kattintás", 1));
		else if (highScoreByTime.getTable().size()>1
				&& highScoreByTime.getTable().get(1).getName().equals(mainStatus.getUser().getUserName()))
			result.add(new Achievement(dim + "Kattintás", 2));
		else if (highScoreByTime.getTable().size()>2
				&& highScoreByTime.getTable().get(2).getName().equals(mainStatus.getUser().getUserName()))
			result.add(new Achievement(dim + "Kattintás", 3));

		return result;
	}

	public List<Achievement> getAchievements() {
		List<Achievement> result = new ArrayList<Achievement>();
		result.addAll(getAchievement("2x2"));
		result.addAll(getAchievement("4x4"));
		result.addAll(getAchievement("6x6"));

		if (result.stream().map(m -> m.getPrize()).filter(p -> p == 1).count() == 3)
			result.add(new Achievement("A játék mestere", 0));

		return result;
	}

	/**
	 * Megszakítja az adatbázis kapcsolatot és kilép.
	 */
	public void exitGame() {
		System.exit(0);
	}

}
