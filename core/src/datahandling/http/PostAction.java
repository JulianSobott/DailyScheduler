package datahandling.http;

public enum PostAction {
	save("save"),
	
	;
	
	public String action_string;
	
	PostAction(String str) {
		this.action_string = str;
	}
}

