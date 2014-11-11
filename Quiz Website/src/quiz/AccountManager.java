package quiz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AccountManager {
	
	public static final String ALGORITHM = "SHA";
	
	private DBConnection database;
	private Statement stmt;
	
	
	public AccountManager(DBConnection database) {
		this.database = database;
		this.stmt = database.getStatement();
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	 */
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	public boolean accountExists(String user) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT username from users WHERE user = " + user);
			return rs.next();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}
	
	private String passHash(String password) {
		MessageDigest md;
		String hash = "";
		try {
			md = MessageDigest.getInstance(ALGORITHM);
			hash = hexToString(md.digest(password.getBytes()));

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	
	public boolean passwordMatches(String user, String pass) {
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT username from users WHERE user = " + user);
			rs.next();
			String hash = rs.getString("password");
			return passHash(pass).equals(hash);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void createAccount(String first, String last, String user, String pass) {
		String hash = passHash(pass);
		String query = "INSERT INTO metropolises VALUES(\"" + first +  "\",\"" + last + "\",\"" + user + "\",\"" + hash + "\")";
		try {
			stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
