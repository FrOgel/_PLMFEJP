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

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@XmlRootElement
@NamedQueries({
		@NamedQuery(query = "SELECT c FROM Contract c WHERE c.principalID = :principalID", name = "find user contracts"),
		@NamedQuery(query = "SELECT COUNT(c.principalID) FROM Contract c WHERE c.contractID = :contractId AND "
				+ "c.principalID = :requesterId", name = "check requesterId"),
		@NamedQuery(query = "SELECT c FROM Contract c WHERE c.searchString LIKE :searchString", name = "search contracts") })
public class Contract {
	
	
	//Different declined views on the contract based on the user relationship to the contract
	//Not fully implemented
	public static class InternalView extends PrincipalView{}
	public static class PrincipalView extends ContractorView{}
	public static class ContractorView {}
	public static class CandidateView {}
	public static class Viewer {}

	// Attribute declaration
	@JsonView(Contract.InternalView.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contractID;
	@JsonView(Contract.InternalView.class)
	private int clientID;
	@JsonView(Contract.InternalView.class)
	private int principalID;
	@JsonView(Contract.Viewer.class)
	private String name;
	@JsonView(Contract.Viewer.class)
	private String subject;
	@JsonView(Contract.InternalView.class)
	private LocalDateTime creationDate;
	@OneToOne(cascade = CascadeType.ALL)
	@JsonView(Contract.InternalView.class)
	private BasicCondition basicConditions;
	@OneToMany(cascade = CascadeType.ALL)
	@JsonView(Contract.Viewer.class)
	private List<Task> taskDescription = new ArrayList<Task>();
	@OneToMany(cascade = CascadeType.ALL)
	@JsonView(Contract.InternalView.class)
	private List<Term> contractTerms = new ArrayList<Term>();
	@Enumerated(EnumType.STRING)
	@JsonView(Contract.Viewer.class)
	private ContractType type = null;
	@Enumerated(EnumType.STRING)
	@JsonView(Contract.InternalView.class)
	private ContractState contractState = null;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTRACTID")
	@JsonView(Contract.InternalView.class)
	private List<Candidate> candidates = new ArrayList<Candidate>();
	@OneToMany(cascade = CascadeType.ALL)
	@JsonView(Contract.Viewer.class)
	private List<Requirement> requirementsProfile = new ArrayList<Requirement>();
	@JsonView(Contract.InternalView.class)
	private String searchString = "";
	@OneToOne(cascade = CascadeType.ALL)
	@JsonView(Contract.Viewer.class)
	private PlaceOfPerformance placeOfPerformance;
	
	
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
		if (this.searchString != null) {
			if (!(this.searchString.equals(""))) {
				this.removeFromSearchString(this.name);
			}

			this.setSearchString(name);
		}

		this.name = name;

	}

	@XmlElement
	public ContractType getType() {
		return type;
	}

	public void setType(ContractType type) {
		if (this.searchString != null) {
			if (this.type != null) {
				this.removeFromSearchString(this.type.toString());
			}
			this.setSearchString(type.toString());
		}

		this.type = type;

	}

	@XmlElement
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		if (this.searchString != null) {
			if ((!(this.searchString.equals(""))) && (this.subject != null)) {
				this.removeFromSearchString(this.subject);
			}

			this.setSearchString(subject);
		}
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

	public String getSearchString() {
		return searchString;
	}

	private void setSearchString(String addString) {
		if (this.searchString != null) {
			this.searchString = this.searchString + addString;
		} else {
			this.searchString = addString;
		}

	}

	private void removeFromSearchString(String removeString) {
		if ((this.searchString != null) && (!(this.searchString.equals(""))) && (removeString != null)) {
			System.out.println(this.searchString);
			this.searchString = this.searchString.replace(removeString, "");
			System.out.println("ok");
		}
	}

	@XmlElement
	public PlaceOfPerformance getPlaceOfPerformance() {
		return placeOfPerformance;
	}
	
	public void setPlaceOfPerformance(PlaceOfPerformance placeOfPerformance) {
		this.placeOfPerformance = placeOfPerformance;
	}

}
