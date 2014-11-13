package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionType {
	private String type;
	private String name;
	
	public QuestionType(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public static QuestionType[] getQuestionTypes(Statement stmt) throws SQLException {
		String query = "SELECT type, type_name FROM question_types";
		ResultSet rs = stmt.executeQuery(query);
		rs.last();
		int numTypes = rs.getRow();
		QuestionType[] types = new QuestionType[numTypes];
		rs.first();
		for (int i = 1; i <= numTypes; i++) {
			QuestionType qt = new QuestionType(rs.getString("type"), rs.getString("type_name"));
			types[i - 1] = qt;
			rs.next();
		}
		return types;
	}
}
