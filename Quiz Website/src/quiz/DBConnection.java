package quiz;

import java.sql.*;

public class DBConnection {

	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;

	private Connection con;

	public DBConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection
			( "jdbc:mysql://" + server, account, password);

			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Statement getStatement() {
		try {
			return con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Connection getConnection() {
		return con;
	}

	public void close() {
		try {
			this.con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 	
	}
}
