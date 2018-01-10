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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(query = "SELECT c FROM Contract c WHERE c.principalID = :principalID", name = "find user contracts"),
		@NamedQuery(query = "SELECT COUNT(c.principalID) FROM Contract c WHERE c.contractID = :contractId AND "
				+ "c.principalID = :requesterId", name = "check requesterId"), })
public class Contract {

	// Attribute declaration
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contractID;
	private int clientID;
	private int principalID;
	private String name;
	private String subject;
	private LocalDateTime creationDate;
	@OneToOne(cascade = CascadeType.ALL)
	private BasicCondition basicConditions;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Task> taskDescription = new ArrayList<Task>();
	@OneToMany(cascade = CascadeType.ALL)
	private List<Term> contractTerms = new ArrayList<Term>();

	@Enumerated(EnumType.STRING)
	private ContractType type;
	@Enumerated(EnumType.STRING)
	private ContractState contractState;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTRACTID")
	private List<Candidate> candidates = new ArrayList<Candidate>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Requirement> requirementsProfile = new ArrayList<Requirement>();

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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public List<Term> getTermId() {
		return this.contractTerms;
	}

	public void setTermId(List<Term> contractTerms) {
		this.contractTerms = contractTerms;
	}

	@XmlElement
	public List<Rank> getRanking() {
		return ranking;
	}

	public void setRanking(List<Rank> ranking) {
		this.ranking = ranking;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	@XmlElement
	public List<Term> getContractTerms() {
		return contractTerms;
	}

	public void setContractTerms(List<Term> contractTerms) {
		this.contractTerms = contractTerms;
	}

}
