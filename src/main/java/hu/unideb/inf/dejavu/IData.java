package hu.unideb.inf.dejavu;

import hu.unideb.inf.dejavu.objects.Status;
import hu.unideb.inf.dejavu.objects.StopWatch;
import hu.unideb.inf.dejavu.objects.User;
import hu.unideb.inf.dejavu.objects.HighScoreTable;

public interface IData {

	public boolean connect();

	public boolean close();

	public boolean createUsersTable();

	public boolean createStatusTable();

	public boolean createHighScoresTable();

	public boolean isHighScoreTableExist();

	public boolean isStatusTableExist();

	public boolean isUserTableExist();

	public boolean saveStatus(Status status);

	public boolean updateHighScores(String sw, String name);

	public StopWatch getTime(User user);

	public HighScoreTable getHighScores();

	public Status loadStatus(User user);

	public boolean isUserExist(User name);

	public boolean isStatusExist(User user);

	public boolean removeStatus(User user);

	public boolean addProfile(User user);

	public boolean loadProfile(User user);
}
