package de.mpa.domain;

import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Contract {
	
	//Attribute declaration
	private int clientID;
	private int principalID;
	private ContractState contractState;
	private Calendar creationDate;
	private Calendar startDate;
	private Calendar endDate;
	private int estimatedWorkload;
	private TaskDescription taskDescription;
	private RequirementProfile requirementsProfile;
	private ConditionOffer contractConditions;
	private List<Rank>ranking;
	//---------------------
	
	
	//Constructor to build a contract object	
	public Contract(int clientID, int principalID, ContractState contractState, Calendar creationDate,
			Calendar startDate, Calendar endDate, int estimatedWorkload, TaskDescription taskDescription,
			RequirementProfile requirementsProfile, ConditionOffer contractConditions, List<Rank> ranking) {
		this.clientID = clientID;
		this.principalID = principalID;
		this.contractState = contractState;
		this.creationDate = creationDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.estimatedWorkload = estimatedWorkload;
		this.taskDescription = taskDescription;
		this.requirementsProfile = requirementsProfile;
		this.contractConditions = contractConditions;
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
	public Calendar getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	@XmlElement
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	@XmlElement
	public int getEstimatedWorkload() {
		return estimatedWorkload;
	}
	
	public void setEstimatedWorkload(int estimatedWorkload) {
		this.estimatedWorkload = estimatedWorkload;
	}
	
	@XmlElement
	public TaskDescription getTaskDescription() {
		return taskDescription;
	}
	
	public void setTaskDescription(TaskDescription taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	@XmlElement
	public RequirementProfile getRequirementsProfile() {
		return requirementsProfile;
	}
	
	public void setRequirementsProfile(RequirementProfile requirementsProfile) {
		this.requirementsProfile = requirementsProfile;
	}
	
	@XmlElement
	public ConditionOffer getContractConditions() {
		return contractConditions;
	}
	
	public void setContractConditions(ConditionOffer contractConditions) {
		this.contractConditions = contractConditions;
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
