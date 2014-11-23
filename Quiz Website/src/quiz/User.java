package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
	private Statement stmt;
	private int id;
	
	public User(int id, Statement stmt) {
		this.stmt = stmt;
		this.id = id;
	}
	
	public User(String username, Statement stmt) throws SQLException {
		this.stmt = stmt;
		ResultSet rs = stmt.executeQuery("select id from users where username = '" + username + "'");
		rs.next();
		id = rs.getInt("id");
	}
	
	public String getFullName() {
		try {
			ResultSet rs = stmt.executeQuery("select * from users where id = '" + id + "'");
			rs.next();
			return rs.getString("first_name") + " " + rs.getString("last_name");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public String getUsername() {
		try {
			ResultSet rs = stmt.executeQuery("select * from users where id = '" + id + "'");
			rs.next();
			return rs.getString("username");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
	}
	
	public int getId() {
		return id;
	}
}
