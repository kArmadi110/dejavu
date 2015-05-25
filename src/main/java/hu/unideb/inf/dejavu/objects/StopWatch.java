package hu.unideb.inf.dejavu.objects;

import hu.unideb.inf.dejavu.DejaVu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@code StopWatch} osztály a játék közben eltelt idő mérésére szolgál.
 * 
 * @author iam346
 *
 */
public class StopWatch {
	/**
	 * A kezdő pillanatot tároljuk el benne eleinte, utánna minden percben
	 * növeljük egy percel.
	 */
	long start = 0;
	/**
	 * A percek számát tároljuk.
	 */
	long minute = 0;
	/**
	 * Az osztály naplózója.
	 */
	private static Logger logger = LoggerFactory.getLogger(DejaVu.class);

	/**
	 * A {@code StopWatch} osztály paraméter nélküli konstuktora.
	 * 
	 * A {@code StopWatch} osztály paraméter nélküli konstuktora elindítja a
	 * számlálót.
	 */
	public StopWatch() {
		start = System.currentTimeMillis();
		logger.info("Jelenlegi idő mentése");
	}

	/**
	 * Percenként frissíti a kezőállapotot, és a percek számát.
	 * 
	 * @return Igaz értékkel tér vissza ha eltelt 60 másodperc, egyébként
	 *         hamissal.
	 */
	 boolean update() {
		if ((System.currentTimeMillis() - start) / 1000 >= 60) {
			minute += 1;
			start += 60000;
			logger.info("Letelt egy perc");

			return true;
		}
		return false;
	}

	/**
	 * Stringből beolvas egy időpontot, mentésekkor használatos.
	 * 
	 * @param time
	 *            A mentett és beolvasandó idő.
	 * 
	 * @return Igazzal tér vissza ha sikeresen beállította az időt, egyébként
	 *         hamissal.
	 */
	public boolean fromString(String time) {
		if (time.length() == 5) {
			String[] minuteAndSecond = time.split(":");
			minute = Long.parseLong(minuteAndSecond[0]);
			start = System.currentTimeMillis()
					- Long.parseLong(minuteAndSecond[1]) * 1000;
			logger.info("Az időmérés elkezdődött.");

		} else
			return false;

		return true;
	}
	/**
	 * Visszatér a start és a jelenlegi időpont között eltelt idővel.
	 * 
	 * @return Az eltelt idő String reprezentációja.
	 */
	@Override
	public String toString() {
		update();
		return String.format("%02d:%02d", minute,
				(System.currentTimeMillis() - start) / 1000);
	}
}
