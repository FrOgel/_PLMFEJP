package de.mpa.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContractState {
	//Attribute declaration
	private int contractStateID;
	private String description;
	//---------------------
	
	
	//Constructor to build a contract state
	public ContractState(int contractStateID, String description) {
		this.contractStateID = contractStateID;
		this.description = description;
	}
	//-------------------------------------


	//Setter and getter
	@XmlElement
	public int getContractStateID() {
		return contractStateID;
	}

	public void setContractStateID(int contractStateID) {
		this.contractStateID = contractStateID;
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
