package de.mpa.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQuery(query = "SELECT c FROM Contract c WHERE c.principalID = :principalID", name = "find user contracts")
public class Contract {

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contractID;
	private int clientID;
	private int principalID;
	private String designation;
	@Enumerated(EnumType.STRING)
	private ContractType type;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Candidate> candidates = new ArrayList<Candidate>();
	private String subject;
	private LocalDateTime creationDate;
	@Enumerated(EnumType.STRING)
	private ContractState contractState;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Task> taskDescription = new ArrayList<Task>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<Requirement> requirementsProfile = new ArrayList<Requirement>();
	// The condition offer consists of the basicConditions and the specialConditions
	@OneToOne(cascade = CascadeType.ALL)
	private BasicCondition basicConditions;
	@OneToMany(cascade = CascadeType.ALL)
	private List<SpecialCondition> specialConditions = new ArrayList<SpecialCondition>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<Rank> ranking = new ArrayList<Rank>();
	// ---------------------

	// Constructor to build a contract object
	public Contract() {
		super();
		this.creationDate = LocalDateTime.now();
		this.contractState = ContractState.NOT_PUBLISHED;
	}
	// --------------------------------------

	// Setter and getter
	@XmlElement
	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	@XmlElement
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@XmlElement
	public ContractType getType() {
		return type;
	}

	public void setType(ContractType type) {
		this.type = type;
	}

	@XmlElement
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@XmlElement
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
	public LocalDateTime getCreationDate() {
		return creationDate;
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
	// -----------------

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

}
