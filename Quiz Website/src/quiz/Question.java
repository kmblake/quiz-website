package quiz;

import java.sql.*;

public abstract class Question {
	
	public abstract String getType();
	
	public static int storeQuestion(Connection con, int quizId, int questionNum, String type) throws SQLException {
		PreparedStatement pStmt = con.prepareStatement("INSERT INTO questions VALUES(NULL, ?, ?, ?);");
		pStmt.setInt(1, quizId);
		pStmt.setInt(2, questionNum);
		pStmt.setString(3, type);
		pStmt.executeUpdate();
		ResultSet rs = con.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
		rs.first();
		return rs.getInt("LAST_INSERT_ID()");
	}
}
