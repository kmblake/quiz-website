package quiz;

public class SearchResult {
	private int id;
	private String name;
	private String username;
	
	public SearchResult(int id, String title) {
		this.id = id;
		this.name = title;
	}
	
	public SearchResult(int id, String name, String username) {
		this.id = id;
		this.name = name;
		this.username = username;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}
}
