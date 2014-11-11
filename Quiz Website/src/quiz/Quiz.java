package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.servlet.ServletContext;

public class Quiz {

	private ArrayList<Question> questions;
	private Statement stmt;
	private ResultSet rs;

	public Quiz(int quizID, Statement statement) throws SQLException {
		stmt = statement;
		rs = stmt.executeQuery("select * from quizzes where id = '" + quizID
				+ "'");
		rs.next();

		// setQuestions(quizID);
	}

	public Quiz(Statement statement, String title, String description,String username,
			boolean randomized, boolean multiple_pages,
			boolean immediate_feedback, boolean practice_mode) throws SQLException {
		stmt = statement;
		rs = stmt.executeQuery("select id from users where username = '" + username + "'");
		rs.first();
		int user_id = rs.getInt("id");
		String created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String values = "'" + title + "', '" + user_id + "', '" + created_on + "', '" + description + 
		"', '" + boolToInt(randomized) + "', '" + boolToInt(multiple_pages) + "', '" + boolToInt(immediate_feedback) + "', '" + boolToInt(practice_mode) + "'";
		String query = "INSERT INTO quizzes(title, created_by, created_on, " +
		"description, randomized, multiple_pages, immediate_feedback, " +
		"practice_mode) VALUES (" + values + ")";
		stmt.executeUpdate(query);
		
	}
	
	private int boolToInt(boolean b) {
		return b ? 1 : 0;
	}

	public String getCreatedBy() throws SQLException {
		int createdByID = rs.getInt("created_by");
		ResultSet currRS = stmt.executeQuery("select * from users where id = '"
				+ createdByID + "'");
		return currRS.getString("username");
	}

	public String getQuizDescription() throws SQLException {
		return rs.getString("description");
	}

	public Date getDateCreated() throws SQLException {
		return rs.getDate("created_on");
	}

	public boolean getIfHasMultiplePages() throws SQLException {
		return rs.getBoolean("multiple_pages");
	}

	public boolean getIfRandomized() throws SQLException {
		return rs.getBoolean("randomized");
	}

	public boolean getIfImmediateFeedback() throws SQLException {
		return rs.getBoolean("immediate_feedback");
	}

	public String getTitle() throws SQLException {
		return rs.getString("title");
	}

	public boolean getIfPracticeMode() throws SQLException {
		return rs.getBoolean("practice_mode");
	}

	// private void setQuestions(int quizID) throws SQLException {
	// ResultSet questionRS =
	// stmt.executeQuery("select * from questions where quiz_id = '" + quizID +
	// "'");
	//
	// }

}
