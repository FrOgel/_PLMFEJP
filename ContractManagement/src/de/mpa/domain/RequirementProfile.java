package de.mpa.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequirementProfile {
	//Attribute declaration
	private int requirementProfileID;
	private String description;
	private List<Requirement> requirementcatalogue;
	//---------------------

	
	//Constructor to build a basic condition
	public RequirementProfile(int requirementProfileID, String description, List<Requirement> requirementcatalogue) {
		this.requirementProfileID = requirementProfileID;
		this.description = description;
		this.requirementcatalogue = requirementcatalogue;
	}
	//--------------------------------------

	
	//Setter and getter
	@XmlElement
	public int getRequirementProfileID() {
		return requirementProfileID;
	}

	public void setRequirementProfileID(int requirementProfileID) {
		this.requirementProfileID = requirementProfileID;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public List<Requirement> getRequirementcatalogue() {
		return requirementcatalogue;
	}

	public void setRequirementcatalogue(List<Requirement> requirementcatalogue) {
		this.requirementcatalogue = requirementcatalogue;
	}	
	//-----------------
}
