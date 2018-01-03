package de.mpa.application;

import java.util.List;

import javax.ejb.Stateless;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.Rank;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Stateless
public class ApplicationContractService implements _ApplicationContractService{

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
