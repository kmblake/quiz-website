package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Challenge {
	
	private int id;
	private int quiz_id;
	private int challenger_best_score;
	private int challenger_id;
	private String quiz_name;
	private String challenger_name;
	private String date_challenged;
	
	
	public static void createChallenge(Connection con, int challenged_user, int challenged_by, int quiz_id) throws SQLException {
		String created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		PreparedStatement pStmt = con
				.prepareStatement("INSERT INTO challenges VALUES (NULL, ?, ?, ?, ?)");
		pStmt.setInt(1, challenged_user);
		pStmt.setInt(2, challenged_by);
		pStmt.setInt(3, quiz_id);
		pStmt.setString(4, created_on);
		pStmt.executeUpdate();
	}
	
	public static Challenge[] getChallenges(Connection con, int user_id)
			throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("SELECT c.*, u.username, q.title FROM challenges AS c INNER JOIN users AS u ON c.challenged_by = u.id INNER JOIN quizzes AS q ON c.quiz_id = q.id WHERE c.challenged_user = "
				+ user_id
				+ " ORDER BY c.challenged_on DESC");
		rs.last();
		int size = rs.getRow();
		Challenge[] challenges = new Challenge[size];
		rs.first();
		for (int i = 0; i < size; i++) {
			Challenge c = new Challenge(con, rs.getInt("id"), rs.getInt("quiz_id"), rs.getInt("challenged_by"), rs.getString("title"), rs.getString("username"), rs.getTimestamp("challenged_on"));
			challenges[i] = c;
			rs.next();
		}
		return challenges;
	}
	
	public Challenge(Connection con, int id, int quiz_id, int challenger_id, String quiz_name, String challenger_name, Timestamp challenged_on) throws SQLException {
		this.id = id;
		this.quiz_id = quiz_id;
		this.challenger_id = challenger_id;
		this.quiz_name = quiz_name;
		this.challenger_name = challenger_name;
		ResultSet rs = con.createStatement().executeQuery("SELECT MAX(score) AS max_score FROM quiz_history WHERE user_id = " + challenger_id + " AND quiz_id = " + quiz_id);
		rs.next();
		this.challenger_best_score = rs.getInt("max_score");
		SimpleDateFormat stringForm = new SimpleDateFormat("MM-dd-yyyy");
		this.date_challenged = stringForm.format(challenged_on);
	}
	
	public int getQuizId() {
		return quiz_id;
	}

	public int getChallengerBestScore() {
		return challenger_best_score;
	}
	
	public int getChallengerId() {
		return challenger_id;
	}

	public String getQuizName() {
		return quiz_name;
	}

	public String getChallengerName() {
		return challenger_name;
	}
	
	public String getDateChallenged() {
		return date_challenged;
	}

}