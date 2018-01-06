package de.mpa.application;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.ContractType;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;
import de.mpa.domain.TaskSubType;
import de.mpa.domain.TaskType;
import de.mpa.infrastructure.PersistanceContract;
import de.mpa.infrastructure.SecurityService;

@Stateless
public class ApplicationContractService implements _ApplicationContractService {

	private PersistanceContract pc = new PersistanceContract();
	private SecurityService ss = new SecurityService();

	@Override
	public Contract saveContract(String token, int contractId, String designation, String contractType,
			String contractSubject) {
		if (contractId != 0) {
			Contract c_old = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			Contract c_new = new Contract();
			if (!(designation.equals("")))
				c_new.setDesignation(designation);
			if (!(contractType.equals("")))
				c_new.setType(ContractType.valueOf(contractType.toUpperCase()));
			if (!(contractSubject.equals("")))
				c_new.setSubject(contractSubject);
			return pc.updateContract(c_old, c_new);
		} else {
			Contract c = new Contract();
			c.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));
			c.setDesignation(designation);
			c.setType(ContractType.valueOf(contractType.toUpperCase()));
			c.setSubject(contractSubject);

			return pc.persistContract(c);
		}

	}

	@Override
	public Task saveTask(String token, int contractId, int taskId, String description, String type, String subType) {
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		if (taskId != 0) {
			Task t_old = (Task) pc.getObjectFromPersistanceById(Task.class, taskId);
			Task t_new = new Task();

			if (!(description.equals(""))) {
				t_new.setDescription(description);
			}
			if (!(type.equals(""))) {
				t_new.setType(TaskType.valueOf(type.toUpperCase()));
			}
			if (!(subType.equals(""))) {
				t_new.setSubType(TaskSubType.valueOf(subType.toUpperCase()));
			}

			return pc.updateTask(t_old, t_new);
		} else {
			Task t = new Task();
			t.setDescription(description);
			t.setType(TaskType.valueOf(type.toUpperCase()));
			t.setSubType(TaskSubType.valueOf(subType.toUpperCase()));

			return pc.persistTaskInContract(c, t);
		}

	}

	@Override
	public BasicCondition saveBasicCondition(String token, int contractId, int basicConditionId, String location,
			String radius, String startDate, String endDate, int estimatedWorkload) {
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		BasicCondition b_new = new BasicCondition();
		if(!(endDate.equals(""))) b_new.setEndDate(LocalDate.parse(endDate));
		if(!(startDate.equals(""))) b_new.setStartDate(LocalDate.parse(startDate));
		b_new.setEstimatedWorkload(estimatedWorkload);
		b_new.setLocation(location);
		b_new.setRadius(radius);

		if (contractId != 0) {
			BasicCondition b_old = (BasicCondition) pc.getObjectFromPersistanceById(BasicCondition.class,
					basicConditionId);
			return pc.updateBasicCondition(b_old, b_new);
		} else {
			return pc.persistBasicCondition(c, b_new);
		}

	}

	@Override
	public Requirement saveRequirement(String token, int contractId, int requirementId, String description) {

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		Requirement r = new Requirement();
		r.setDescription(description);

		return pc.persistRequirementInContract(c, r);
	}

	@Override
	public SpecialCondition saveSpecialCondition(String token, int contractId, int specialConditionId,
			String description) {

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		SpecialCondition s = new SpecialCondition();
		s.setDescription(description);

		return pc.persistSpecialConditionInContract(c, s);
	}

	@Override
	public boolean deleteContract(String token, int contractId) {
		ss.authenticateToken(token);
		return pc.deleteContract(contractId);
	}

	@Override
	public boolean deleteTask(String token, int contractId, int taskId) {
		ss.authenticateToken(token);
		return pc.deleteTaskFromContracT(contractId, taskId);
	}

	@Override
	public boolean deleteBasicCondition(String token, int contractId) {
		ss.authenticateToken(token);
		return pc.deleteBasicCondition(contractId);
	}

	@Override
	public boolean deleteRequirement(String token, int contractId, int requirementId) {
		ss.authenticateToken(token);
		return pc.deleteRequirementFromContract(contractId, requirementId);
	}

	@Override
	public boolean deleteSpecialCondition(String token, int contractId, int conditionId) {
		ss.authenticateToken(token);
		return pc.deleteSpecialConditionFromContract(contractId, conditionId);
	}

	@Override
	public List<Contract> getAllContractsFromUser(String token) {
		return pc.findUserContracts(Integer.parseInt(ss.authenticateToken(token)));
	}

	@Override
	public boolean changeContractState(String token, int contractId, String state) {
		ss.authenticateToken(token);
		return pc.changeContractState(contractId, ContractState.valueOf(state.toUpperCase()));
	}

}
