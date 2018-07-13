package datahandling;

import java.util.ArrayList;
import java.util.List;

public class Data {

	public List<Task> all_tasks = new ArrayList<Task>(); 
	
	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Task lastTask() {
		return all_tasks.get(all_tasks.size() - 1);
	}
}
