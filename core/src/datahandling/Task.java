package datahandling;

import utils.Time;

public class Task {

	public Time start;
	public Time end;
	
	public String body;
	
	public Task() {
	}

	@Override
	public String toString() {
		String str = "";
		str += 	Time.toInt(start) + "\n" +
				Time.toInt(end) + "\n" +
				body + "\n"
				;
		return str;
	}

}
