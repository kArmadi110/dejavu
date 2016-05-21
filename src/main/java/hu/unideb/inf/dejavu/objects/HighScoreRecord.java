package hu.unideb.inf.dejavu.objects;

public class HighScoreRecord {

	String name;
	String time;
	int clicks = 0;
	int dimension = 0;

	public HighScoreRecord(String name, String time, int clicks, int dimension) {
		super();
		this.name = name;
		this.time = time;
		this.clicks = clicks;
		this.dimension = dimension;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

}
