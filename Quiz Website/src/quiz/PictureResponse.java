package quiz;

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


	public PictureResponse(DBConnection con, int theQuestionID, int theQuestionNumber) throws SQLException {
		questionNumber = theQuestionNumber;
		questionID = theQuestionID;
		Statement stmt = con.getStatement();
		ResultSet rs = stmt.executeQuery("select * from " + type + " where question_id = '" + questionID + "'");
		if (rs.next()) {
			imageURL = rs.getString("image_url");
			answer = rs.getString("answer");
		}
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
	
	public String getQuestion() {
		//TODO
		return "";
	}

}
