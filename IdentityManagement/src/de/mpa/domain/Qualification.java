package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author frank.vogel created on: 06.01.2018 purpose: Model for the
 *         qualifications of a client
 */
@Entity
@XmlRootElement
public class Qualification {

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requirementID;
	private String description;

	// Constructor to build a requirement
	public Qualification() {
		super();
	}
	// ----------------------------------

	// Setter and getter
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
	// -----------------
}
