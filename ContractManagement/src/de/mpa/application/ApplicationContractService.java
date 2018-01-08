package de.mpa.application;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.CandidateId;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.ContractType;
import de.mpa.domain.CriteriaType;
import de.mpa.domain.NegotiationCondition;
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

		Contract c_new = new Contract();
		if (!(designation.equals(""))) {
			c_new.setDesignation(designation);
		}

		if (!(contractType.equals(""))) {
			c_new.setType(ContractType.valueOf(contractType.toUpperCase()));
		}

		if (!(contractSubject.equals(""))) {
			c_new.setSubject(contractSubject);
		}

		if (contractId != 0) {
			Contract c_old = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			return pc.updateContract(c_old, c_new);
		} else {
			c_new.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));
			return pc.persistContract(c_new);
		}

	}

	@Override
	public Task saveTask(String token, int contractId, int taskId, String description, String type, String subType) {

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

		if (taskId != 0) {
			Task t_old = (Task) pc.getObjectFromPersistanceById(Task.class, taskId);
			return pc.updateTask(t_old, t_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			return pc.persistTaskInContract(c, t_new);
		}

	}

	@Override
	public BasicCondition saveBasicCondition(String token, int contractId, int basicConditionId, String location,
			int radius, String startDate, String endDate, int estimatedWorkload, double fee) {
		
		BasicCondition b_new = new BasicCondition();
		if (!(endDate.equals("")))
			b_new.setEndDate(LocalDate.parse(endDate));
		if (!(startDate.equals("")))
			b_new.setStartDate(LocalDate.parse(startDate));
		b_new.setEstimatedWorkload(estimatedWorkload);
		b_new.setLocation(location);
		b_new.setRadius(radius);
		b_new.setFee(fee);

		if (basicConditionId != 0) {
			BasicCondition b_old = (BasicCondition) pc.getObjectFromPersistanceById(BasicCondition.class,
					basicConditionId);
			return pc.updateBasicCondition(b_old, b_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			return pc.persistBasicConditionInContract(c, b_new);
		}
	}

	@Override
	public Requirement saveRequirement(String token, int contractId, int requirementId, String description,
			String criteriaType) {

		Requirement r_new = new Requirement();
		r_new.setDescription(description);
		if (!(criteriaType.equals("")))
			r_new.setCriteriaType(CriteriaType.valueOf(criteriaType.toUpperCase()));

		if (requirementId != 0) {
			Requirement r_old = (Requirement) pc.getObjectFromPersistanceById(Requirement.class, requirementId);
			return pc.updateRequirement(r_old, r_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			return pc.persistRequirementInContract(c, r_new);
		}

	}

	@Override
	public SpecialCondition saveSpecialCondition(String token, int contractId, int specialConditionId,
			String description) {

		SpecialCondition s_new = new SpecialCondition();
		s_new.setDescription(description);

		if (specialConditionId != 0) {
			SpecialCondition s_old = (SpecialCondition) pc.getObjectFromPersistanceById(SpecialCondition.class,
					specialConditionId);
			return pc.updateSpecialCondition(s_old, s_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			return pc.persistSpecialConditionInContract(c, s_new);
		}

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

	@Override
	public Candidate applyForContract(String token, int contractId) {

		String tokenSubject = ss.authenticateToken(token);
		int principalId = Integer.parseInt(tokenSubject);

		CandidateId candidateId = new CandidateId(contractId, principalId);

		Candidate can = (Candidate) pc.getObjectFromPersistanceById(Candidate.class, candidateId);

		if (can == null) {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			can = new Candidate();
			can.setAccepted(false);
			can.setCandidateId(candidateId);
			can = pc.persistCandidateInContract(c, can);
			return can;
		} else {
			return null;
		}

	}

	@Override
	public boolean pickCandidate(String token, int contractId, int candidateId, String acceptance) {

		ss.authenticateToken(token);

		switch (acceptance.toUpperCase()) {
		case "ACCEPT":
			return pc.acceptCandidateInContract(new CandidateId(contractId, candidateId));
		case "DECLINE":
			return pc.deleteCandidateFromContract(new CandidateId(contractId, candidateId));
		default:
			return false;
		}

	}

	
	@Override
	public boolean makeOffer(String token, int contractId, int candidateId, int basicConditionId) {
		
		int token_subject_id = Integer.parseInt(ss.authenticateToken(token));
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		int senderId = 0;
		int receiverId = 0;
		
		if(token_subject_id == candidateId) {
			senderId = candidateId;
			receiverId = c.getPrincipalID();
		}else {
			senderId = c.getPrincipalID();
			receiverId = token_subject_id;
		}
		
		NegotiationCondition nc = new NegotiationCondition();
			nc.setReceiverId(receiverId);
			nc.setSenderId(senderId);
			
		return pc.addOfferToCandidateContract(new CandidateId(contractId, candidateId), basicConditionId, nc);
	}

	
	@Override
	public boolean cancelNegotiation(String token, int contractId, int candidateId) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public boolean acceptOffer(String token, int contractId, int candidateId) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
