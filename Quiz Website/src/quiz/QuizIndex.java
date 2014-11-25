package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Set;

public class QuizIndex {
	HashMap<Integer, String> index;
	Statement stmt;

	public QuizIndex(DBConnection connection) {
		index = new HashMap<Integer, String>();
		this.stmt = connection.getStatement();
	}

	public void loadAllQuizzes() {
		index.clear();

		StringBuilder query = new StringBuilder("SELECT id, title FROM quizzes");
		ResultSet rs;

		try {
			rs = stmt.executeQuery(query.toString());

			while(rs.next()) {
				Integer quizID = rs.getInt("id");
				String quizTitle = rs.getString("title");
				index.put(quizID, quizTitle);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} 	
	}
	
	public Set<Integer> getKeys() {
		return index.keySet();
	}
	
	public HashMap<Integer, String> getIndex() {
		return index;
	}
}
