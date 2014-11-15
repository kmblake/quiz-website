package quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PictureResponse extends Question {
	
	public static final String type = "picture_response";
	public static final int type_id = 4;
	private int questionNumber;
	private int questionID;
	private String imageURL;
	private String answer;
	
	public PictureResponse(Statement stmt, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		imageURL = rs.getString("image_url");
		answer = rs.getString("answer");
	}
	
	public static void storeQuestion(Connection con, int questionNumber, int quizId, String imageURL, String answer) throws SQLException {
		int questionId = Question.storeQuestion(con, quizId, questionNumber, type);
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO " + type + " VALUES(?, ?, ?);");
		pStmt.setInt(1, questionId);
		pStmt.setString(2, imageURL);
		pStmt.setString(3, answer);
		pStmt.executeUpdate();
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	public String getAnswer() {
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

}
