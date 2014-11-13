package quiz;

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
	
	
	public FillInTheBlank(Statement stmt, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		question = rs.getString("question");
		answer = rs.getString("answer");
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

}
