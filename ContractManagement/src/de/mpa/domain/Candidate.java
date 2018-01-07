package de.mpa.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 	frank.vogel
 * Date:	07.01.2018
 * Purpose:	Represents a applicant for a specific contract
 */
@Entity
@IdClass(CandidateId.class)
@XmlRootElement
public class Candidate {

	@EmbeddedId
	private CandidateId candidateId;
	private boolean accepted;
	private BasicCondition negotiatedConditions;

	public Candidate() {
		super();
	}
	
	@XmlElement
	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

	@XmlElement
	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
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
	public BasicCondition getNegotiatedConditions() {
		return negotiatedConditions;
	}

	public void setNegotiatedConditions(BasicCondition negotiatedConditions) {
		this.negotiatedConditions = negotiatedConditions;
	}

}
