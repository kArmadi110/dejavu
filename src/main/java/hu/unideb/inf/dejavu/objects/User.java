package hu.unideb.inf.dejavu.objects;

public class User {
	String UserName;
	String Password;

	public User(){
		UserName="";
		Password="";
	}
	
	public User(String userName, String password) {
		super();
		UserName = userName;
		Password = password;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
