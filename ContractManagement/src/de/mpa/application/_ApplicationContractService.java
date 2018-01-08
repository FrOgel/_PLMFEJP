package de.mpa.application;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

@Local
public interface _ApplicationContractService {
	// Contract itself
	public Response saveContract(String token, String designation, String contractType, String contractSubject,
			int contractId);

	public Response updateContract(String token, String designation, String contractType, String contractSubject,
			int contractId);

	public Response deleteContract(String token, int contractId);

	public Response getContracts(String token);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Task in contract
	public Response saveTask(String token, String description, String type, String subType, int contractId);

	public Response updateTask(String token, String description, String type, String subType, int contractId,
			int taskId);

	public Response deleteTask(String token, int contractId, int taskId);

	public Response getTasks(String token, int contractId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Basic condition in contract
	public Response saveBasicCondition(String token, String location, String startDate, String endDate, int contractId,
			int basicConditionId, int radius, int estimatedWorkload, double fee);

	public Response deleteBasicCondition(String token, int contractId);

	public Response updateBasicCondition(String token, String location, String startDate, String endDate,
			int contractId, int basicConditionId, int radius, int estimatedWorkload, double fee);

	public Response getBasicCondition(String token, int contractId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Requirement in contract
	public Response saveRequirement(String token, String description, String criteriaType, int contractId);

	public Response deleteRequirement(String token, int contractId, int requirementId);

	public Response updateRequirement(String token, int contractID);

	public Response getRequirement(String token, int contractId, int requirementId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Special condition in contract
	public Response saveSpecialCondition(String token, String description, int contractId);

	public Response deleteSpecialCondition(String token, int contractId, int conditionId);

	public Response updateSpecialCondition(String token, int contractId, int conditionId);

	public Response getSpecialCondition(String token, int contractId, int conditionId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Candidate in contract
	public Response saveCandidate(String token, int contractId);

	public Response deleteCandidate(String token, int contractId, int candidateId);

	public Response updateCandidate(String token, int contractId, int candidateId);

	public Response getCandidate(String token, int contractId, int candidateId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Condition offer in contract from candidate or principal
	public Response saveOffer(String token, int contractId, int candidateId, String location, int radius,
			String startDate, String endDate, int estimatedWorkload, double fee);
	
	public Response updateOffer(String token, int contractId, int candidateId, String location, int radius,
			String startDate, String endDate, int estimatedWorkload, double fee);
	
	public Response getOffer(String token, int contractId, int candidateId);
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	
	// public Response getMyAppliedContracts(String token);

	public Response changeContractState(String token, int contractId, String state);

	// Method to accept or decline a candidate by the principal of the specific
	// contract
	public Response pickCandidate(String token, int contractId, int candidateId, String acceptance);

	

	public Response cancelNegotiation(String token, int contractId, int candidateId);

	public Response acceptOffer(String token, int contractId, int candidateId);

}
