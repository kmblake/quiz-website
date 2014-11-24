package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Friend {
	public static final int FROM_USER_PAGE = 1;
	public static final int FROM_MESSAGES_PAGE = 2;
	public static final int PENDING_REQUEST = 3;
	public static final int FRIENDS = 4;
	public static final int NOT_FRIENDS = 5;
	

	public static void createRequest(Connection con, int requested_by, int requested_for) throws SQLException {
		String created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		PreparedStatement pStmt = con
				.prepareStatement("INSERT INTO friends VALUES (NULL, ?, ?, ?, ?)");
		pStmt.setInt(1, requested_by);
		pStmt.setInt(2, requested_for);
		pStmt.setBoolean(3, false);
		pStmt.setString(4, created_on);
		System.out.println(pStmt);
		pStmt.executeUpdate();
	}
	
	public static int friendshipStatus(Statement stmt, int requested_by, int requested_for) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT approved FROM friends WHERE requested_by =" + requested_by + " AND requested_for = " + requested_for);
			if(rs.next()) {
				if (rs.getBoolean("approved")) {
					return FRIENDS;
				} else {
					return PENDING_REQUEST;
				}
			} else {
				return NOT_FRIENDS;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return NOT_FRIENDS;
		}
	}
}
