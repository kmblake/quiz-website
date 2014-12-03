package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

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
			QuizHistory currHistory = new QuizHistory(rs.getInt("score"), rs.getTime("time"), theUsername, rs.getTimestamp("taken_on"), id, rs.getString("title"), rs.getInt("quiz_id"));
			recentlyTakenQuizzes.add(currHistory);
		}
		return recentlyTakenQuizzes;
	}
	
	public ArrayList<QuizHistory> getRecentlyCreatedQuizzes() throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from quizzes where created_by = " + id + " order by created_on desc");
		ArrayList<QuizHistory> recentlyCreatedQuizzes = new ArrayList<QuizHistory>();
		
		while(rs.next()) {
			QuizHistory currHistory = new QuizHistory(0, null, theUsername, rs.getTimestamp("created_on"), id, rs.getString("title"), rs.getInt("id"));
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
	
	public ArrayList<NewsItem> getNewsFeed() {
		ArrayList<NewsItem> news = new ArrayList<NewsItem>();
		ResultSet rs;
		try {
			String friendsList = getFriendIdsString();
			System.out.println(friendsList);
			if (friendsList.length() == 0) return news;
			 rs = stmt.executeQuery("SELECT quizzes.*, users.username FROM quizzes INNER JOIN users ON quizzes.created_by = users.id WHERE created_by IN " + friendsList + " ORDER BY created_on DESC LIMIT 8");
			while(rs.next()) {
				news.add(new NewsItem(NewsItem.CREATED, rs.getString("username"), rs.getInt("created_by"), rs.getString("title"), rs.getInt("id"), rs.getTimestamp("created_on")));
			}
			rs = stmt.executeQuery("SELECT qh.*, q.title, u.username FROM quiz_history as qh INNER JOIN quizzes AS q ON qh.quiz_id = q.id INNER JOIN users AS u on qh.user_id = u.id WHERE user_id IN " + friendsList + " ORDER BY taken_on DESC LIMIT 8");
			while(rs.next()) {
				news.add(new NewsItem(NewsItem.TOOK, rs.getString("username"), rs.getInt("user_id"), rs.getString("title"), rs.getInt("quiz_id"), rs.getTimestamp("taken_on")));
			}
			Collections.sort(news);
			return news;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<NewsItem>();
		}
	}
	
	private String getFriendIdsString() {
		ResultSet rs;
		StringBuilder friendsBuilder = new StringBuilder();
		try {
			rs = stmt.executeQuery("SELECT requested_by as friend_id FROM `friends` WHERE requested_for = " + id + " AND approved = 1 UNION SELECT requested_for FROM friends WHERE requested_by = " + id + " AND approved = 1");
			if (!rs.next()) return "";
			rs.beforeFirst();
			friendsBuilder.append("(");
			while (rs.next()) {
				friendsBuilder.append(rs.getInt("friend_id")).append(", ");
			}
			int len = friendsBuilder.length();
			friendsBuilder.replace(len - 2, len, ")");
			return friendsBuilder.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}
