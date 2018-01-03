package de.mpa.application;

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
	public Contract createContract(int clientID, int principalID, ContractState contractState,
			List<Task> taskDescription, List<Requirement> requirementsProfile, BasicCondition basicConditions,
			List<SpecialCondition> specialConditions, List<Rank> ranking);
	
	public Contract deleteContract(int contractID);
	
	
}
