package de.mpa.application;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.CandidateId;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.ContractType;
import de.mpa.domain.CriteriaType;
import de.mpa.domain.ConditionOffer;
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
	public Response saveContract(String token, String designation, String contractType, String contractSubject) {

		Contract c_new = new Contract();
		if (!(designation.equals(""))) {
			c_new.setDesignation(designation);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No designation").build();
		}

		if (!(contractType.equals(""))) {
			c_new.setType(ContractType.valueOf(contractType.toUpperCase()));
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No contract type").build();
		}

		if (!(contractSubject.equals(""))) {
			c_new.setSubject(contractSubject);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No contract subject").build();
		}

		c_new.setPrincipalID(Integer.parseInt(ss.authenticateToken(token))); // ==> Performance optimization potential
																				// through handing over the id instead
																				// of the token
		c_new = pc.persistContract(c_new);

		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteContract(String token, int contractId) {
		if (pc.deleteContract(contractId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Contract not deleted.").build();
		}
	}

	@Override
	public Response updateContract(String token, String designation, String contractType, String contractSubject,
			String contractState, int contractId) {

		Contract c_new = new Contract();
		c_new.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));

		if (contractId != 0)
			c_new.setContractID(contractId);
		if (!(designation.equals("")))
			c_new.setDesignation(designation);
		if (!(contractType.equals("")))
			c_new.setType(ContractType.valueOf(contractType.toUpperCase()));
		if (!(contractSubject.equals("")))
			c_new.setSubject(contractSubject);

		c_new = pc.updateContract(c_new);

		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getContracts(String token) {

		List<Contract> contracts = pc.findUserContracts(Integer.parseInt(ss.authenticateToken(token)));
		if (contracts != null) {
			return Response.ok(contracts, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.noContent().build();
		}

	}

	@Override
	public Response saveTask(String token, String description, String type, String subType, int contractId) {

		Task t_new = new Task();

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (!(description.equals(""))) {
			t_new.setDescription(description);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		}
		if (!(type.equals(""))) {
			t_new.setType(TaskType.valueOf(type.toUpperCase()));
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No type").build();
		}

		if (!(subType.equals(""))) {
			t_new.setSubType(TaskSubType.valueOf(subType.toUpperCase()));
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No sub type").build();
		}

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		t_new = pc.persistTaskInContract(c, t_new);

		return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response deleteTask(String token, int contractId, int taskId) {

		if (contractId != 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (taskId != 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (pc.deleteTaskFromContracT(contractId, taskId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Task not deleted.").build();
		}
	}

	@Override
	public Response updateTask(String token, String description, String type, String subType, int contractId,
			int taskId) {

		if (taskId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No taskId").build();

		Task t_new = new Task();
		t_new.setTaskID(taskId);

		if (!(description.equals("")))
			t_new.setDescription(description);
		if (!(type.equals("")))
			t_new.setType(TaskType.valueOf(type.toUpperCase()));
		if (!(subType.equals("")))
			t_new.setSubType(TaskSubType.valueOf(subType.toUpperCase()));

		Task t_old = (Task) pc.getObjectFromPersistanceById(Task.class, taskId);
		t_new = pc.updateTask(t_old, t_new);

		return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getTasks(String token, int contractId) {

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		if (c != null) {
			return Response.ok(c.getTaskDescription(), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.noContent().build();
		}

	}

	@Override
	public Response saveBasicCondition(String token, String location, String startDate, String endDate, int contractId,
			int basicConditionId, int radius, int estimatedWorkload, double fee) {

		BasicCondition b_new = new BasicCondition();
		if (!(endDate.equals(""))) {
			b_new.setEndDate(endDate);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No end date").build();
		}
		if (!(startDate.equals(""))) {
			b_new.setStartDate(startDate);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No start date").build();
		}
		if (estimatedWorkload != 0) {
			b_new.setEstimatedWorkload(estimatedWorkload);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No workload").build();
		}
		if (!(endDate.equals(""))) {
			b_new.setLocation(location);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No location").build();
		}
		if (radius != 0) {
			b_new.setRadius(radius);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No radius").build();
		}
		if (fee != 0) {
			b_new.setFee(fee);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No fee").build();
		}

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
	public Response deleteBasicCondition(String token, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (pc.deleteBasicCondition(contractId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.notModified("Basic condition").build();
		}
	}

	@Override
	public Response updateBasicCondition(String token, String location, String startDate, String endDate,
			int contractId, int basicConditionId, int radius, int estimatedWorkload, double fee) {

		BasicCondition b_new = new BasicCondition();
		if (!(endDate.equals(""))) {
			b_new.setEndDate(endDate);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No end date").build();
		}
		if (!(startDate.equals(""))) {
			b_new.setStartDate(startDate);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No start date").build();
		}
		if (estimatedWorkload != 0) {
			b_new.setEstimatedWorkload(estimatedWorkload);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No workload").build();
		}
		if (!(endDate.equals(""))) {
			b_new.setLocation(location);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No location").build();
		}
		if (radius != 0) {
			b_new.setRadius(radius);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No radius").build();
		}
		if (fee != 0) {
			b_new.setFee(fee);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No fee").build();
		}

		BasicCondition b_old = (BasicCondition) pc.getObjectFromPersistanceById(BasicCondition.class, basicConditionId);
		b_new = pc.updateBasicCondition(b_old, b_new);

		return Response.ok(b_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response getBasicCondition(String token, int contractId, int basicConditionId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (basicConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No condition id").build();

		BasicCondition bc = (BasicCondition) pc.getObjectFromPersistanceById(BasicCondition.class, basicConditionId);

		if (bc != null) {
			return Response.ok(bc, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No condition found").build();
		}

	}

	@Override
	public Response saveRequirement(String token, String description, String criteriaType, int contractId) {

		Requirement r_new = new Requirement();

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (!(description.equals(""))) {
			r_new.setDescription(description);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		}

		if (!(criteriaType.equals(""))) {
			r_new.setCriteriaType(CriteriaType.valueOf(criteriaType.toUpperCase()));
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No criteria type").build();
		}

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		r_new = pc.persistRequirementInContract(c, r_new);

		return Response.ok(r_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response deleteRequirement(String token, int contractId, int requirementId) {

		int requesterId = Integer.parseInt(ss.authenticateToken(token));

		if (!(pc.checkIfContractIdMatchesRequester(contractId, requesterId)))
			return Response.status(Status.FORBIDDEN).entity("You are not allowed to change this resourcce.").build();

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (requirementId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No requirementId").build();

		if (pc.deleteRequirementFromContract(contractId, requirementId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Requirement").build();
		}
	}

	@Override
	public Response updateRequirement(String token, String description, String criteriaType, int contractId,
			int requirementId) {

		Requirement r_new = new Requirement();

		if (contractId == 0) {
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		}

		if (requirementId == 0) {
			return Response.status(Status.BAD_REQUEST).entity("No requirementId").build();
		}

		if (!(description.equals(""))) {
			r_new.setDescription(description);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		}

		if (!(criteriaType.equals(""))) {
			r_new.setCriteriaType(CriteriaType.valueOf(criteriaType.toUpperCase()));
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No criteria type").build();
		}

		System.out.println(requirementId);
		Requirement r_old = (Requirement) pc.getObjectFromPersistanceById(Requirement.class, requirementId);
		r_new = pc.updateRequirement(r_old, r_new);

		return Response.ok(r_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getRequirements(String token, int contractId, int requirementId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (requirementId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No requirement id").build();

		Requirement r = (Requirement) pc.getObjectFromPersistanceById(Requirement.class, requirementId);

		if (r != null) {
			return Response.ok(r, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No requirement found").build();
		}
	}

	@Override
	public Response saveSpecialCondition(String token, String description, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (description.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		SpecialCondition s_new = new SpecialCondition();
		s_new.setDescription(description);

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		s_new = pc.persistSpecialConditionInContract(c, s_new);

		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteSpecialCondition(String token, int contractId, int specialConditionId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (specialConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not conditionId").build();

		if (pc.deleteSpecialConditionFromContract(contractId, specialConditionId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.notModified("Contract").build();
		}
	}

	@Override
	public Response updateSpecialCondition(String token, String description, int contractId, int specialConditionId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (specialConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not conditionId").build();

		SpecialCondition s_old = (SpecialCondition) pc.getObjectFromPersistanceById(SpecialCondition.class,
				specialConditionId);

		if (s_old == null)
			return Response.status(Status.BAD_REQUEST).entity("Specialcondition doesn't exist").build();

		SpecialCondition s_new = new SpecialCondition();
		s_new.setSpecialConditionID(specialConditionId);

		if (description.equals("")) {
			s_new.setDescription(s_old.getDescription());
		} else {
			s_new.setDescription(description);
		}

		s_new = (SpecialCondition) pc.updateExistingObject(s_new);

		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getSpecialCondition(String token, int contractId, int specialConditionId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (specialConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No conditionId").build();

		SpecialCondition sc = (SpecialCondition) pc.getObjectFromPersistanceById(SpecialCondition.class,
				specialConditionId);

		if (sc != null) {
			return Response.ok(sc, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No requirement found").build();
		}
	}

	@Override
	public Response saveCandidate(String token, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		int requesterId = Integer.parseInt(ss.authenticateToken(token));

		CandidateId candidateId = new CandidateId(contractId, requesterId);

		if (pc.doesCandiateAlreadyExistInContract(candidateId)) {
			Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
			Candidate can = new Candidate();
			can.setCandidateId(candidateId);
			can = pc.persistCandidateInContract(c, can);
			return Response.ok(can, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Already applied").build();
		}

	}

	@Override
	public Response updateCandidate(String token, Boolean candidateAccepted, Boolean candidateDeclined, int contractId,
			int candidateId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (candidateId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No candidateId").build();

		CandidateId candidateID = new CandidateId(contractId, candidateId);
		Candidate c_old = (Candidate) pc.getObjectFromPersistanceById(Candidate.class, candidateID);

		if (c_old == null)
			return Response.status(Status.BAD_REQUEST).entity("Candidate doesn't exist").build();

		Candidate c_new = new Candidate();
		c_new.setCandidateId(candidateID);

		if (candidateAccepted == null) {
			if (c_old.isCandidateAccepted() != null) {
				c_new.setCandidateAccepted(c_old.isCandidateAccepted());
			}
		} else {
			c_new.setCandidateAccepted(candidateAccepted);
		}

		c_new = (Candidate) pc.updateExistingObject(c_new);

		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getCandidate(String token, int contractId, int candidateId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (candidateId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No candidateId").build();

		CandidateId candidateID = new CandidateId(contractId, candidateId);
		
		Candidate c = (Candidate) pc.getObjectFromPersistanceById(Candidate.class, candidateID);

		if (c != null) {
			return Response.ok(c, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No requirement found").build();
		}

	}

	@Override
	public Response saveOffer(String token, int contractId, int candidateId, String location, int radius,
			String startDate, String endDate, int estimatedWorkload, double fee) {

		int tokenSubjectId = Integer.parseInt(ss.authenticateToken(token));
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		int senderId = 0;
		int receiverId = 0;

		BasicCondition b_new = new BasicCondition();
		if (!(endDate.equals("")))
			b_new.setEndDate(endDate);
		if (!(startDate.equals("")))
			b_new.setStartDate(startDate);
		if (!(location.equals("")))
			b_new.setLocation(location);
		b_new.setEstimatedWorkload(estimatedWorkload);
		b_new.setRadius(radius);
		b_new.setFee(fee);

		if (tokenSubjectId == candidateId) {
			senderId = candidateId;
			receiverId = c.getPrincipalID();
		} else {
			senderId = c.getPrincipalID();
			receiverId = candidateId;
		}

		ConditionOffer nc = new ConditionOffer();
		nc.setReceiverId(receiverId);
		nc.setSenderId(senderId);

		nc = pc.addOfferToCandidateContract(new CandidateId(contractId, candidateId), b_new, nc);

		return Response.ok(nc, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getOffer(String token, int contractId, int candidateId) {
		// TODO Auto-generated method stub
		return null;
	}

}
