package hu.unideb.inf.dejavu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.dejavu.objects.Card;
import hu.unideb.inf.dejavu.objects.Position;
import hu.unideb.inf.dejavu.objects.StopWatch;

/**
 * Játéklogikát megvalósító osztály. A {@code Game} osztály tartalmazza a
 * megjelenítendő kártyákat, azokról az információkat, innen kérdezhető le
 * továbbá az adatbázis.
 * 
 * @author iam346
 *
 */
public class Game {

	/**
	 * A megjelenítendő kártyák.
	 * */
	Card[][] cards;
	/**
	 * A játéktér dimenziója.
	 * 
	 * Kizárólag négyzetes mátrixokkal játszunk, ezért elég egy érték.
	 * */
	int dimension;
	/**
	 * A felfordított kártyákat tárolja ideiglenesen.
	 */
	private List<Position> upCards = new ArrayList<Position>();
	/**
	 * A lefordítani a kívánt kártyák.
	 * 
	 * Alapértelmezésben amikor rákattintunk egy kártyára felfordul, ez a lista
	 * tartalmazza majd azokat amiket vissza kell fordítani.
	 * */
	List<Position> downCards = new ArrayList<Position>();
	/**
	 * Stopper az idő mérésére.
	 * */
	public StopWatch timer;
	/**
	 * A jelenlegi játékos felhasználóneve.
	 * */
	String name;
	/**
	 * A jelenlegi játékos jelszava.
	 * */
	String pass;
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
		dimension = 0;
		timer = new StopWatch();
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
	 * */
	public void setCards(List<File> cardNames) {
		upCards.clear();
		downCards.clear();
		if (DataB.isStatusExist(name)) {
			logger.info("Mentett játékállás betöltése");
			loadCards();
		} else if (isSetDim()) {
			logger.info("Új játékállás betöltése");
			timer = new StopWatch();
			Rand randomTemp = new Rand(dimension);
			cards = new Card[dimension][dimension];

			for (int i = 0; i < (dimension * dimension) / 2; i++) {
				addCard(cardNames.get(i), randomTemp.randomPos.get(i)
						.getFirst(), randomTemp.randomPos.get(i).getSecond(),
						dimension);
				randomTemp.randomPos.remove(i);

				addCard(cardNames.get(i), randomTemp.randomPos.get(i)
						.getFirst(), randomTemp.randomPos.get(i).getSecond(),
						dimension);
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
		cards[x][y] = new Card(file, x, y, dimension);
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
	 * */

	public Card getCard(int row, int column) {
		if (row < dimension && column < dimension) {
			return cards[row][column];
		}
		return null;
	}

	/**
	 * Visszaadja a lefordítandó kártyák pozicióinak listáját.
	 * 
	 * @return Visszatér a lefordított jártyák pozicióinak listájával.
	 * */
	public List<Position> getDownCard() {
		return downCards;
	}

	/**
	 * Beállíthatjuk a kívánt dimenziót.
	 * 
	 * @param dimension
	 *            A kívánt dimenzió.
	 * */
	public void setDim(int dimension) {
	//	if(isStatusExist())
		//	removeStatus();
		logger.info("Új dimenzió beállítva");
		upCards.clear();
		downCards.clear();
		this.dimension = dimension;
	}

	/**
	 * Visszaadja, hogy beállítottunk e már dimenziót.
	 * 
	 * @return igazzal tér vissza, ha a dimension be van állítva.
	 */
	public boolean isSetDim() {
		return dimension > 0 ? true : false;
	}

	/**
	 * Visszaadja a dimenzió értékét.
	 * 
	 * @return A dimenzió értéke.
	 * */
	public int getDim() {
		return dimension;
	}

	/**
	 * Meghatározza a megfelelő mátrixméreteket.
	 * 
	 * Meghatározza, hogy milyen mátrixméretek lehetnek megfelelőek, és azt
	 * visszaadja egy listában, ha nincs megfelelő akkor üresm listával tér
	 * vissza.
	 * 
	 * @param numberOfElement
	 *            a kártyák maximális elemszáma.
	 * @return A lehetséges dimenziók listájával tér vissza.
	 */
	public static List<Integer> matrixSize(int numberOfElement) {
		List<Integer> result = new ArrayList<Integer>();

		for (int i = 2 * numberOfElement; i > 1; i--)
			if (!result.contains((int) Math.sqrt(i))
					&& (int) Math.sqrt(i) % 2 == 0)
				result.add((int) Math.sqrt(i));
		logger.info("Új mátrix méretek létrehozása sikeres");

		return result;
	}

	/**
	 * Meghatározza, hogy a játékos nyer-e.
	 * 
	 * @return Igazzal tér vissza, ha minden kártyának sikeresen megleltük a
	 *         párját.
	 */

	public boolean isEnd() {
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++) {
				if (getCard(i, j).isClicked())
					return false;
			}

		return true;
	}

	/**
	 * Frissíti a visszafordítandó kártyák listáját.
	 * 
	 * Frissíti a visszafordítandó kártyák listáját. Minden kártyára való
	 * kattintás után meg kell hívni, és a kattintás után a lefordítandó
	 * kártyákat lefordítani.
	 * 
	 * @param row
	 *            A kártya sorindexe.
	 * @param column
	 *            A kártya oszlopindexe.
	 * 
	 * @return Igaz értékkel tér vossza, ha akártyák felfordítva maradnak,
	 *         egyébként hamissal.
	 */
	public boolean updateCardStatus(int row, int column) {
		downCards.clear();
		if (cards[row][column].isClicked()) {
			if (!upCards.contains(cards[row][column].getPosition()))
				upCards.add(cards[row][column].getPosition());

			if (upCards.size() == 2
					&& !upCards.get(0).equals(upCards.get(1))
					&& cards[upCards.get(0).getFirst()][upCards.get(0)
							.getSecond()].getValue().equals(
							cards[upCards.get(1).getFirst()][upCards.get(1)
									.getSecond()].getValue())) {

				cards[upCards.get(0).getFirst()][upCards.get(0).getSecond()]
						.setClicked(false);
				upCards.remove(0);
				cards[upCards.get(0).getFirst()][upCards.get(0).getSecond()]
						.setClicked(false);
				upCards.remove(0);
				return true;

			} else if (upCards.size() > 2) {
				downCards.add(upCards.get(0));
				upCards.remove(0);
				downCards.add(upCards.get(0));
				upCards.remove(0);
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
		List<Card> res = DataB.loadStatus(name);
		for (int i = 0; i < res.size(); i++) {
			if (!res.get(i).getValue().exists()
					|| res.get(i).getValue().isDirectory()) {
				removeStatus();
				return false;
			}
		}
		return true;
	}

	/**
	 * Betölti a játékállást.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha kártyákat sikeresen
	 *         betöltöttük.
	 */

	public boolean loadCards() {
		List<Card> res = DataB.loadStatus(name);
		String time = DataB.getTime(name);
		timer.fromString(time);

		if (!filesExist())
			return false;

		setDim((int) Math.sqrt(res.size()));
		cards = new Card[dimension][dimension];

		for (int i = 0; i < res.size(); i++) {
			cards[res.get(i).getPosition().getFirst()][res.get(i).getPosition()
					.getSecond()] = res.get(i);
		}
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
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++) {
				DataB.saveStatus(name, cards[i][j].getValue().toString(),
						timer, cards[i][j].isClicked(), cards[i][j]
								.getPosition().getFirst(), cards[i][j]
								.getPosition().getSecond(), dimension);
			}

		return true;
	}

	/**
	 * Visszaadja, hogy tartozik e mentett játékállás a betöltött profilhoz.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha létezik mentett játékállás,
	 *         egyébként hamissal.
	 */
	public boolean isStatusExist() {
		return DataB.isStatusExist(name);
	}

	/**
	 * Törli a mentett játékállást.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikeres a törlés, egyébként
	 *         hamissal.
	 */
	public boolean removeStatus() {
		return DataB.removeStatus(name);
	}

	/**
	 * Frissíti az eredménytáblát.
	 * 
	 * @return Igaz igazságértékkel tér vissza, ha sikeres a frissítés,
	 *         egyébként hamissal.
	 */
	public boolean updateHighScores() {
		return DataB.updateHighScores(timer.toString(), name);
	}

	/**
	 * Visszaadja az eredménytáblát.
	 * 
	 * @return visszaad egy rendezett TreeMap példányt.
	 */
	public TreeMap<String, String> getHighScores() {
		TreeMap<String, String> result = new TreeMap<String, String>(
				Collections.reverseOrder());
		result = (TreeMap<String, String>) DataB.getHighScores();

		return result;
	}

	/**
	 * Meghatározza egy felhasználónévről, vagy jelszóról, hogy helyes e vagy
	 * sem.
	 * 
	 * @param name
	 *            A választott név.
	 * @return Igaz igazságértékkel tér vissza, ha a választott név megfelelő,
	 *         egyébként hamissal.
	 */
	public boolean isPerfectName(String name) {
		if (name.contains("\"") || name.contains("*") || name.contains("'")
				|| name.contains("#") || name.contains("@")
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
		if (DataB.isUserExist(name)) {
			return false;
		}
		this.name = name;
		this.pass = pass;
		return DataB.addProfile(name, pass);
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

	public boolean loadProfile(String name, String pass) {
		if (!isPerfectName(name) && !isPerfectName(pass)) {
			return false;
		}

		this.name = name;
		this.pass = pass;
		return DataB.loadProfile(name, pass);
	}

	/**
	 * Megszakítja az adatbázis kapcsolatot és kilép.
	 */
	public void exitGame() {
		DataB.close();
		System.exit(0);
	}

}
