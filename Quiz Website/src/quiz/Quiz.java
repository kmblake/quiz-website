package quiz;

import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.servlet.ServletContext;

public class Quiz {

	private ArrayList<Question> questions;
	private Statement stmt;
	private ResultSet rs;
	
	
	public Quiz(int quizID, Statement statement) throws SQLException {
		stmt = statement;
		rs = stmt.executeQuery("select * from quizzes where id = '" + quizID + "'");
		rs.next();
		
		setQuestions(quizID);
	}
	
	
	
	public class QuestionInfo {
		
		private int questionID;
		private String questionType;
		private int questionNumber;

		public QuestionInfo(int theQuestionID, String theQuestionType, int theQuestionNumber) {
			questionID = theQuestionID;
			questionType = theQuestionType;
			questionNumber = theQuestionNumber;
		}
		
		public String getQuestionType() {
			return questionType;
		}
		
		public int getQuestionID() {
			return questionID;
		}
		
		public int getQuestionNumber() {
			return questionNumber;
		}
	}
	
	
	
	public String getCreatedBy() throws SQLException {
		int createdByID = rs.getInt("created_by");
		ResultSet currRS = stmt.executeQuery("select * from users where id = '" + createdByID + "'");
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
	
	private void setQuestions(int quizID) throws SQLException {
		ResultSet questionRS = stmt.executeQuery("select * from questions where quiz_id = '" + quizID + "'");
		
		ArrayList<QuestionInfo> questionInfo = new ArrayList<QuestionInfo>();
		while(questionRS.next()) {
			QuestionInfo newQuestion = new QuestionInfo(questionRS.getInt("id"), questionRS.getString("question_type"), questionRS.getInt("question_number"));
		}
		
		
	}
	
	
	
	
	
}
