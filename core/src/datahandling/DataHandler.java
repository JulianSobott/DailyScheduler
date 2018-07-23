package datahandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import datahandling.http.PostAction;
import datahandling.http.Response;
import utils.Time;

public class DataHandler {
	private final String FILE_PATH = "assets/save_file.txt";
	private ServerCommunicator serverCommunicator;
	private Data data;
	
	
	private String dataString;
	
	private LineMarker currentLinemarker = LineMarker.task_header;
	
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
		data.all_tasks.clear();
		int online_time;
		if(getLocalTime() > (online_time = getOnlineTime())) {
			loadLocal();
		}else if(online_time != 0) {
			loadOnline();
		}
		else {
			System.err.println("No File to Load from (neither online nor local)");
		}
	}
	
	private String createDataString() {
		String str = "";
		str += System.currentTimeMillis() + "\n"; //TODO change to Time.currentTime()
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
		FileHandle file = Gdx.files.local(FILE_PATH);
		file.writeString(dataString, false);
	}
	
	private void saveOnline() {
		Response response = serverCommunicator.post(PostAction.save, dataString);
	}
	
	private void loadLocal() {
		
	}
	
	private void loadOnline() {
		FileHandle file = Gdx.files.local(FILE_PATH);
		String t_data = file.readString();
		int idx_endLine;
		while((idx_endLine = t_data.indexOf('\n')) != -1) {
			String line = t_data.substring(0, idx_endLine);
			t_data = t_data.substring(idx_endLine + 1);
			evaluate_loaded_line(line);
		}
	}
	
	private void evaluate_loaded_line(String line) {
		switch(this.currentLinemarker) {
		case save_time:
			break;
		case task_header:
			data.all_tasks.add(new Task());
			break;
		case start:
			Time t = Time.intToTime(Integer.parseInt(line));
			data.lastTask().start = t;
			break;
		case end:
			t = Time.intToTime(Integer.parseInt(line));
			data.lastTask().end = t;
			break;
		case body:
			data.lastTask().body = line;
			break;
		default:
			System.err.println("Unknown enum type in: DataHandler evaluate_loaded_line " + this.currentLinemarker);
		}
		this.currentLinemarker = this.currentLinemarker.next();
	}
	
	private int getLocalTime() {
		FileHandle file = Gdx.files.local(FILE_PATH);
		if(!file.exists())
			return 0;
		String t_data = file.readString();
		int idx_endLine = t_data.indexOf('\n');
		if(idx_endLine == -1)
			return 0;
		String line = t_data.substring(0, idx_endLine);
		line = line.replace("\r", "");
		line = line.replace("\n", "");
		
		return Integer.parseInt(line);
	}
	
	private int getOnlineTime() {
		//TODO implement
		return 0;
	}
}
