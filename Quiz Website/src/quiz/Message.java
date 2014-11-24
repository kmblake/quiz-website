package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

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

	public static Message[] getMessagesForRecipient(Statement stmt,
			int user_id, int message_type) throws SQLException {
		ResultSet rs = stmt
				.executeQuery("SELECT messages.*, users.username FROM messages INNER JOIN users ON messages.sender = users.id WHERE recipient = "
						+ user_id
						+ " AND message_type_id = "
						+ message_type
						+ " ORDER BY sent_on DESC");
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
				.prepareStatement("INSERT INTO messages VALUES (NULL, ?, ?, ?, ?, ?, ?)");
		pStmt.setInt(1, sender_id);
		pStmt.setInt(2, recipient_id);
		pStmt.setInt(3, type);
		pStmt.setString(4, body);
		pStmt.setString(5, created_on);
		pStmt.setBoolean(6, false);
		pStmt.executeUpdate();
		ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
		rs.first();

		return rs.getInt("LAST_INSERT_ID()");
	}

	public Message(int id, String sender, int sender_id, int recipient_id, String body, boolean read, Timestamp sent_on) {
		this.id = id;
		this.sender_name = sender;
		this.sender_id = sender_id;
		this.recipient_id = recipient_id;
		this.body = body;
		this.read = read;
		SimpleDateFormat stringForm = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		this.sent_on = stringForm.format(sent_on);
	}

	public int getType() {
		return NOTE;
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

}
