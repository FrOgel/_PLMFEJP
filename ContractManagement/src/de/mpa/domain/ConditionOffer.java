package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConditionOffer {
	//Attribute declaration
	private int conditionOfferID;
	private String description;
	//---------------------
	
	
	//Constructor to build a condition offer
	public ConditionOffer(int conditionOfferID, String description) {
		this.conditionOfferID = conditionOfferID;
		this.description = description;
	}	
	//--------------------------------------
	
	
	//Setter and getter
	@XmlElement
	public int getConditionOfferID() {
		return conditionOfferID;
	}

	public void setConditionOfferID(int conditionOfferID) {
		this.conditionOfferID = conditionOfferID;
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
