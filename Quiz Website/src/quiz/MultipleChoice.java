package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MultipleChoice extends Question {

	public static final String type = "multiple_choice";
	private static final String answersTable = "multiple_choice_answers";
	public static final int type_id = 3;
	private int questionNumber;
	private int questionID;
	private String question;
	private Map<String, Boolean> answer;

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
