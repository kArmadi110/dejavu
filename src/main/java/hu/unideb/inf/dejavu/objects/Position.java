package hu.unideb.inf.dejavu.objects;

/**
 * A {@code Position} osztály kártyák pozicióját tárolja.
 * 
 * @author iam346
 *
 */
public class Position {
	
	/**
	 * A mátrix beli X koordináta.
	 */
	private int first;
	
	/**
	 * A mátrix beli Y koordináta.
	 */
	private int second;
	
	/**
	 * A mátrix dimenziója.
	 */
	private int dimension;

	/**
	 * A {@code Position} osztály üres konstruktora.
	 * 
	 * Alapértelmezett érték minden értéknél nulla;
	 */
	public Position() {
		first = 0;
		second = 0;
		dimension = 0;
	}

	/**
	 * A {@code Position} osztály paraméterezett konstruktora.
	 * 
	 * @param first
	 *            A mátrix beli X koordináta.
	 * @param second
	 *            A mátrix beli Y koordináta.
	 * @param dimension
	 *            A mátrix dimenziója.
	 */
	public Position(int first, int second, int dimension) {
		this.dimension = dimension;
		this.first = first;
		this.second = second;
	}

	/**
	 * Megváltoztatja a mátrix beli poziciót.
	 * 
	 * @param first
	 *            A mátrix beli X koordináta.
	 * @param second
	 *            A mátrix beli Y koordináta.
	 * @param dimension
	 *            A mátrix dimenziója.
	 */
	public void setPos(int first, int second, int dimension) {
		this.first = first;
		this.second = second;
		this.dimension = dimension;
	}
	/**
	 * A visszaadja a mátrix beli X koordinátát.
	 * @return a mátrix beli X koordinátával tér vissza.
	 */
	public int getFirst() {
	
		return first;
	}
	
	/**
	 * A visszaadja a mátrix beli Y koordinátát.
	 * @return a mátrix beli Y koordinátával tér vissza.
	 */
	public int getSecond() {
	
		return second;
	}
	
	/**
	 * A {@code Position} osztály hash metódusa.
	 */
	@Override
	public int hashCode() {
	
		return first * 1000 + second * 100 + dimension * 10;
	}
	/**
	 * A {@code Position} osztály equals metódusa.
	 */
	@Override
	public boolean equals(Object otherObject) {
		Position other = (Position) otherObject;
		
		return other.getFirst() == getFirst()
				&& other.getSecond() == getSecond()
				&& dimension == other.dimension;
	}

}