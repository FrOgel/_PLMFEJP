package de.mpa.application;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

@Local
public interface _ApplicationContractService {
	// Contract itself
	public Response saveContract(String token, String designation, String contractType, String contractSubject);

	public Response updateContract(String token, String designation, String contractType, String contractSubject,
			String contractState, int contractId);

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

	public Response getBasicCondition(String token, int contractId, int basicConditionId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Requirement in contract
	public Response saveRequirement(String token, String description, String criteriaType, int contractId);

	public Response deleteRequirement(String token, int contractId, int requirementId);

	public Response updateRequirement(String token, String description, String criteriaType, int contractId,
			int requirementId);

	public Response getRequirements(String token, int contractId, int requirementId);
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	// Special condition in contract
	public Response saveSpecialCondition(String token, String description, int contractId);

	public Response deleteSpecialCondition(String token, int contractId, int specialConditionId);

	public Response updateSpecialCondition(String token, String description, int contractId, int specialConditionId);

	public Response getSpecialCondition(String token, int contractId, int specialConditionId);
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

	public Response getOffer(String token, int contractId, int candidateId);

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}
