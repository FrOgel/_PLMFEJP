package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Task {
	
	//Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskID;
	private String description;
	private TaskType type;
	private TaskSubType subType;
	//---------------------
	
	
	//Constructor to build a task
	public Task() {
		super();
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

	@XmlElement
	public TaskType getType() {
		return type;
	}


	public void setType(TaskType type) {
		this.type = type;
	}

	@XmlElement
	public TaskSubType getSubType() {
		return subType;
	}


	public void setSubType(TaskSubType subType) {
		this.subType = subType;
	}
}
