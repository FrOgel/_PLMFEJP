package de.mpa.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.Rank;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;
import de.mpa.infrastructure.PersistanceContract;

@Stateless
public class ApplicationContractService implements _ApplicationContractService{

	private PersistanceContract pc = new PersistanceContract();
	
	
	@Override
	public Contract createContract(int clientID, int principalID, ContractState contractState,
			List<Task> taskDescription, List<Requirement> requirementsProfile, BasicCondition basicConditions,
			List<SpecialCondition> specialConditions, List<Rank> ranking) {
		
		Contract c = new Contract();
		//Setting mandatory contract data
		c.setBasicConditions(basicConditions);
		c.setSpecialConditions(specialConditions);
		c.setPrincipalID(principalID);
		c.setContractState(contractState);
		c.setCreationDate(Calendar.getInstance());
		c.setRanking(new ArrayList<Rank>());
		c.setRequirementsProfile(new ArrayList<Requirement>());
		c.setSpecialConditions(new ArrayList<SpecialCondition>());
		c.setTaskDescription(new ArrayList<Task>());

		
		return null;
	}

	@Override
	public boolean deleteContract(int contractID) {
		// TODO Auto-generated method stub
		return true;
	}

}
