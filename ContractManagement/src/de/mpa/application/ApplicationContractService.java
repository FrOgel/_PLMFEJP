package de.mpa.application;

import java.time.LocalDate;

import javax.ejb.Stateless;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractType;
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
	public Contract createContract(String token, String designation, String contractType, String contractSubject) {
		
		System.out.println(token);
		
		Contract c = new Contract();
		c.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));
		c.setDesignation(designation);
		c.setType(ContractType.valueOf(contractType.toUpperCase()));
		c.setSubject(contractSubject);
		
		return pc.persistContract(c);
	}

	@Override
	public Task createTask(String token, int contractId, String description) {
		
		Contract c = pc.findContract(contractId);
		
		Task t = new Task();
		t.setDescription(description);
		
		
		return pc.persistTaskInContract(c, t);
	}

	@Override
	public BasicCondition createBasicCondition(String token, int contractId, String location, String radius,
			String startDate, String endDate, int estimatedWorkload) {
		
		Contract c = pc.findContract(contractId);
		
		BasicCondition b = new BasicCondition();
		b.setEndDate(LocalDate.parse(endDate));
		b.setStartDate(LocalDate.parse(startDate));
		b.setEstimatedWorkload(estimatedWorkload);
		b.setLocation(location);
		b.setRadius(radius);
		
		return pc.persistBasicCondition(c, b);
	}

	@Override
	public Requirement createRequirement(String token, String description, int contractId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpecialCondition createSpecialCondition(String token, String description, int contractId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteContract(String token, int contractId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTask(String token, int contractId, int taskId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBasicCondition(String token, int contractId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteRequirement(String token, int contractId, int requirementId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSpecialCondition(String token, int contractId, int conditionId) {
		// TODO Auto-generated method stub
		return false;
	}

}
