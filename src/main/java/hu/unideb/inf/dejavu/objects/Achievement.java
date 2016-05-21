package hu.unideb.inf.dejavu.objects;

public class Achievement {
	private String description;
	private int prize;

	public Achievement(String description, int prize) {
		this.description = description;
		this.prize = prize;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}

}
