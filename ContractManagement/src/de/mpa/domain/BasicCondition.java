package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BasicCondition {
	//Attribute declaration
	private int conditionID;
	private String description;
	//---------------------
	
	
	//Constructor to build a basic condition
	public BasicCondition(int conditionID, String description) {
		this.conditionID = conditionID;
		this.description = description;
	}
	//--------------------------------------
	
	
	//Setter and getter
	@XmlElement
	public int getConditionID() {
		return conditionID;
	}

	public void setConditionID(int conditionID) {
		this.conditionID = conditionID;
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
