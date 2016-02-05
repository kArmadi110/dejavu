package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.objects.Position;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * A {@code Rand} osztály véletlen poziciókat szolgáltat, ami a kártyák
 * elhelyezésében segít.
 * 
 * @author iam346
 *
 */
public class Rand {

	/**
	 * A véletlen poziciók tárolására szolgáló lista.
	 */
	List<Position> randomPos = new ArrayList<Position>();

	/**
	 * Beállítja a véletlen poziciókat.
	 * 
	 * @param dimension
	 *            A pozicióknak átadott dimenzió érték.
	 */
	public Rand(int dimension) {
		List<Position> positions = new ArrayList<Position>();
		
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				positions.add(new Position(i, j, dimension));

		Random r = new Random();

		int temp;

		while (positions.size() > 1) {
			temp = r.nextInt(positions.size() - 1);
			randomPos.add(positions.get(temp));
			positions.remove(temp);
		}

		randomPos.add(positions.get(0));
	}

	/**
	 * Visszaadja, a véletlen poziciókat tartalmazó listát.
	 * 
	 * @return andomPos
	 */
	public List<Position> getrandomPos() {
		
		return randomPos;
	}
}
