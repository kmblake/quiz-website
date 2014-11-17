package quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultipleAnswer extends Question {
	
	public static final String type = "multiple_answer";
	private static final String answersTable = "multiple_answer_answers";
	public static final int type_id = 5;
	private int questionNumber;
	private int questionID;
	private String question;
	private ArrayList<String> answers;

	public MultipleAnswer(Statement stmt, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		question = rs.getString("question");
		
		answers = new ArrayList<String>();
		
		setAnswers(stmt);
	}
	
	public static void storeQuestion(Connection con, int quizId,
			int questionNumber, String question,
			String[] answers) throws SQLException {
		int questionId = Question.storeQuestion(con, quizId, questionNumber, type);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + type + " VALUES(?, ?);");
		pStmt.setInt(1, questionId);
		pStmt.setString(2, question);
		pStmt.executeUpdate();
		storeAnswers(con, questionId, answers);
	}

	private static void storeAnswers(Connection con, int questionId, 
		String[] options) throws SQLException {
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + answersTable +  " VALUES(NULL, ?, ?);");
		for (int i = 0; i < options.length; i++) {
			pStmt.setInt(1, questionId);
			pStmt.setString(2, options[i]);
			pStmt.executeUpdate();
		}
	}
	
	private void setAnswers(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from " + answersTable + " where question_id = '" + questionID + "'");
		while(rs.next()) {
			answers.add(rs.getString("answer"));
		}
	}
	
	public String getQuestion() {
		return question;
	}
	
	public ArrayList<String> getAnswers() {
		return answers;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public int getQuestionNumber() {
		return questionNumber;
	}

	@Override
	public int getQuestionID() {
		return questionID;
	}

}
