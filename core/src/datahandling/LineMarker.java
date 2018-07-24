package datahandling;

//Every line in file belongs to one of the following markers
public enum LineMarker {
	save_time,
	task_header,
	daily_task_id,
	start,
	end,
	body,
	
	END_OF_FILE
	;
	
	public LineMarker next() {
		switch(this) {
		case save_time: return daily_task_id;
		case daily_task_id: return task_header;
		case task_header: return start;
		case start: return end;
		case end: return body;
		case body: return task_header;
		default: return END_OF_FILE;
		}
	}
}
