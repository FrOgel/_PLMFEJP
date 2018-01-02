package de.mpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskDescription {
	//Attribute declaration
	private int taskDescriptionID;
	private String description;
	private List<Task> TaskList;
	//---------------------
	
	
	//Constructor to build a task description
	public TaskDescription(int taskDescriptionID, String description, List<Task> taskList) {
		this.taskDescriptionID = taskDescriptionID;
		this.description = description;
		TaskList = taskList;
	}
	//---------------------------------------

	
	//Setter and getter
	@XmlElement
	public int getTaskDescriptionID() {
		return taskDescriptionID;
	}

	public void setTaskDescriptionID(int taskDescriptionID) {
		this.taskDescriptionID = taskDescriptionID;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public List<Task> getTaskList() {
		return TaskList;
	}

	public void setTaskList(List<Task> taskList) {
		TaskList = taskList;
	}
	//-----------------
}
