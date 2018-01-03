package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {
	
	//Attribute declaration
	private int taskID;
	private String description;
	//---------------------
	
	
	//Constructor to build a task
	public Task(int taskID, String description) {
		this.taskID = taskID;
		this.description = description;
	}
	//---------------------------


	//Setter and getter
	@XmlElement
	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	//-----------------
}
