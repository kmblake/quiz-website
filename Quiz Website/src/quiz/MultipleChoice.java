package quiz;

import java.sql.*;
import java.util.*;

public class MultipleChoice extends Question {

	public static final String type = "multiple_choice";
	private static final String answersTable = "multiple_choice_answers";
	public static final int type_id = 3;
	private int questionNumber;
	private int questionID;
	private String question;
	private Map<String, Boolean> answer;
	
	public static void storeQuestion(Connection con, int quizId,
			int questionNumber, String question,
			String[] options, int answerIndex) throws SQLException {
		int questionId = Question.storeQuestion(con, quizId, questionNumber, type);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + type + " VALUES(?, ?);");
		pStmt.setInt(1, questionId);
		pStmt.setString(2, question);
		pStmt.executeUpdate();
		storeOptions(con, questionId, options, answerIndex);
	}

	private static void storeOptions(Connection con, int questionId, 
		String[] options, int answerIndex) throws SQLException {
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + answersTable +  " VALUES(NULL, ?, ?, ?);");
		for (int i = 0; i < options.length; i++) {
			pStmt.setInt(1, questionId);
			pStmt.setString(2, options[i]);
			pStmt.setBoolean(3, i == answerIndex);
			pStmt.executeUpdate();
		}
	}

	public MultipleChoice(DBConnection con, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		Statement stmt = con.getStatement();
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		if (rs.next()) {
			question = rs.getString("question");
		}
		answer = new HashMap<String, Boolean>();

		setAnswers(stmt);
	}

	private void setAnswers(Statement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery("select * from " + answersTable + " where question_id = '" + questionID + "'");
		while(rs.next()) {
			answer.put(rs.getString("answer"), rs.getBoolean("correct"));
		}
	}

	public Map<String, Boolean> getAnswers() {
		return answer;
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

	public String getQuestion() {
		return question;
	}

}
