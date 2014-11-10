package quiz;

import java.sql.*;

public class AccountManager {
	
	DBConnection database;
	Statement stmt;
	
	public AccountManager(DBConnection database) {
		this.database = database;
		this.stmt = database.getStatement();
	}
	
	public boolean accountExists(String user) {
			//TODO
		return false;
	}
	
	public boolean passwordMatches(String user, String pass) {
		if (accountExists(user)) {
			//TODO
			//return (database.get(user).equals(pass));
			return false;
		}
		return false;
	}
	
	public void createAccount(String user, String pass) {
		//database.put(user, pass);
		//TODO
	}
}
