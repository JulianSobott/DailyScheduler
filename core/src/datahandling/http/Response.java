package datahandling.http;

public class Response {

	public String message;
	public Status status;
	
	public Response() {
		
	}
	
	public Response(Status s, String message) {
		this.status = s;
		this.message = message;
	}

	@Override
	public String toString() {
		String str = "";
		str += status + "\n";
		str += message + "\n";
		return str;
	}
	
	
}
