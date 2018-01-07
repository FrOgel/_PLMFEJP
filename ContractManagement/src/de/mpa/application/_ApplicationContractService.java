package de.mpa.application;

import java.util.List;

import javax.ejb.Local;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.Contract;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Local
public interface _ApplicationContractService {
	public Contract saveContract(String token, int contractId, String designation, String contractType,
			String contractSubject);

	public Task saveTask(String token, int contractId, int taskId, String description, String type, String subType);

	public BasicCondition saveBasicCondition(String token, int contractId, int basicConditionId, String location,
			int radius, String startDate, String endDate, int estimatedWorkload, double fee);

	public Requirement saveRequirement(String token, int contractId, int requirementId, String description,
			String criteriaType);

	public SpecialCondition saveSpecialCondition(String token, int contractId, int specialConditionId,
			String description);

	public Candidate applyForContract(String token, int contractId);

	public boolean deleteContract(String token, int contractId);

	public boolean deleteTask(String token, int contractId, int taskId);

	public boolean deleteBasicCondition(String token, int contractId);

	public boolean deleteRequirement(String token, int contractId, int requirementId);

	public boolean deleteSpecialCondition(String token, int contractId, int conditionId);

	public List<Contract> getAllContractsFromUser(String token);

	public boolean changeContractState(String token, int contractId, String state);

	public boolean pickCandidate(String token, int contractId, int candidateId, String acceptance);
}
