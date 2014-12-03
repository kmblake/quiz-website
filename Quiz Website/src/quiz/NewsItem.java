package quiz;

import java.sql.*;
import java.text.SimpleDateFormat;

public class NewsItem implements Comparable<NewsItem> {
	public static final String CREATED = "created";
	public static final String TOOK = "took";
	
	private String verb;
	private String username;
	private int user_id;
	private String quiz_title;
	private int quiz_id;
	private Timestamp happened_on;
	
	public NewsItem(String verb, String username, int user_id,
			String quiz_title, int quiz_id, Timestamp happened_on) {
		this.verb = verb;
		this.username = username;
		this.user_id = user_id;
		this.quiz_title = quiz_title;
		this.quiz_id = quiz_id;
		this.happened_on = happened_on;
	}
	
	public String getVerb() {
		return verb;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public String getQuizTitle() {
		return quiz_title;
	}
	
	public int getQuizId() {
		return quiz_id;
	}
	
	public Timestamp getHappenedOn() {
		return happened_on;
	}
	
	public String getHappenedOnString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
		return dateFormat.format(happened_on) + " at " + timeFormat.format(happened_on);
	}

	public int compareTo(NewsItem n) {
		return n.getHappenedOn().compareTo(this.happened_on);
	}
	
	// return date

}
