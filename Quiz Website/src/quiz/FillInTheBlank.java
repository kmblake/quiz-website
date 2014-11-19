package quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FillInTheBlank extends Question {

	public static final String type = "fill_in_the_blank";
	public static final int type_id = 2;
	private int questionNumber;
	private int questionID;
	private String question;
	private String answer;


	public FillInTheBlank(DBConnection con, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		Statement stmt = con.getStatement();
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		if (rs.next()) {
			question = rs.getString("question");
			answer = rs.getString("answer");
		}
	}
	
	public static void storeQuestion(Connection con, int questionNumber, int quizId, String question, String answer) throws SQLException {
		int questionId = Question.storeQuestion(con, quizId, questionNumber, type);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + type + " VALUES(?, ?, ?);");
		pStmt.setInt(1, questionId);
		pStmt.setString(2, question);
		pStmt.setString(3, answer);
		pStmt.executeUpdate();
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

	public String getAnswer() {
		return answer;
	}

	public boolean isCorrect(String userAnswer) {
		return userAnswer.equals(this.answer);
		//TODO
	}
}
