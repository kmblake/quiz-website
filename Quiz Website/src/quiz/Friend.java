package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Friend {
	public static final int REQUEST = 1;
	public static final int APPROVE = 2;
	public static final int PENDING_REQUEST = 3;
	public static final int FRIENDS = 4;
	public static final int NOT_FRIENDS = 5;
	public static final int UNFRIEND = 6;
	public static final int DENY = 7;
	
	private int id;
	private int requested_by;
	private String sender;
	

	public static void createRequest(Connection con, int requested_by, int requested_for) throws SQLException {
		String created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		PreparedStatement pStmt = con
				.prepareStatement("INSERT INTO friends VALUES (NULL, ?, ?, ?, ?)");
		pStmt.setInt(1, requested_by);
		pStmt.setInt(2, requested_for);
		pStmt.setBoolean(3, false);
		pStmt.setString(4, created_on);
		pStmt.executeUpdate();
	}
	
	public static int friendshipStatus(Statement stmt, int requested_by, int requested_for) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT approved FROM friends WHERE requested_by IN (" + requested_by + ", " +  requested_for + ") AND requested_for IN (" + requested_by + ", " +  requested_for + ")");
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
	
	public static Friend[] getFriendRequests(Statement stmt, int user_id)
			throws SQLException {
		ResultSet rs = stmt
				.executeQuery("SELECT f.*, u.username FROM friends AS f INNER JOIN users AS u ON f.requested_by = u.id WHERE f.requested_for = "
						+ user_id + " AND approved = 0");
		rs.last();
		int size = rs.getRow();
		Friend[] requests = new Friend[size];
		rs.first();
		for (int i = 0; i < size; i++) {
			String sender = rs.getString("username");
			Friend f = new Friend(rs.getInt("id"), rs.getInt("requested_by"), sender);
			requests[i] = f;
			rs.next();
		}
		return requests;
	}
	
	public static String friendRequestSummary(Statement stmt, int user_id) {
		int rc = getFriendRequestCount(stmt, user_id);
		if (rc == 0) {
			return "No Friend Requests";
		} else if (rc == 1) {
			return "1 Friend Request";
		} else {
			return rc + " Friend Requests";
		}
	}
	
	public static int getFriendRequestCount(Statement stmt, int user_id) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM friends AS f WHERE f.requested_for = " + user_id + " AND approved = 0");
			rs.first();
			return rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		
	}
	
	public static boolean approveFriendship(Statement stmt, int id) {
		try {
			stmt.executeUpdate("UPDATE friends SET approved = 1 WHERE id = " + id);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static void unfriend(Statement stmt, int idA, int idB) throws SQLException {
		stmt.executeUpdate("DELETE FROM friends WHERE requested_by IN (" + idA + ", " +  idB + ") AND requested_for IN (" + idA + ", " +  idB + ")");
	}
	
	public static void deny(Statement stmt, int friendship_id) throws SQLException {
		stmt.executeUpdate("DELETE FROM friends WHERE id = " + friendship_id);
	}
	
	public Friend(int id, int requested_by, String sender) {
		this.id = id;
		this.requested_by = requested_by;
		this.sender = sender;
	}

	public int getId() {
		return id;
	}

	public int getRequestedBy() {
		return requested_by;
	}

	public String getSender() {
		return sender;
	}
}
