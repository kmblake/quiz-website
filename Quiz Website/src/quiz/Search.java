package quiz;

import java.sql.*;

public class Search {
	private SearchResult[] quizResults;
	private SearchResult[] userResults;
	private Statement stmt;
	public static int USERS_ONLY = 1;
	public static int QUIZZES_ONLY = 2;
	public static int BOTH = 3;

	public Search(Statement stmt, String query, int filter) {
		this.stmt = stmt;
		if (filter == USERS_ONLY) {
			updateUserResults(query);
			quizResults = new SearchResult[0];
		} else if (filter == QUIZZES_ONLY) {
			updateQuizResults(query);
			userResults = new SearchResult[0];
		} else if (filter == BOTH) {
			updateUserResults(query);
			updateQuizResults(query);
		}
	}
	
	public SearchResult[] getQuizResults() {
		return quizResults;
	}
	
	public SearchResult[] getUserResults() {
		return userResults;
	}

	private void updateUserResults(String query) {
		try {
			String sqlQuery = "SELECT id, first_name, last_name, username from users WHERE first_name like %"
					+ query
					+ "% OR last_name like %"
					+ query
					+ "% OR username like %" + query + "%";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			if (rs.last()) {
				userResults = new SearchResult[rs.getRow()];
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					userResults[i] = new SearchResult(rs.getInt("id"),
							rs.getString("first_name") + rs.getString("last_name"), rs.getString("username"));
					i++;
				}
			} else {
				userResults = new SearchResult[0];
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void updateQuizResults(String query) {
		try {
			String sqlQuery = "SELECT id, title from quizzes WHERE title like %"
					+ query + "%";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			if (rs.last()) {
				quizResults = new SearchResult[rs.getRow()];
				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					quizResults[i] = new SearchResult(rs.getInt("id"),
							rs.getString("name"));
					i++;
				}
			} else {
				quizResults = new SearchResult[0];
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
