package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Requirement {
	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int requirementID;
	@Lob
	private String description;
	@Enumerated(EnumType.STRING)
	private RequirementCriteriaType criteriaType;

	// Constructor to build a requirement
	// ---------------------
	public Requirement() {
		super();
	}

	public Requirement(int requirementID, String description) {
		this.requirementID = requirementID;
		this.description = description;
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

	@XmlElement
	public RequirementCriteriaType getCriteriaType() {
		return criteriaType;
	}

	public void setCriteriaType(RequirementCriteriaType criteriaType) {
		this.criteriaType = criteriaType;
	}
}
