package quiz;

import java.util.Date;

public class QuizHistory {
	
	private int score;
	private long time;
	private String user;
	private Date whenTaken;
	
	// Note that time is in milliseconds
	public QuizHistory(int theScore, long theTime, String theUser, Date whenWasTaken) {
		score = theScore;
		time = theTime;
		user = theUser;
		whenTaken = whenWasTaken;
	}
	
	public int getScore() {
		return score;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getUser() {
		return user;
	}
	
	public Date getWhenTaken() {
		return whenTaken;
	}
	
}
