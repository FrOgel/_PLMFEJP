package de.mpa.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author frank.vogel Date: 07.01.2018 Purpose: Represents a applicant for a
 *         specific contract
 */

@Entity
@NamedQuery(query = "SELECT COUNT(c.candidateId) FROM Candidate c WHERE c.candidateId = :candidateId", name = "does candidate exist")
@XmlRootElement
public class Candidate {

	@EmbeddedId
	private CandidateId candidateId;
	private Boolean candidateAccepted;
	@OneToOne(cascade = CascadeType.ALL)
	private BasicCondition acceptedCondition;
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
	public Boolean isCandidateAccepted() {
		return candidateAccepted;
	}

	public void setCandidateAccepted(boolean candidateAccepted) {
		this.candidateAccepted = candidateAccepted;
	}

	@XmlElement
	public List<ConditionOffer> getNegotiatedConditions() {
		return negotiatedConditions;
	}

	public void setNegotiatedConditions(List<ConditionOffer> negotiatedConditions) {
		this.negotiatedConditions = negotiatedConditions;
	}

	@XmlElement
	public BasicCondition getAcceptedCondition() {
		return acceptedCondition;
	}

	public void setAcceptedCondition(BasicCondition acceptedCondition) {
		this.acceptedCondition = acceptedCondition;
	}

}
