package de.mpa.application;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import de.mpa.domain.BasicCondition;

@Local
public interface _ApplicationContractService {
	// Contract itself
	public Response saveContract(Integer httpRequesterId,String designation, String contractType, String contractSubject);

	public Response updateContract(Integer httpRequesterId,String designation, String contractType, String contractSubject,
			String contractState, Integer contractId);

	public Response deleteContract(Integer httpRequesterId,Integer contractId);

	public Response getContracts(Integer httpRequesterId);

	public Response getContract(Integer httpRequesterId,Integer contractId);

	public Response getUserContractRelationship(Integer principalId, Integer userId);

	// Sub section search for contracts ==> Additional resource which won't be
	// persisted

	public Response createContractSearch(Integer httpRequesterId,String searchText, String country, String zipCode, String city,
			int radius);

	// Place of performance

	public Response createPlaceOfPerformance(Integer httpRequesterId,String country, String place, String zipCode,
			int contractId);

	public Response updatePlaceOfPerformance(Integer httpRequesterId,String country, String place, String zipCode,
			int contractId);

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Task in contract
	public Response saveTask(Integer httpRequesterId,String description, String type, String subType, int contractId);

	public Response updateTask(Integer httpRequesterId,String description, String type, String subType, int contractId,
			int taskId);

	public Response deleteTask(Integer httpRequesterId,int contractId, int taskId);

	public Response getTasks(Integer httpRequesterId,int contractId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Basic condition in contract
	public Response saveBasicCondition(Integer httpRequesterId,String startDate, String endDate, boolean teleWorkPossible,
			int contractId, int estimatedWorkload, double fee);

	public Response deleteBasicCondition(Integer httpRequesterId,int contractId);

	public Response updateBasicCondition(Integer httpRequesterId,String startDate, String endDate, boolean teleWorkPossible,
			int contractId, int basicConditionId, int estimatedWorkload, double fee);

	public Response getBasicCondition(Integer httpRequesterId,int contractId, int basicConditionId);

	public List<BasicCondition> getAllBasicConditions(Integer httpRequesterId); // Might be used for the matching engine
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Requirement in contract
	public Response saveRequirement(Integer httpRequesterId,String description, String criteriaType, int contractId);

	public Response deleteRequirement(Integer httpRequesterId,int contractId, int requirementId);

	public Response updateRequirement(Integer httpRequesterId,String description, String criteriaType, int contractId,
			int requirementId);

	public Response getRequirements(Integer httpRequesterId,int contractId, int requirementId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Special condition in contract
	public Response saveTerm(Integer httpRequesterId,String description, String termType, int contractId);

	public Response deleteTerm(Integer httpRequesterId,int contractId, int specialConditionId);

	public Response updateTerm(Integer httpRequesterId,String description, String termType, int contractId,
			int specialConditionId);

	public Response getTerm(Integer httpRequesterId,int contractId, int specialConditionId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Candidate in contract
	public Response saveCandidate(Integer httpRequesterId,int contractId);

	public Response updateCandidate(Integer httpRequesterId, Boolean candidateAccepted, Boolean candidateDeclined, int contractId,
			int candidateId);

	public Response getCandidate(Integer httpRequesterId,int contractId, int candidateId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Condition offer in contract from candidate or principal
	public Response saveOffer(Integer httpRequesterId,String startDate, String endDate, String comment, boolean teleWorkPossible,
			int contractId, int estimatedWorkload, double fee, Integer candidateId);

	public Response acceptOffer(Integer httpRequesterId, Integer offerId, Integer contractId);
	
	public Response getOffers(Integer httpRequesterId,int contractId, int candidateId);

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	// Get enums
	public Response getContractStates();
	
	public Response getContractType();
	
	public Response getRequirementCriteriaType();
	
	public Response getTaskType();
	
	public Response getTaskSubType();
	
	public Response getTermType();
	
}
