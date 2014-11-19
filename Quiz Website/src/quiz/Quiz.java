package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.servlet.ServletContext;

public class Quiz {

	private ArrayList<Question> questions;
	private DBConnection con;
	private Statement stmt;
	private ResultSet rs;

	public Quiz(Integer quizID, DBConnection con) throws SQLException {
		stmt = con.getStatement();
		rs = stmt.executeQuery("select * from quizzes where id = '" + quizID
				+ "'");
		rs.next();
		this.con = con;
		questions = new ArrayList<Question>();
		setQuestions(quizID);
	}

	public static int createQuiz(Connection con, String title, String description,String username,
			boolean randomized, boolean multiple_pages,
			boolean immediate_feedback, boolean practice_mode) throws SQLException {
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("select id from users where username = '" + username + "'");
		rs.first();
		int user_id = rs.getInt("id");
		String created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO quizzes(title, created_by, created_on, " +
				"description, randomized, multiple_pages, immediate_feedback, " +
		"practice_mode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		pStmt.setString(1, title);
		pStmt.setInt(2, user_id);
		pStmt.setString(3, created_on);
		pStmt.setString(4, description);
		pStmt.setBoolean(5, randomized);
		pStmt.setBoolean(6, multiple_pages);
		pStmt.setBoolean(7, immediate_feedback);
		pStmt.setBoolean(8, practice_mode);
		pStmt.executeUpdate();
		rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
		rs.first();

		return rs.getInt("LAST_INSERT_ID()");
	}

	public class QuestionInfo {

		private int questionID;
		private String questionType;
		private int questionNumber;

		public QuestionInfo(int theQuestionID, String theQuestionType, int theQuestionNumber) {
			questionID = theQuestionID;
			questionType = theQuestionType;
			questionNumber = theQuestionNumber;
		}

		public String getQuestionType() {
			return questionType;
		}

		public int getQuestionID() {
			return questionID;
		}

		public int getQuestionNumber() {
			return questionNumber;
		}
		
		public void setQuestionNumber(int newNum) {
			questionNumber = newNum;
		}

	}
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public String getCreatedBy() throws SQLException {
		int createdByID = rs.getInt("created_by");
		Statement temp = con.getStatement();
		ResultSet currRS = temp.executeQuery("select * from users where id = '"
				+ createdByID + "'");
		currRS.next();
		System.out.println(currRS.getString("username"));
		return currRS.getString("username");
	}

	public String getQuizDescription() throws SQLException {
		return rs.getString("description");
	}

	public Date getDateCreated() throws SQLException {
		return rs.getDate("created_on");
	}

	public boolean getIfHasMultiplePages() throws SQLException {
		return rs.getBoolean("multiple_pages");
	}

	public boolean getIfRandomized() throws SQLException {
		return rs.getBoolean("randomized");
	}

	public boolean getIfImmediateFeedback() throws SQLException {
		return rs.getBoolean("immediate_feedback");
	}

	public String getTitle() throws SQLException {
		return rs.getString("title");
	}

	public boolean getIfPracticeMode() throws SQLException {
		return rs.getBoolean("practice_mode");
	}

	/**
	 * Comparator class for sorting questions into the right order if they have an order.
	 * @author Eric
	 */

	private class CustomComparator implements Comparator<QuestionInfo> {
		public int compare(QuestionInfo questionOne, QuestionInfo questionTwo) {
			return questionOne.getQuestionNumber() - questionTwo.getQuestionNumber();
		}
	}


	private void setQuestions(int quizID) throws SQLException {
		Statement temp = con.getStatement();
		ResultSet questionRS = temp.executeQuery("select * from questions where quiz_id = '" + quizID + "'");

		ArrayList<QuestionInfo> questionInfo = new ArrayList<QuestionInfo>();
		while(questionRS.next()) {
			QuestionInfo newQuestion = new QuestionInfo(questionRS.getInt("question_id"), questionRS.getString("question_type"), questionRS.getInt("question_number"));
			questionInfo.add(newQuestion);
		}

		// Sort or randomize as necessary
		if(getIfRandomized()) {
			Collections.shuffle(questionInfo);
			for(int i=0;i<questionInfo.size();i++) {
				QuestionInfo currQuestion = questionInfo.get(i);
				currQuestion.setQuestionNumber(i+1);
			}
		} else {
			Collections.sort(questionInfo, new CustomComparator());
		}

		setQuestionsArray(questionInfo);
	}

	private void setQuestionsArray(ArrayList<QuestionInfo> questionInfo) throws SQLException {
		ArrayList<Question> questionsArray = new ArrayList<Question>();
		for(int i=0; i<questionInfo.size(); i++) {
			QuestionInfo currQuestionInfo = questionInfo.get(i);

			int questionID = currQuestionInfo.getQuestionID();
			String questionType = currQuestionInfo.getQuestionType();
			int questionNumber = currQuestionInfo.getQuestionNumber();

			if(questionType.equals("question_response")) {
				questionsArray.add(new QuestionResponse(con, questionID, questionNumber));
			} else if(questionType.equals("fill_in_the_blank")) {
				questionsArray.add(new FillInTheBlank(con, questionID, questionNumber));
			} else if(questionType.equals("multiple_choice")) {
				questionsArray.add(new MultipleChoice(con, questionID, questionNumber));
			} else if(questionType.equals("picture_response")) {
				questionsArray.add(new PictureResponse(con, questionID, questionNumber));
			} else if(questionType.equals("multiple_answer")) {
				questionsArray.add(new MultipleAnswer(con, questionID, questionNumber));
			}
		}
		questions = questionsArray;
	}
}

