package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Term {
	//Attribute declaration
	private int termID;
	private String description;
	//---------------------
	
	
	//Constructor to build a term
	public Term(int termID, String description) {
		this.termID = termID;
		this.description = description;
	}
	//---------------------------
	
	
	//Setter and getter
	@XmlElement
	public int getTermID() {
		return termID;
	}

	public void setTermID(int termID) {
		this.termID = termID;
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
