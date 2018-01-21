package de.mpa.application;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import de.mpa.domain.BasicCondition;

@Local
public interface _ApplicationContractService {
	// Contract itself
	public Response saveContract(String token, String designation, String contractType, String contractSubject);

	public Response updateContract(String token, String designation, String contractType, String contractSubject,
			String contractState, int contractId);

	public Response deleteContract(String token, int contractId);

	public Response getContracts(String token);

	public Response getContract(String token, int contractId);
	
	public Response getUserContractRelationship(int principalId, int userId);

	// Sub section search for contracts ==> Additional resource which won't be
	// persisted

	public Response createContractSearch(String token, String searchText, String country, String zipCode, String city, int radius);

	// Place of performance
	
	public Response createPlaceOfPerformance(String token, String country, String place, String zipCode, int contractId);
	
	public Response updatePlaceOfPerformance(String token, String country, String place, String zipCode, int contractId);
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Task in contract
	public Response saveTask(String token, String description, String type, String subType, int contractId);

	public Response updateTask(String token, String description, String type, String subType, int contractId,
			int taskId);

	public Response deleteTask(String token, int contractId, int taskId);

	public Response getTasks(String token, int contractId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Basic condition in contract
	public Response saveBasicCondition(String token, String startDate, String endDate, boolean teleWorkPossible, int contractId, int estimatedWorkload, double fee);

	public Response deleteBasicCondition(String token, int contractId);

	public Response updateBasicCondition(String token, String startDate, String endDate, boolean teleWorkPossible, int contractId, int basicConditionId, int estimatedWorkload, double fee);

	public Response getBasicCondition(String token, int contractId, int basicConditionId);
	
	public List<BasicCondition> getAllBasicConditions(String token); // Might be used for the matching engine
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Requirement in contract
	public Response saveRequirement(String token, String description, String criteriaType, int contractId);

	public Response deleteRequirement(String token, int contractId, int requirementId);

	public Response updateRequirement(String token, String description, String criteriaType, int contractId,
			int requirementId);

	public Response getRequirements(String token, int contractId, int requirementId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Special condition in contract
	public Response saveTerm(String token, String description, String termType, int contractId);

	public Response deleteTerm(String token, int contractId, int specialConditionId);

	public Response updateTerm(String token, String description, String termType, int contractId,
			int specialConditionId);

	public Response getTerm(String token, int contractId, int specialConditionId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Candidate in contract
	public Response saveCandidate(String token, int contractId);

	public Response updateCandidate(String token, Boolean candidateAccepted, Boolean candidateDeclined, int contractId,
			int candidateId);

	public Response getCandidate(String token, int contractId, int candidateId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Condition offer in contract from candidate or principal
	public Response saveOffer(String token, int contractId, int candidateId, String location, int radius,
			String startDate, String endDate, int estimatedWorkload, double fee);

	public Response getOffers(String token, int contractId, int candidateId);

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}
