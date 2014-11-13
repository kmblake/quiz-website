package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MultipleChoice extends Question {
	
	public static final String type = "multiple_choice";
	public static final int type_id = 3;
	private int questionNumber;
	private int questionID;
	private String question;
	private ArrayList<String> answer;

	public MultipleChoice(Statement stmt, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		question = rs.getString("question");
		
		setAnswers();
	}
	
	private void setAnswers() {
		
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
