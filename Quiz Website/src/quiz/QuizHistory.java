package quiz;

import java.sql.*;
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
	
	// Once they've taken a quiz call this constructor to update the database
	public QuizHistory(Connection con, Date updateWhenTaken, int updateUser, Time updateTime, int updateScore, int quizID) throws SQLException {
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO quiz_history VALUES (NULL, ?, ?, ?, ?, ?)");
		pStmt.setInt(1, updateUser);
		pStmt.setInt(2, quizID);
		pStmt.setInt(3, updateScore);
		pStmt.setTime(4, updateTime);
		pStmt.setDate(5, (java.sql.Date) updateWhenTaken);
		pStmt.executeUpdate();
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
