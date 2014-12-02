package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
	private Statement stmt;
	private int id;
	private String theUsername;
	
	public User(int id, Statement stmt) {
		this.stmt = stmt;
		this.id = id;
	}
	
	public User(String username, Statement stmt) throws SQLException {
		this.stmt = stmt;
		theUsername = username;
		ResultSet rs = stmt.executeQuery("select id from users where username = '" + username + "'");
		rs.next();
		id = rs.getInt("id");
	}
	
	public String getFullName() {
		try {
			ResultSet rs = stmt.executeQuery("select * from users where id = '" + id + "'");
			rs.next();
			return rs.getString("first_name") + " " + rs.getString("last_name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public String getFirstName() {
		try {
			ResultSet rs = stmt.executeQuery("select first_name from users where id = '" + id + "'");
			rs.next();
			return rs.getString("first_name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public String getUsername() {
		try {
			ResultSet rs = stmt.executeQuery("select * from users where id = '" + id + "'");
			rs.next();
			return rs.getString("username");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public ArrayList<QuizHistory> getRecentlyTakenQuizzes() throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from quiz_history inner join quizzes " +
				"on quiz_history.quiz_id=quizzes.id  where user_id = " + id + " order by taken_on desc");
		
		ArrayList<QuizHistory> recentlyTakenQuizzes = new ArrayList<QuizHistory>();
		while(rs.next()) {
			QuizHistory currHistory = new QuizHistory(rs.getInt("score"), rs.getTime("time"), theUsername, rs.getDate("taken_on"), id, rs.getString("title"));
			recentlyTakenQuizzes.add(currHistory);
		}
		return recentlyTakenQuizzes;
	}
	
	public ArrayList<QuizHistory> getRecentlyCreatedQuizzes() throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from quizzes where created_by = " + id + " order by created_on desc");
		ArrayList<QuizHistory> recentlyCreatedQuizzes = new ArrayList<QuizHistory>();
		
		while(rs.next()) {
			QuizHistory currHistory = new QuizHistory(0, null, theUsername, rs.getDate("created_on"), id, rs.getString("title"));
			recentlyCreatedQuizzes.add(currHistory);
		}
		
		return recentlyCreatedQuizzes;
	}
	
	public int getId() {
		return id;
	}
	
	public Quiz[] recentlyTakenQuizzes(DBConnection con) {
		String query = "SELECT quiz_id FROM `quiz_history` WHERE user_id = " + id + " GROUP BY quiz_id ORDER BY taken_on DESC LIMIT 5";
		return quizQuery(con, query);
	}
	
	public Quiz[] recentlyCreatedQuizzes(DBConnection con) {
		String query = "SELECT id AS quiz_id FROM `quizzes` WHERE created_by = " + id + " ORDER BY created_on DESC LIMIT 5";
		return quizQuery(con, query);
	}
	
	private Quiz[] quizQuery(DBConnection con, String query) {
		ResultSet rs;
		try {
			rs = con.getStatement().executeQuery(query);
			rs.last();
			int numRows = rs.getRow();
			rs.first();
			Quiz[] quizzes = new Quiz[numRows];
			for (int i = 0; i < numRows; i++) {
				int quiz_id = rs.getInt("quiz_id");
				quizzes[i] = new Quiz(quiz_id, con);
				rs.next();
			}
			return quizzes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
