package de.mpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 	frank.vogel
 * Date:	07.01.2018
 * Purpose:	Represents a applicant for a specific contract
 */

@Entity
@XmlRootElement
public class Candidate {

	@EmbeddedId private CandidateId candidateId;
	private boolean accepted;
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("timestamp DESC")
	private List<ConditionOffer> negotiatedConditions = new ArrayList<ConditionOffer>();

	public Candidate() {
		super();
	}
	
	@XmlElement
	public CandidateId getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(CandidateId candidateId) {
		this.candidateId = candidateId;
	}

	@XmlElement
	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@XmlElement
	public List<ConditionOffer> getNegotiatedConditions() {
		return negotiatedConditions;
	}

	public void setNegotiatedConditions(List<ConditionOffer> negotiatedConditions) {
		this.negotiatedConditions = negotiatedConditions;
	}

	

}
