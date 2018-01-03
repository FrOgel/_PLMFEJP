package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.mpa.application._ApplicationContractService;
import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.Rank;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Path("/contract")
@Produces(MediaType.APPLICATION_JSON)
public class ContractRestService implements _ApplicationContractService{
	
	//Inject ApplicationContractService
	@EJB
	_ApplicationContractService ac;

	@Override
	public Contract createContract(int clientID, int principalID, ContractState contractState,
			List<Task> taskDescription, List<Requirement> requirementsProfile, BasicCondition basicConditions,
			List<SpecialCondition> specialConditions, List<Rank> ranking) {
		return ac.createContract(clientID, principalID, contractState, taskDescription, requirementsProfile, basicConditions, specialConditions, ranking);
	}

	@Override
	public boolean deleteContract(int contractID) {
		// TODO Auto-generated method stub
		return ac.deleteContract(contractID);
	}
	
	
}
