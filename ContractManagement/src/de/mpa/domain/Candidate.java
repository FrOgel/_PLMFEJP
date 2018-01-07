package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@IdClass(CandidateId.class)
@XmlRootElement
public class Candidate {

	@Id private int contractId;
	@Id private int candidateId;

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

}
