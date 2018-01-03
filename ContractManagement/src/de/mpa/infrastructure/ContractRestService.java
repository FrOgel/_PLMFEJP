package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.EJB;

import de.mpa.application._ApplicationContractService;
import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.Rank;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

public class ContractRestService implements _ApplicationContractService{
	
	//Inject ApplicationContractService
	@EJB
	_ApplicationContractService ac;

	@Override
	public Contract createContract(int clientID, int principalID, ContractState contractState,
			List<Task> taskDescription, List<Requirement> requirementsProfile, BasicCondition basicConditions,
			List<SpecialCondition> specialConditions, List<Rank> ranking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Contract deleteContract(int contractID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
