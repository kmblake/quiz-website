package quiz;

import java.sql.*;
import java.util.*;

public class MultipleChoiceMultipleAnswer extends Question {
	
	public static final String type = "multiple_choice_multiple_answer";
	private static final String answersTable = "multiple_choice_answers";
	public static final int type_id = 6;
	private int questionNumber;
	private int questionID;
	private String question;
	private Map<String, Boolean> answer;
	
	public static void storeQuestion(Connection con, int quizId,
			int questionNumber, String question,
			String[] options, String[] answers) throws SQLException {
		int questionId = Question.storeQuestion(con, quizId, questionNumber, type);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + type + " VALUES(?, ?);");
		pStmt.setInt(1, questionId);
		pStmt.setString(2, question);
		pStmt.executeUpdate();
		storeOptions(con, questionId, options, answers);
	}

	private static void storeOptions(Connection con, int questionId, 
		String[] options, String[] stringAnswers) throws SQLException {
		ArrayList<Integer> answers = answersToList(stringAnswers);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + answersTable +  " VALUES(NULL, ?, ?, ?);");
		for (int i = 0; i < options.length; i++) {
			pStmt.setInt(1, questionId);
			pStmt.setString(2, options[i]);
			pStmt.setBoolean(3, answers.contains(i + 1));
			pStmt.executeUpdate();
		}
	}
	
	private static ArrayList<Integer> answersToList(String[] correctStrings) {
		ArrayList<Integer> correctInts = new ArrayList<Integer>(correctStrings.length);
		for (String s : correctStrings) {
			correctInts.add(Integer.parseInt(s));
		}
		return correctInts;
	}

	public MultipleChoiceMultipleAnswer(Statement stmt, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		question = rs.getString("question");
		
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
	
	public boolean isCorrect(String userAnswer) {
		return true;
		//TODO
	}

	@Override
	public String getAnswer() {
		ArrayList<String> answerList = new ArrayList<String>();
		for (String a : answer.keySet()) {
			if(answer.get(a))
				answerList.add(a);
		}
		StringBuilder string = new StringBuilder(); 
		string.append(answerList.get(0));
		for (int i = 1; i < answerList.size(); i++) {
			string.append(answerList.get(i));
		}
		return string.toString();
	}

}
