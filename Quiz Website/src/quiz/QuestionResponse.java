package quiz;

import java.sql.*;

public class QuestionResponse extends Question {
	public static final String type = "question_response";
	public static final int type_id = 1;

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

}
