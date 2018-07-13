package datahandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import datahandling.http.PostAction;
import datahandling.http.Response;

public class DataHandler {
	private ServerCommunicator serverCommunicator;
	private Data data;
	
	private String dataString;
	
	public DataHandler() {
		this.data = new Data();
		
		serverCommunicator = new ServerCommunicator();
	}

	public void save() {
		dataString = createDataString();
		saveLocal();
		saveOnline();
		
	}
	
	public void load() {
		FileHandle file = Gdx.files.local("assets/save_file.txt");
		String t_data = file.readString();
		System.out.println(t_data);
	}
	
	private String createDataString() {
		String str = "";
		for(Task t : data.all_tasks) {
			str += FileMarker.task_begin.mark + "\n";
			str += t.toString();
		}
		
		return str;
	}
	
	
	public void addNewTask(Task t) {
		this.data.all_tasks.add(t);
	}
	
	private void saveLocal() {
		FileHandle file = Gdx.files.local("save_file.txt");
		file.writeString(dataString, false);
	}
	
	private void saveOnline() {
		Response response = serverCommunicator.post(PostAction.save, dataString);
		System.out.println(response.toString());
	}
}
