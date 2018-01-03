package de.mpa.application;

import java.sql.Date;
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
import de.mpa.infrastructure.SecurityService;

@Stateless
public class ApplicationContractService implements _ApplicationContractService{

	private PersistanceContract pc = new PersistanceContract();
	private SecurityService ss = new SecurityService();
	
	@Override
	public Contract createContract(String token, String designation) {
		
		Contract c = new Contract();
		c.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));
		c.setDesignation(designation);
		pc.persistContract(c);
		
		return c;
	}

	@Override
	public Task createTask(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicCondition createBasicCondition(String location, String radius, Date startDate, Date endDate,
			int estimatedWorkload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Requirement createRequirement(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpecialCondition createSpecialCondition(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteContract(int contractID) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
