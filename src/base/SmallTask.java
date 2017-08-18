package base;

import base.Task.TaskBody;

public class SmallTask {

	private int priority;
	private TaskBody taskBody;

	public SmallTask(int priority, TaskBody taskBody){
		this.priority = priority;
		this.taskBody = taskBody;
	}
	
	public int execute(){
		return taskBody.execute();
	}
	
	public int getPriority(){
		return priority;
	}

}
