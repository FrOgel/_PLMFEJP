package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Requirement {
	//Attribute declaration
	private int requirementID;
	private String description;
	
	
	//Constructor to build a requirement
	//---------------------
	public Requirement(int requirementID, String description) {
		this.requirementID = requirementID;
		this.description = description;
	}
	//----------------------------------

	
	//Setter and getter
	@XmlElement
	public int getRequirementID() {
		return requirementID;
	}

	public void setRequirementID(int requirementID) {
		this.requirementID = requirementID;
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
