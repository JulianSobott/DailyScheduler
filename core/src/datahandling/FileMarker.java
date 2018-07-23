package datahandling;

public enum FileMarker {
	task_begin("<Task>"),
	
	;
	public String mark;
	
	FileMarker(String str) {
		this.mark = str;
	}
}
