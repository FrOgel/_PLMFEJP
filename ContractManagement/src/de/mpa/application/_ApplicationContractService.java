package de.mpa.application;

import java.sql.Date;
import java.util.List;

import javax.ejb.Local;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.Rank;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Local
public interface _ApplicationContractService {
	public Contract createContract(String token, String designation);
	
	public Task createTask(String token, String description);
	
	public BasicCondition createBasicCondition(String location, String radius, Date startDate, Date endDate, int estimatedWorkload);
	
	public Requirement createRequirement(String description);
	
	public SpecialCondition createSpecialCondition (String description);
	
	public boolean deleteContract(int contractID);
	
	
}
