package hu.unideb.inf.dejavu.objects;

import java.util.ArrayList;
import java.util.List;

public class Status {
	User user;
	Pack pack;
	StopWatch time;
	int dimension;

	public List<Position> upCards = new ArrayList<Position>();
	public List<Position> downCards = new ArrayList<Position>();

	public Status(User user, Pack pack, StopWatch time, int dimension) {
		super();
		this.user = user;
		this.pack = pack;
		this.time = time;
		this.dimension = dimension;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pack getPack() {
		return pack;
	}

	public void setPack(Pack pack) {
		this.pack = pack;
	}

	public StopWatch getTime() {
		return time;
	}

	public void setTime(StopWatch time) {
		this.time = time;
	}

	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(int dimension){
		this.dimension=dimension;
	}

}
