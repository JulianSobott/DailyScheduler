package datahandling.http;

public class Response {

	public String message;
	public String status;
	
	public Response() {
		
	}
	
	public Response(String message) {
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
