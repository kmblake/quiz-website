package quiz;

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
