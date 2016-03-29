package hu.unideb.inf.dejavu.objects;

public class Pair implements Comparable<Pair> {

	String key;
	String value;

	public Pair(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(Pair o) {
		return value.compareTo(o.getValue());
	}

}
