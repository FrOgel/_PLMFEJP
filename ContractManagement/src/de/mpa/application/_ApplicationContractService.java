package de.mpa.application;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

@Local
public interface _ApplicationContractService {
	public Response saveContract(String token, int contractId, String designation, String contractType,
			String contractSubject);

	public Response saveTask(String token, int contractId, int taskId, String description, String type, String subType);

	public Response saveBasicCondition(String token, int contractId, int basicConditionId, String location,
			int radius, String startDate, String endDate, int estimatedWorkload, double fee);

	public Response saveRequirement(String token, int contractId, int requirementId, String description,
			String criteriaType);

	public Response saveSpecialCondition(String token, int contractId, int specialConditionId,
			String description);

	public Response applyForContract(String token, int contractId);

	public Response deleteContract(String token, int contractId);

	public Response deleteTask(String token, int contractId, int taskId);

	public Response deleteBasicCondition(String token, int contractId);

	public Response deleteRequirement(String token, int contractId, int requirementId);

	public Response deleteSpecialCondition(String token, int contractId, int conditionId);

	public Response getAllContractsFromUser(String token);
	
	//public Response getMyAppliedContracts(String token);

	public Response changeContractState(String token, int contractId, String state);

	//Method to accept or decline a candidate by the principal of the specific contract
	public Response pickCandidate(String token, int contractId, int candidateId, String acceptance);
	
	public Response makeOffer(String token, int contractId, int candidateId, String location,
			int radius, String startDate, String endDate, int estimatedWorkload, double fee);
	
	public Response cancelNegotiation(String token, int contractId, int candidateId);
	
	public Response acceptOffer(String token, int contractId, int candidateId);
	
}
