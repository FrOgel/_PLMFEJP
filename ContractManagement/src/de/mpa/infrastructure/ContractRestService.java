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
	
	/* Testring
	 * https://localhost:8443/ContractManagement/rest/contract/create
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("createContract")
	public Contract createContract(@CookieParam("token") String token, @FormParam("designation") String designation, 
			@FormParam("contractType") String contractType, @FormParam("contractSubject") String contractSubject) {
		return ac.createContract(token, designation, contractType, contractSubject);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createTask")
	public Task createTask(@CookieParam("token") String token, @FormParam("contractId") int contractId, 
			@FormParam("description") String description) {
		return ac.createTask(token, contractId, description);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createBasicCondition")
	public BasicCondition createBasicCondition(@CookieParam("token") String token, @FormParam("contractId") int contractId, 
			@FormParam("location") String location, @FormParam("radius") String radius, String startDate,
			String endDate, int estimatedWorkload) {
		return ac.createBasicCondition(token, contractId, location, radius, startDate, endDate, estimatedWorkload);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createRequirement")
	public Requirement createRequirement(@CookieParam("token") String token, @FormParam("description") String description, 
			@FormParam("contractId") int contractId) {
		return ac.createRequirement(token, description, contractId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("createSpecialCondition")
	public SpecialCondition createSpecialCondition(@CookieParam("token") String token, @FormParam("description") String description, 
			@FormParam("contractId") int contractId) {
		return ac.createSpecialCondition(token, description, contractId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteContract")
	public boolean deleteContract(@CookieParam("token") String token, @FormParam("contractId") int contractId) {
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
	public boolean deleteRequirement(@CookieParam("token") String token, int contractId, @FormParam("requirementId") int requirementId) {
		return ac.deleteRequirement(token, contractId, requirementId);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("deleteSpecialCondition")
	public boolean deleteSpecialCondition(String token, int contractId, int conditionId) {
		return ac.deleteSpecialCondition(token, contractId, conditionId);
	}

	
}
