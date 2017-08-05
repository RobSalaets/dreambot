package base;

public class Task {
	
	public interface TaskBody{
		public int execute();
	}

	private String label;
	private TaskBody body;
	
	public Task(String label, TaskBody body){
		this.body = body;
		this.label = label;
	}
	
	public int execute(){
		return body.execute();
	}
	
	public String getLabel(){
		return label;
	}
}
