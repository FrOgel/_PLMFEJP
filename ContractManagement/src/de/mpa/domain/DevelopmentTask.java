package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class DevelopmentTask extends Task{

	// Attribute declaration
	@Enumerated(EnumType.STRING)
	private TaskType type;
	@Enumerated(EnumType.STRING)
	private TaskSubType subType;
	// ---------------------

	// Constructor to build a task
	public DevelopmentTask() {
		super();
	}
	// ---------------------------

	// Setter and getter	
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

