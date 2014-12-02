package quiz;

//import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QuizHistory implements Comparable {
	
	private int score;
	private Time time;
	private String user;
	private Date whenTaken;
	private int userID;
	private String quizName;
	private String formattedWhenTaken;
	
	// Note that time is in milliseconds
	public QuizHistory(int theScore, Time theTime, String theUser, Date whenWasTaken, int theUserID, String theQuizName) {
		score = theScore;
		time = theTime;
		user = theUser;
		whenTaken = whenWasTaken;
		userID = theUserID;
		quizName = theQuizName;
		
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
		formattedWhenTaken = dateTimeInstance.format(whenWasTaken);
	}
	
	
	// Once they've taken a quiz call this constructor to update the database
	public QuizHistory(Connection con, String updateWhenTaken, int updateUser, Time updateTime, int updateScore, int quizID) throws SQLException {
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO quiz_history VALUES (NULL, ?, ?, ?, ?, ?)");
		pStmt.setInt(1, updateUser);
		pStmt.setInt(2, quizID);
		pStmt.setInt(3, updateScore);
		pStmt.setTime(4, updateTime);
		pStmt.setString(5, updateWhenTaken);
		pStmt.executeUpdate();
	}
	
	public String getFormattedWhenTaken() {
		return formattedWhenTaken;
	}
	
	public int getUserID() {
		return userID;
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
	
	public String getQuizName() {
		return quizName;
	}

	@Override
	public int compareTo(Object arg0) {
		QuizHistory other = (QuizHistory) arg0;
		return other.getWhenTaken().compareTo(whenTaken);
	}
	
}
