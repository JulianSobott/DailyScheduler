package datahandling;

import gui.TaskField;
import utils.Time;

public class Task {

	public Time start;
	public Time end;
	
	public String body;
	
	public static int num_daily_tasks = 0;
	private final int DAILY_ID;
	
	public Task() {
		Task.num_daily_tasks++;
		this.DAILY_ID = Task.num_daily_tasks;
	}

	@Override
	public String toString() {
		String str = "";
		str += 	this.DAILY_ID + "\n" +
				Time.toInt(start) + "\n" +
				Time.toInt(end) + "\n" +
				body + "\n"
				;
		return str;
	}

	
	//===========GETTER & SETTER==================
	public int getDailyID() {
		return this.DAILY_ID;
	}
}


