package quiz;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {
	private int id;
	private String sender_name;
	private int sender_id;
	private String body;
	private boolean read;
	private int recipient_id;
	private String sent_on;
	
	public static final int NOTE = 1;
	public static final int CHALLENGE = 2;
	public static final int FRIEND_REQUEST = 3;
	public static final int PREVIEW_LENGTH = 300;

	public static Message[] getNotesForRecipient(Connection con, int user_id,
			int message_type) throws SQLException {
		ResultSet rs = con.createStatement()
				.executeQuery("SELECT m . * , users.username FROM messages AS m INNER JOIN (SELECT * , MAX( sent_on ) AS time_stamp FROM messages WHERE recipient = "
						+ user_id
						+ " GROUP BY sender) AS q ON m.sender = q.sender AND m.sent_on = q.time_stamp INNER JOIN users ON m.sender = users.id ");
		return parseResults(rs);
	}
	
	public static Message[] getConversation(Statement stmt, int sender_id, int recipient_id) throws SQLException {
		stmt.executeUpdate("UPDATE messages SET message_read = 1 WHERE sender = " + sender_id + " AND recipient = " + recipient_id);
		ResultSet rs = stmt
		.executeQuery("SELECT messages.*, users.username FROM messages INNER JOIN users ON messages.sender = users.id WHERE recipient IN ("
				+ sender_id + ", " + recipient_id + ")"
				+ " AND sender IN ("
				+ sender_id + ", " + recipient_id + ")"
				+ " ORDER BY sent_on");
		return parseResults(rs);
	}
	
	private static Message[] parseResults(ResultSet rs) throws SQLException {
		rs.last();
		int size = rs.getRow();
		Message[] messages = new Message[size];
		rs.first();
		for (int i = 0; i < size; i++) {
			String sender = rs.getString("username");
			Message m = new Message(rs.getInt("id"), sender, rs.getInt("sender"), rs.getInt("recipient"), rs.getString("body"), rs.getBoolean("message_read"), rs.getTimestamp("sent_on"));
			messages[i] = m;
			rs.next();
		}
		return messages;
	}

	public static int createMessage(Connection con, int sender_id,
			int recipient_id, int type, String body) throws SQLException {
		Statement statement = con.createStatement();
		String created_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());
		PreparedStatement pStmt = con
				.prepareStatement("INSERT INTO messages VALUES (NULL, ?, ?, ?, ?, ?)");
		pStmt.setInt(1, sender_id);
		pStmt.setInt(2, recipient_id);
		pStmt.setString(3, body);
		pStmt.setString(4, created_on);
		pStmt.setBoolean(5, false);
		pStmt.executeUpdate();
		ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
		rs.first();

		return rs.getInt("LAST_INSERT_ID()");
	}
	
	public static String unreadNoteSummary(Statement stmt, int user_id) {
		int numUnread = unreadNoteCount(stmt, user_id);
		if (numUnread == 0) {
			return "No Unread Notes";
		} else if (numUnread == 1) {
			return "1 Unread Note";
		} else {
			return numUnread + " Unread Notes";
		}
	}
	
	public static int unreadNoteCount(Statement stmt, int user_id) {
		try {
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM messages WHERE message_read = 0 AND recipient = " + user_id);
			rs.first();
			return rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Message(int id, String sender, int sender_id, int recipient_id, String body, boolean read, Timestamp sent_on) {
		this.id = id;
		this.sender_name = sender;
		this.sender_id = sender_id;
		this.recipient_id = recipient_id;
		this.body = body;
		this.read = read;
		DateFormat dateInstance = SimpleDateFormat.getDateInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
		this.sent_on  = dateInstance.format(sent_on) + " at " + timeFormat.format(sent_on);
	}

	public int getId() {
		return id;
	}

	public String getSenderName() {
		return sender_name;
	}

	public int getSenderId() {
		return sender_id;
	}

	public String getBody() {
		return body;
	}

	public boolean isRead() {
		return read;
	}

	public int getRecipientId() {
		return recipient_id;
	}
	
	public String getReceivedOn() {
		return sent_on;
	}
	
	public String getPreview() {
		if (body.length() < PREVIEW_LENGTH) {
			return body;
		} else {
			return body.substring(0, PREVIEW_LENGTH) + "...";
		}
	}

}
