package de.mpa.infrastructure;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.mpa.application._ApplicationContractService;

@Path("/contract")
@Produces(MediaType.APPLICATION_JSON)

public class ContractRestService implements _ApplicationContractService {

	// Inject ApplicationContractService
	@EJB
	_ApplicationContractService ac;

	// @Context
	// UriInfo uriInfo;

	/*
	 * For service registration
	 * 
	 * @WebListener Interface: ServletContextListener
	 * 
	 * public void contextDestroyed(ServletContextEvent arg0) {
	 * 
	 * }
	 * 
	 * @Override public void contextInitialized(ServletContextEvent arg0) { for
	 * (Method method : ContractRestService.class.getDeclaredMethods()) { try {
	 * System.out.println(ContractRestService.registerServices(method.getName(),
	 * "")); }catch(IllegalArgumentException e) { continue; } } }
	 * 
	 * private static String registerServices(String methodName, String baseUri) {
	 * UriBuilder builder = UriBuilder.fromResource(ContractRestService.class); try
	 * { String host = InetAddress.getLocalHost().toString(); host =
	 * host.substring(host.indexOf('/')); builder.host("https:/" + host +
	 * "/ContractManagement/rest") .path(ContractRestService.class, methodName); }
	 * catch (UnknownHostException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return builder.toTemplate(); }
	 */

