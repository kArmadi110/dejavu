package hu.unideb.inf.dejavu.gui;

public class Player {
	public int number;
	String name, time;

	public Player(int number, String name, String time) {
		this.name = name;
		this.time = time;
		this.number=number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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
}
