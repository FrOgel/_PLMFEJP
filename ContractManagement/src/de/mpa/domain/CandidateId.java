package de.mpa.domain;

import java.io.Serializable;

/**
 * @author 	frank.vogel
 * Date:	07.01.2018
 * Purpose:	ID Class for the candidate class
 */

public class CandidateId implements Serializable{
	
	int contractId;
	int candidateId;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + candidateId;
		result = prime * result + contractId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidateId other = (CandidateId) obj;
		if (candidateId != other.candidateId)
			return false;
		if (contractId != other.contractId)
			return false;
		return true;
	}

}
