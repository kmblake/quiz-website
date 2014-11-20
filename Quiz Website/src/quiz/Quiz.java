package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.servlet.ServletContext;

public class Quiz {

	private ArrayList<Question> questions;
	private DBConnection con;
	private Statement statement;
	private int quizID;
	
	private ArrayList<QuizHistory> history;
	private String createdBy;
	private String quizDescription;
	private Date dateCreated;
	private boolean multiplePages;
	private boolean randomized;
	private boolean immediateFeedback;
	private String title;
	private boolean practiceMode;
	

	public Quiz(Integer theQuizID, DBConnection theCon) throws SQLException {
		quizID = theQuizID;
		Statement stmt = theCon.getStatement();
		statement = stmt;
		ResultSet rs = stmt.executeQuery("select * from quizzes where id = '" + quizID
				+ "'");
		rs.next();
		setInstanceVars(rs);
		con = theCon;
		questions = new ArrayList<Question>();
		setQuestions(quizID);
		setHistory(theCon);
	}
	
	private void setHistory(DBConnection con) throws SQLException {
		Statement stmt = con.getStatement();
		ResultSet rs = stmt.executeQuery("select * from quiz_history where id = '" + quizID
				+ "'");
		
		history = new ArrayList<QuizHistory>();
		
		while(rs.next()) {
			int userID = rs.getInt("user_id");
			ResultSet userRS = stmt.executeQuery("select * from users where id = '" + userID
				+ "'");
			userRS.next();
			QuizHistory toAdd = new QuizHistory(rs.getInt("score"), rs.getTime("time"), userRS.getString("username"), rs.getDate("taken_on"));
			history.add(toAdd);
		}
		
		Collections.sort(history, new HistoryComparator());
	}
	
	/**
	 * Comparator to sort the history for a quiz from best scores to worst
	 * @author Eric
	 *
	 */
	
	private class HistoryComparator implements Comparator<QuizHistory> {
		
		public int compare(QuizHistory quizOne, QuizHistory quizTwo) {
			int toReturn =  quizOne.getScore() - quizTwo.getScore();
			if(toReturn == 0) {
				// need to figure out how to implement time comparison
			}
			
			return toReturn;
		}
	}
	
	public int getQuizID() {
		return quizID;
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
	
	private void setInstanceVars(ResultSet rs) throws SQLException {
		
		quizDescription = rs.getString("description");
		dateCreated = rs.getDate("created_on");
		multiplePages = rs.getBoolean("multiple_pages");
		randomized = rs.getBoolean("randomized");
		immediateFeedback = rs.getBoolean("immediate_feedback");
		title = rs.getString("title");
		practiceMode = rs.getBoolean("practice_mode");
		
		int createdByID = rs.getInt("created_by");
		ResultSet currRS = statement.executeQuery("select * from users where id = '"
				+ createdByID + "'");
		currRS.next();
		createdBy = currRS.getString("username");
	}

	public String getCreatedBy() throws SQLException {
		return createdBy;
	}

	public String getQuizDescription() throws SQLException {
		return quizDescription;
	}

	public Date getDateCreated() throws SQLException {
		return dateCreated;
	}

	public boolean getIfHasMultiplePages() throws SQLException {
		return multiplePages;
	}

	public boolean getIfRandomized() throws SQLException {
		return randomized;
	}

	public boolean getIfImmediateFeedback() throws SQLException {
		return immediateFeedback;
	}

	public String getTitle() throws SQLException {
		return title;
	}
	
	public int getLength() {
		return questions.size();
	}

	public boolean getIfPracticeMode() throws SQLException {
		return practiceMode;
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
	
	public ArrayList<QuizHistory> getHistory() {
		return history;
	}
}

