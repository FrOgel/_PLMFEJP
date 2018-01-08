package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.mpa.application._ApplicationContractService;
import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.Contract;
import de.mpa.domain.Requirement;
import de.mpa.domain.SpecialCondition;
import de.mpa.domain.Task;

@Path("/contract")
@Produces(MediaType.APPLICATION_JSON)
public class ContractRestService implements _ApplicationContractService {

	// Inject ApplicationContractService
	@EJB
	_ApplicationContractService ac;

	/*
	 * Testring https://localhost:8443/ContractManagement/rest/contract/create
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("createContract")
	public Contract saveContract(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("designation") String designation, @FormParam("contractType") String contractType,
			@FormParam("contractSubject") String contractSubject) {
		System.out.println(contractType);
		return ac.saveContract(token, contractId, designation, contractType, contractSubject);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createTask")
	public Task saveTask(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("taskId") int taskId, @FormParam("description") String description,
			@FormParam("taskType") String type, @FormParam("taskSubType") String subType) {
		return ac.saveTask(token, contractId, taskId, description, type, subType);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createBasicCondition")
	public BasicCondition saveBasicCondition(@CookieParam("token") String token,
			@FormParam("contractId") int contractId, @FormParam("basicConditionId") int basicConditionId,
			@FormParam("location") String location, @FormParam("radius") int radius,
			@FormParam("startDate") String startDate, @FormParam("endDate") String endDate,
			@FormParam("workload") int estimatedWorkload, @FormParam("fee") double fee) {

		return ac.saveBasicCondition(token, contractId, basicConditionId, location, radius, startDate, endDate,
				estimatedWorkload, fee);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createRequirement")
	public Requirement saveRequirement(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("requirementId") int requirementId, @FormParam("description") String description,
			@FormParam("criteriaType") String criteriaType) {
		return ac.saveRequirement(token, contractId, requirementId, description, criteriaType);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createSpecialCondition")
	public SpecialCondition saveSpecialCondition(@CookieParam("token") String token,
			@FormParam("contractId") int contractId, @FormParam("conditionId") int specialConditionId,
			@FormParam("description") String description) {
		return ac.saveSpecialCondition(token, contractId, specialConditionId, description);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteContract")
	public boolean deleteContract(@CookieParam("token") String token, @FormParam("contractId") int contractId) {
		System.out.println(contractId);
		return ac.deleteContract(token, contractId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteTask")
	public boolean deleteTask(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("taskId") int taskId) {
		return ac.deleteTask(token, contractId, taskId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteBasicCondition")
	public boolean deleteBasicCondition(@CookieParam("token") String token, @FormParam("contractId") int contractId) {
		return ac.deleteBasicCondition(token, contractId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteRequirement")
	public boolean deleteRequirement(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("requirementId") int requirementId) {
		return ac.deleteRequirement(token, contractId, requirementId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteSpecialCondition")
	public boolean deleteSpecialCondition(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("conditionId") int conditionId) {
		return ac.deleteSpecialCondition(token, contractId, conditionId);
	}

	@Override
	@POST
	@Path("getUserContracts")
	public List<Contract> getAllContractsFromUser(@CookieParam("token") String token) {
		return ac.getAllContractsFromUser(token);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("changeContractState")
	public boolean changeContractState(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("contractState") String state) {
		return ac.changeContractState(token, contractId, state);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("applyForContract")
	public Candidate applyForContract(@CookieParam("token") String token, @FormParam("contractId") int contractId) {
		return ac.applyForContract(token, contractId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("pickCandidate")
	public boolean pickCandidate(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("candidateId") int candidateId, @FormParam("acceptance") String acceptance) {
		return ac.pickCandidate(token, contractId, candidateId, acceptance);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("makeOffer")
	public boolean makeOffer(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("candidateId") int candidateId, @FormParam("location") String location,
			@FormParam("radius") int radius, @FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate, @FormParam("workload") int estimatedWorkload,
			@FormParam("fee") double fee) {

		return ac.makeOffer(token, contractId, candidateId, location, radius, startDate, endDate, estimatedWorkload, fee);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("cancelNegotiation")
	public boolean cancelNegotiation(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("candidateId") int candidateId) {
		return ac.cancelNegotiation(token, contractId, candidateId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("acceptOffer")
	public boolean acceptOffer(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("candidateId") int candidateId) {
		return ac.acceptOffer(token, contractId, candidateId);
	}

}
