package de.mpa.domain;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Contract {
	
	//Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contractID;
	private int clientID;
	private int principalID;
	private Calendar creationDate;
	private ContractState contractState;
	private List<Task> taskDescription;
	private List<Requirement>requirementsProfile;
	//The condition offer consists of the basicConditions and the specialConditions
	private BasicCondition basicConditions;
	private List<SpecialCondition> specialConditions;
	private List<Rank> ranking;
	//---------------------
	
	
	//Constructor to build a contract object
	public Contract() {
		super();
	}
	public Contract(int clientID, int principalID, ContractState contractState,
			List<Task> taskDescription, List<Requirement> requirementsProfile, BasicCondition basicConditions,
			List<SpecialCondition> specialConditions, List<Rank> ranking) {
		super();
		this.clientID = clientID;
		this.principalID = principalID;
		this.creationDate = Calendar.getInstance();
		this.contractState = contractState;
		this.setTaskDescription(taskDescription);
		this.requirementsProfile = requirementsProfile;
		this.basicConditions = basicConditions;
		this.specialConditions = specialConditions;
		this.ranking = ranking;
	}
	//--------------------------------------
	
	
	
	//Setter and getter	
	@XmlElement
	public int getClientID() {
		return clientID;
	}
	
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	public int getContractID() {
		return contractID;
	}
	
	public void setContractID(int contractID) {
		this.contractID = contractID;
	}
	
	@XmlElement
	public int getPrincipalID() {
		return principalID;
	}
	
	public void setPrincipalID(int principalID) {
		this.principalID = principalID;
	}
	
	@XmlElement
	public ContractState getContractState() {
		return contractState;
	}
	
	public void setContractState(ContractState contractState) {
		this.contractState = contractState;
	}
	
	@XmlElement
	public Calendar getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	
	@XmlElement
	public List<Task> getTaskDescription() {
		return taskDescription;
	}
	
	public void setTaskDescription(List<Task> taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	@XmlElement
	public List<Requirement> getRequirementsProfile() {
		return requirementsProfile;
	}
	
	public void setRequirementsProfile(List<Requirement> requirementsProfile) {
		this.requirementsProfile = requirementsProfile;
	}
	
	@XmlElement
	public BasicCondition getBasicConditions() {
		return basicConditions;
	}
	
	public void setBasicConditions(BasicCondition basicConditions) {
		this.basicConditions = basicConditions;
	}
	
	@XmlElement
	public List<SpecialCondition> getSpecialConditions() {
		return specialConditions;
	}
	
	public void setSpecialConditions(List<SpecialCondition> specialConditions) {
		this.specialConditions = specialConditions;
	}
	
	@XmlElement
	public List<Rank> getRanking() {
		return ranking;
	}
	
	public void setRanking(List<Rank> ranking) {
		this.ranking = ranking;
	}
	//-----------------
	
}
