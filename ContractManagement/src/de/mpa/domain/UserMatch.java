package de.mpa.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.xml.bind.annotation.XmlRootElement;

//@Entity
@XmlRootElement
public class UserMatch implements Comparable<UserMatch> {

	//@JoinColumn(name = "contractId")
	private int principalId;
	private int contractId;
	private String contractSubject;
	private int userId;

	public UserMatch(int principalId, int contractId, String contractSubject, int userId) {
		this.principalId = principalId;
		this.contractId = contractId;
		this.contractSubject = contractSubject;
		this.userId = userId;
	}

	public int getPrincipalId() {
		return principalId;
	}

	public int getContractId() {
		return contractId;
	}

	public String getContractSubject() {
		return contractSubject;
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public int compareTo(UserMatch m) {

		int compare = 0;
		
		if (this.principalId == m.principalId)
			compare = 0;
		if (this.principalId < m.principalId)
			compare = -1;
		if (this.principalId > m.principalId)
			compare = 1;

		return compare;
	}

}
