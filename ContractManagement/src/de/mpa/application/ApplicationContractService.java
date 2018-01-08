package de.mpa.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response saveContract(String token, int contractId, String designation, String contractType,
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
			c_new = pc.updateContract(c_old, c_new);
		} else {
			c_new.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));
			c_new = pc.persistContract(c_new);
		}
		
		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response saveTask(String token, int contractId, int taskId, String description, String type, String subType) {

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
			t_new = pc.updateTask(t_old, t_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			t_new = pc.persistTaskInContract(c, t_new);
		}

		return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response saveBasicCondition(String token, int contractId, int basicConditionId, String location,
			int radius, String startDate, String endDate, int estimatedWorkload, double fee) {

		BasicCondition b_new = new BasicCondition();
		if (!(endDate.equals("")))
			b_new.setEndDate(endDate);
		if (!(startDate.equals("")))
			b_new.setStartDate(startDate);
		b_new.setEstimatedWorkload(estimatedWorkload);
		b_new.setLocation(location);
		b_new.setRadius(radius);
		b_new.setFee(fee);
		

		if (basicConditionId != 0) {
			BasicCondition b_old = (BasicCondition) pc.getObjectFromPersistanceById(BasicCondition.class,
					basicConditionId); // ==> Bad -> better: only hand over the basic condition id 
			b_new = pc.updateBasicCondition(b_old, b_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			b_new = pc.persistBasicConditionInContract(c, b_new);
		}
		
		return Response.ok(b_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response saveRequirement(String token, int contractId, int requirementId, String description,
			String criteriaType) {

		Requirement r_new = new Requirement();
		r_new.setDescription(description);
		if (!(criteriaType.equals("")))
			r_new.setCriteriaType(CriteriaType.valueOf(criteriaType.toUpperCase()));

		if (requirementId != 0) {
			Requirement r_old = (Requirement) pc.getObjectFromPersistanceById(Requirement.class, requirementId);
			r_new = pc.updateRequirement(r_old, r_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			r_new = pc.persistRequirementInContract(c, r_new);
		}

		return Response.ok(r_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response saveSpecialCondition(String token, int contractId, int specialConditionId,
			String description) {

		SpecialCondition s_new = new SpecialCondition();
		s_new.setDescription(description);

		if (specialConditionId != 0) {
			SpecialCondition s_old = (SpecialCondition) pc.getObjectFromPersistanceById(SpecialCondition.class,
					specialConditionId);
			s_new = pc.updateSpecialCondition(s_old, s_new);
		} else {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			s_new = pc.persistSpecialConditionInContract(c, s_new);
		}
		
		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteContract(String token, int contractId) {
		ss.authenticateToken(token);
		if (pc.deleteContract(contractId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.notModified("Contract").build();
		}
	}

	@Override
	public Response deleteTask(String token, int contractId, int taskId) {
		ss.authenticateToken(token);
		if(pc.deleteTaskFromContracT(contractId, taskId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.notModified("Task").build();
		}
	}

	@Override
	public Response deleteBasicCondition(String token, int contractId) {
		ss.authenticateToken(token);
		if(pc.deleteBasicCondition(contractId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.notModified("Basic condition").build();
		}
	}

	@Override
	public Response deleteRequirement(String token, int contractId, int requirementId) {
		ss.authenticateToken(token);
		if(pc.deleteRequirementFromContract(contractId, requirementId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.notModified("Requirement").build();
		}
	}

	@Override
	public Response deleteSpecialCondition(String token, int contractId, int conditionId) {
		ss.authenticateToken(token);
		if(pc.deleteSpecialConditionFromContract(contractId, conditionId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.notModified("Contract").build();
		}
	}

	@Override
	public Response changeContractState(String token, int contractId, String state) {
		ss.authenticateToken(token);
		if(pc.changeContractState(contractId, ContractState.valueOf(state.toUpperCase()))) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.notModified("Contract").build();
		}
	}

	@Override
	public Response getAllContractsFromUser(String token) {
		ss.authenticateToken(token);
		List<Contract> contracts = pc.findUserContracts(Integer.parseInt(ss.authenticateToken(token)));
		if(contracts!=null) {
			return Response.ok(contracts, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.noContent().build();
		}
	}
	
	@Override
	public Response applyForContract(String token, int contractId) {

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
			return Response.ok(can, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.notModified("Already applied").build();
		}

	}

	@Override
	public Response pickCandidate(String token, int contractId, int candidateId, String acceptance) {

		ss.authenticateToken(token);

		switch (acceptance.toUpperCase()) {
		case "ACCEPT":
			  if(pc.acceptCandidateInContract(new CandidateId(contractId, candidateId))) {
				  return Response.ok("Accepted", MediaType.APPLICATION_JSON).build();
			  }
			  break;
		case "DECLINE":
			  if(pc.declineCandidateInContract(new CandidateId(contractId, candidateId))) {
				  return Response.ok("Declined", MediaType.APPLICATION_JSON).build();
			  }
			  break;
		default:
			return Response.status(500).build();
		}
		
		//Method return 500 status code if the accept or the decline for a candidate fails at persistence level
		return Response.status(500).build();

	}

	
	@Override
	public Response makeOffer(String token, int contractId, int candidateId, String location,
			int radius, String startDate, String endDate, int estimatedWorkload, double fee) {
		
		int token_subject_id = Integer.parseInt(ss.authenticateToken(token));
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		int senderId = 0;
		int receiverId = 0;
		
		BasicCondition b_new = new BasicCondition();
		if (!(endDate.equals(""))) b_new.setEndDate(endDate);
		if (!(startDate.equals(""))) b_new.setStartDate(startDate);
		if (!(location.equals("")))  b_new.setLocation(location);
		b_new.setEstimatedWorkload(estimatedWorkload);
		b_new.setRadius(radius);
		b_new.setFee(fee);
		
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
			
		nc = pc.addOfferToCandidateContract(new CandidateId(contractId, candidateId), b_new, nc);
		
		return Response.ok(nc, MediaType.APPLICATION_JSON).build();
	}

	
	@Override
	public Response cancelNegotiation(String token, int contractId, int candidateId) {
		// TODO Auto-generated method stub
		return Response.ok().build();
	}

	
	@Override
	public Response acceptOffer(String token, int contractId, int candidateId) {
		// TODO Auto-generated method stub
		return Response.ok().build();
	}

}
