package datahandling;

import utils.Time;

public class Task {

	public Time start;
	public Time end;
	
	public String body;
	
	public Task() {
		//TODO del
		start = new Time(8);
		end = new Time(8, 20);
		body = "test body TODO";
	}

	@Override
	public String toString() {
		String str = "";
		str += 	start.toString() + "\n" +
				end.toString() + "\n" +
				body + "\n"
				;
		return str;
	}

}
