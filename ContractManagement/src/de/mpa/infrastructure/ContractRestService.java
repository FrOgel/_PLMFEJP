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

	@Context 
	UriInfo uriInfo;
	
	/*For service registration
	@WebListener
	Interface: ServletContextListener
	 
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		for (Method method : ContractRestService.class.getDeclaredMethods()) {
			try {
			System.out.println(ContractRestService.registerServices(method.getName(), ""));
			}catch(IllegalArgumentException e) {
				continue;
			}
		}
	}
	
	private static String registerServices(String methodName, String baseUri)  {
		UriBuilder builder = UriBuilder.fromResource(ContractRestService.class);
		try {
			String host = InetAddress.getLocalHost().toString();
			host = host.substring(host.indexOf('/'));
			builder.host("https:/" + host + "/ContractManagement/rest")
				   .path(ContractRestService.class, methodName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toTemplate();
	}*/
	
	/*
	 * Testring https://localhost:8443/ContractManagement/rest/contract/create
	 */
	
	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("contracts")
	public Response saveContract(@CookieParam("token") String token, @FormParam("designation") String designation,
			@FormParam("contractType") String contractType, @FormParam("contractSubject") String contractSubject,
			@FormParam("contractId") int contractId) {
		return ac.saveContract(token, designation, contractType, contractSubject, contractId);
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
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks")
	public Response saveTask(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@FormParam("taskId") int taskId, @FormParam("description") String description,
			@FormParam("taskType") String type, @FormParam("taskSubType") String subType) {
		return ac.saveTask(token, contractId, taskId, description, type, subType);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions")
	public Response saveBasicCondition(@CookieParam("token") String token, @FormParam("location") String location,
			@FormParam("startDate") String startDate, @FormParam("endDate") String endDate,
			@PathParam("contractId") int contractId, @FormParam("basicConditionId") int basicConditionId,
			@FormParam("radius") int radius, @FormParam("workload") int estimatedWorkload,
			@FormParam("fee") double fee) {

		return ac.saveBasicCondition(token, location, startDate, endDate, contractId, basicConditionId, radius,
				estimatedWorkload, fee);
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
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/specialconditions")
	public Response saveSpecialCondition(@CookieParam("token") String token, @FormParam("description") String description,
			@PathParam("contractId") int contractId) {
		return ac.saveSpecialCondition(token, description, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/")
	public Response deleteContract(@CookieParam("token") String token, @PathParam("contractId") int contractId) {
		System.out.println(contractId);
		return ac.deleteContract(token, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks")
	public Response deleteTask(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@FormParam("taskId") int taskId) {
		return ac.deleteTask(token, contractId, taskId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions")
	public Response deleteBasicCondition(@CookieParam("token") String token, @PathParam("contractId") int contractId) {
		return ac.deleteBasicCondition(token, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements")
	public Response deleteRequirement(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@FormParam("requirementId") int requirementId) {
		return ac.deleteRequirement(token, contractId, requirementId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/specialconditions")
	public Response deleteSpecialCondition(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@FormParam("conditionId") int conditionId) {
		return ac.deleteSpecialCondition(token, contractId, conditionId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("changeContractState")
	public Response changeContractState(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("contractState") String state) {
		return ac.changeContractState(token, contractId, state);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{@PathParam}/candidates")
	public Response saveCandidate(@CookieParam("token") String token, @FormParam("contractId") int contractId) {
		return ac.saveCandidate(token, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/{candidateId}")
	public Response pickCandidate(@CookieParam("token") String token, @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId, @FormParam("acceptance") String acceptance) {
		return ac.pickCandidate(token, contractId, candidateId, acceptance);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/{candidateId}/offer")
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
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("cancelNegotiation")
	public Response cancelNegotiation(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("candidateId") int candidateId) {
		return ac.cancelNegotiation(token, contractId, candidateId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("acceptOffer")
	public Response acceptOffer(@CookieParam("token") String token, @FormParam("contractId") int contractId,
			@FormParam("candidateId") int candidateId) {
		return ac.acceptOffer(token, contractId, candidateId);
	}

	

}