	/*
	 * Testring https://localhost:8443/ContractManagement/rest/contract/create
	 */

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts")
	public Response saveContract(@CookieParam("token") String token, @FormParam("designation") String designation,
			@FormParam("contractType") String contractType, @FormParam("contractSubject") String contractSubject) {
		return ac.saveContract(token, designation, contractType, contractSubject);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/")
	public Response deleteContract(@CookieParam("token") String token, @PathParam("contractId") int contractId) {
		return ac.deleteContract(token, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}")
	public Response updateContract(@CookieParam("token") String token, @FormParam("designation") String designation,
			@FormParam("contractType") String contractType, @FormParam("contractSubject") String contractSubject,
			@FormParam("contractState") String contractState, @PathParam("contractId") int contractId) {
		return ac.updateContract(token, designation, contractType, contractSubject, contractState, contractId);
	}

	@UserAuthorization
	@Override
	@GET
	@Path("contracts")
	public Response getContracts(@CookieParam("token") String token) {
		return ac.getContracts(token);
	}

	@UserAuthorization
	@Override
	@GET
	@Path("contracts/{id}")
	public Response getContract(@CookieParam("token") String token, @PathParam("id") int contractId) {
		return ac.getContract(token, contractId);
	}
	
	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks")
	public Response saveTask(@CookieParam("token") String token, @FormParam("description") String description,
			@FormParam("taskType") String type, @FormParam("taskSubType") String subType,
			@PathParam("contractId") int contractId) {
		return ac.saveTask(token, description, type, subType, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks/{taskId}")
	public Response updateTask(@CookieParam("token") String token, @FormParam("description") String description,
			@FormParam("taskType") String type, @FormParam("taskSubType") String subType,
			@PathParam("contractId") int contractId, @PathParam("taskId") int taskId) {
		return ac.updateTask(token, description, type, subType, contractId, taskId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks/{taskId}")
	public Response deleteTask(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("taskId") int taskId) {
		return ac.deleteTask(token, contractId, taskId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks")
	public Response getTasks(@CookieParam("token") String token, @PathParam("contractId") int contractId) {
		return ac.getTasks(token, contractId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions")
	public Response saveBasicCondition(@CookieParam("token") String token, @FormParam("location") String location,
			@FormParam("startDate") String startDate, @FormParam("endDate") String endDate,
			@PathParam("contractId") int contractId, @FormParam("radius") int radius,
			@FormParam("estimatedWorkload") int estimatedWorkload, @FormParam("fee") double fee) {

		return ac.saveBasicCondition(token, location, startDate, endDate, contractId, radius, estimatedWorkload,
				fee);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions/{basicConditionId}")
	public Response deleteBasicCondition(@CookieParam("token") String token, @PathParam("contractId") int contractId) {
		return ac.deleteBasicCondition(token, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions/{basicConditionId}")
	public Response updateBasicCondition(@CookieParam("token") String token, @FormParam("location") String location,
			@FormParam("startDate") String startDate, @FormParam("endDate") String endDate,
			@PathParam("contractId") int contractId, @PathParam("basicConditionId") int basicConditionId,
			@FormParam("radius") int radius, @FormParam("estimatedWorkload") int estimatedWorkload,
			@FormParam("fee") double fee) {
		return ac.updateBasicCondition(token, location, startDate, endDate, contractId, basicConditionId, radius,
				estimatedWorkload, fee);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions/{basicConditionId}")
	public Response getBasicCondition(@CookieParam("token") String token, int contractId,
			@PathParam("basicConditionId") int basicConditionId) {
		return ac.getBasicCondition(token, contractId, basicConditionId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements")
	public Response saveRequirement(@CookieParam("token") String token, @FormParam("description") String description,
			@FormParam("criteriaType") String criteriaType, @PathParam("contractId") int contractId) {
		return ac.saveRequirement(token, description, criteriaType, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements/{requirementId}")
	public Response deleteRequirement(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("requirementId") int requirementId) {
		return ac.deleteRequirement(token, contractId, requirementId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements/{requirementId}")
	public Response updateRequirement(@CookieParam("token") String token, @FormParam("description") String description,
			@FormParam("criteriaType") String criteriaType, @PathParam("contractId") int contractID,
			@PathParam("requirementId") int requirementId) {
		return ac.updateRequirement(token, description, criteriaType, contractID, requirementId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements/{requirementId}")
	public Response getRequirements(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("requirementId") int requirementId) {
		return ac.getRequirements(token, contractId, requirementId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/specialconditions")
	public Response saveTerm(@CookieParam("token") String token,
			@FormParam("description") String description, @FormParam("termType") String termType, @PathParam("contractId") int contractId) {
		return ac.saveTerm(token, description, termType, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/specialconditions/{specialConditionId}")
	public Response deleteTerm(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("specialConditionId") int conditionId) {
		return ac.deleteTerm(token, contractId, conditionId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/specialconditions/{specialConditionId}")
	public Response updateTerm(@CookieParam("token") String token,
			@FormParam("description") String description, @FormParam("termType") String termType,
			@PathParam("contractId") int contractId, @PathParam("specialConditionId") int conditionId) {
		return ac.updateTerm(token, description, termType, contractId, conditionId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/specialconditions/{specialConditionId}")
	public Response getTerm(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("specialConditionId") int specialConditionId) {
		return ac.getTerm(token, contractId, specialConditionId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidates")
	public Response saveCandidate(@CookieParam("token") String token, @PathParam("contractId") int contractId) {
		return ac.saveCandidate(token, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidates/{candidateId}")
	public Response updateCandidate(@CookieParam("token") String token,
			@FormParam("candidateAccepted") Boolean candidateAccepted,
			@FormParam("candidateDeclined") Boolean candidateDeclined, @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId) {
		return ac.updateCandidate(token, candidateAccepted, candidateDeclined, contractId, candidateId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidates/{candidateId}")
	public Response getCandidate(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId) {
		return ac.getCandidate(token, contractId, candidateId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/{candidateId}/offers")
	public Response saveOffer(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId, @FormParam("location") String location,
			@FormParam("radius") int radius, @FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate, @FormParam("workload") int estimatedWorkload,
			@FormParam("fee") double fee) {

		return ac.saveOffer(token, contractId, candidateId, location, radius, startDate, endDate, estimatedWorkload,
				fee);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/{candidateId}/offers")
	public Response getOffers(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId) {
		return ac.getOffers(token, contractId, candidateId);
	}

	

}
