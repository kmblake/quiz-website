package quiz;

import java.sql.Time;
import java.util.Date;

public class QuizHistory {
	
	private int score;
	private Time time;
	private String user;
	private Date whenTaken;
	
	// Note that time is in milliseconds
	public QuizHistory(int theScore, Time theTime, String theUser, Date whenWasTaken) {
		score = theScore;
		time = theTime;
		user = theUser;
		whenTaken = whenWasTaken;
	}
	
	public int getScore() {
		return score;
	}
	
	public Time getTime() {
		return time;
	}
	
	public String getUser() {
		return user;
	}
	
	public Date getWhenTaken() {
		return whenTaken;
	}
	
}
