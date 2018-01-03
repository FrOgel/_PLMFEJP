package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SpecialCondition { 
	
	//Attribute declaration
	private int specialConditionID;
	private String description;
	//---------------------
	
	
	//Constructor to build a special condition
	public SpecialCondition(int specialConditionID, String description) {
		this.specialConditionID = specialConditionID;
		this.description = description;
	}
	//----------------------------------------


	//Setter and getter
	@XmlElement
	public int getSpecialConditionID() {
		return specialConditionID;
	}

	public void setSpecialConditionID(int specialConditionID) {
		this.specialConditionID = specialConditionID;
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
