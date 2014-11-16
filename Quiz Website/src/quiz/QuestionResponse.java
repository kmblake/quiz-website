package quiz;

import java.sql.*;

public class QuestionResponse extends Question {
	public static final String type = "question_response";
	public static final int type_id = 1;
	private int questionNumber;
	private int questionID;
	private String question;
	private String answer;

	public QuestionResponse(DBConnection con, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		Statement stmt = con.getStatement();
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		if (rs.next()) {
			question = rs.getString("question");
			answer = rs.getString("answer");
		}
	}

	@Override
	public String getType() {
		return type;
	}

	public static void storeQuestion(Connection con, int questionNumber, int quizId, String question, String answer) throws SQLException {
		int questionId = Question.storeQuestion(con, quizId, questionNumber, type);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO question_response VALUES(?, ?, ?);");
		pStmt.setInt(1, questionId);
		pStmt.setString(2, question);
		pStmt.setString(3, answer);
		pStmt.executeUpdate();
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

	public String getAnswer() {
		return answer;
	}

}
