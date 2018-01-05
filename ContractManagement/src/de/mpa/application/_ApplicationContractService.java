package de.mpa.application;

import javax.ejb.Local;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Local
public interface _ApplicationContractService {
	public Contract createContract(String token, String designation, String contractType, String contractSubject);
	
	public Task createTask(String token, int contractId, String description);
	
	public BasicCondition createBasicCondition(String token, int contractId, String location, String radius, String startDate, String endDate, int estimatedWorkload);
	
	public Requirement createRequirement(String token, String description, int contractId);
	
	public SpecialCondition createSpecialCondition (String token, String description, int contractId);
	
	public boolean deleteContract(String token, int contractId);
	
	public boolean deleteTask(String token, int contractId, int taskId);
	
	public boolean deleteBasicCondition(String token, int contractId);
	
	public boolean deleteRequirement(String token, int contractId, int requirementId);

	public boolean deleteSpecialCondition(String token, int contractId, int conditionId);

}
