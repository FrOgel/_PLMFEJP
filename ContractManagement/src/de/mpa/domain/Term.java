package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 	frank.vogel
 * Date: 	10.01.2018
 * Purpose:	Class for setting the contract terms
 */
@Entity
@XmlRootElement
public class Term{

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int termId;
	private String description;
	@Enumerated(EnumType.STRING)
	private TermType termType;
	// ---------------------

	// Constructor to build a special condition
	public Term() {
		super();
	}

	public Term(int specialConditionID, String description) {
		this.termId = specialConditionID;
		this.description = description;
	}
	// ----------------------------------------

	// Setter and getter
	@XmlElement
	public int getTermId() {
		return termId;
	}

	public void setTermId(int specialConditionID) {
		this.termId = specialConditionID;
	}

	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TermType getTermType() {
		return termType;
	}

	public void setTermType(TermType termType) {
		this.termType = termType;
	}

	
}
