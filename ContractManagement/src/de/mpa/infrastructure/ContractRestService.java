package de.mpa.infrastructure;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonView;

import de.mpa.application._ApplicationContractService;
import de.mpa.domain.BasicCondition;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractType;
import de.mpa.domain.RequirementCriteriaType;
import de.mpa.domain.TaskSubType;
import de.mpa.domain.TaskType;
import de.mpa.domain.TermType;

@Path("/contract")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
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
	public Response saveContract(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("designation") String designation,
			@FormParam("contractType") String contractType, @FormParam("contractSubject") String contractSubject) {
		return ac.saveContract(httpRequesterId, designation, contractType, contractSubject);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/")
	public Response deleteContract(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") Integer contractId) {
		return ac.deleteContract(httpRequesterId, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}")
	public Response updateContract(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("designation") String designation,
			@FormParam("contractType") String contractType, @FormParam("contractSubject") String contractSubject,
			@FormParam("contractState") String contractState, @PathParam("contractId") Integer contractId) {
		return ac.updateContract(httpRequesterId, designation, contractType, contractSubject, contractState, contractId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts")
	public Response getContracts(@HeaderParam("httpRequesterId") Integer httpRequesterId) {
		return ac.getContracts(httpRequesterId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{id}")
	public Response getContract(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("id") Integer contractId) {
		return ac.getContract(httpRequesterId, contractId);
	}

	@Override
	@GET
	@Path("contracts/{principalId}/relationship/{userId}")
	public Response getUserContractRelationship(@PathParam("principalId") Integer contractId,
			@PathParam("userId") Integer userId) {
		return ac.getUserContractRelationship(contractId, userId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/searches")
	@JsonView(Contract.Viewer.class)
	public Response createContractSearch(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("searchText") String searchText,
			@FormParam("country") String country, @FormParam("zipCode") String zipCode, @FormParam("city") String city,
			@FormParam("radius") int radius) {
		return ac.createContractSearch(httpRequesterId, searchText, country, zipCode, city, radius);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/places")
	public Response createPlaceOfPerformance(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("country") String country,
			@FormParam("place") String place, @FormParam("zipCode") String zipCode,
			@PathParam("contractId") int contractId) {
		return ac.createPlaceOfPerformance(httpRequesterId, country, place, zipCode, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/places")
	public Response updatePlaceOfPerformance(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("country") String country,
			@FormParam("place") String place, @FormParam("zipCode") String zipCode,
			@PathParam("contractId") int contractId) {
		return ac.updatePlaceOfPerformance(httpRequesterId, country, place, zipCode, contractId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks")
	public Response saveTask(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("description") String description,
			@FormParam("taskType") String type, @FormParam("taskSubType") String subType,
			@PathParam("contractId") int contractId) {
		return ac.saveTask(httpRequesterId, description, type, subType, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks/{taskId}")
	public Response updateTask(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("description") String description,
			@FormParam("taskType") String type, @FormParam("taskSubType") String subType,
			@PathParam("contractId") int contractId, @PathParam("taskId") int taskId) {
		return ac.updateTask(httpRequesterId, description, type, subType, contractId, taskId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks/{taskId}")
	public Response deleteTask(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("taskId") int taskId) {
		return ac.deleteTask(httpRequesterId, contractId, taskId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/tasks")
	public Response getTasks(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId) {
		return ac.getTasks(httpRequesterId, contractId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions")
	public Response saveBasicCondition(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate, @FormParam("teleWorkPossible") boolean teleWorkPossible,
			@PathParam("contractId") int contractId, @FormParam("estimatedWorkload") int estimatedWorkload,
			@FormParam("fee") double fee) {

		return ac.saveBasicCondition(httpRequesterId, startDate, endDate, teleWorkPossible, contractId, estimatedWorkload, fee);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions/{basicConditionId}")
	public Response deleteBasicCondition(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId) {
		return ac.deleteBasicCondition(httpRequesterId, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions/{basicConditionId}")
	public Response updateBasicCondition(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate, @FormParam("teleWorkPossible") boolean teleWorkPossible,
			@PathParam("contractId") int contractId, @PathParam("basicConditionId") int basicConditionId,
			@FormParam("estimatedWorkload") int estimatedWorkload, @FormParam("fee") double fee) {
		return ac.updateBasicCondition(httpRequesterId, startDate, endDate, teleWorkPossible, contractId, basicConditionId,
				estimatedWorkload, fee);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/basicconditions/{basicConditionId}")
	public Response getBasicCondition(@HeaderParam("httpRequesterId") Integer httpRequesterId,  int contractId,
			@PathParam("basicConditionId") int basicConditionId) {
		return ac.getBasicCondition(httpRequesterId, contractId, basicConditionId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/basicconditions/all")
	public List<BasicCondition> getAllBasicConditions(Integer httpRequesterId) {
		return ac.getAllBasicConditions(httpRequesterId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements")
	public Response saveRequirement(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("description") String description,
			@FormParam("criteriaType") String criteriaType, @PathParam("contractId") int contractId) {
		return ac.saveRequirement(httpRequesterId, description, criteriaType, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements/{requirementId}")
	public Response deleteRequirement(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("requirementId") int requirementId) {
		return ac.deleteRequirement(httpRequesterId, contractId, requirementId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements/{requirementId}")
	public Response updateRequirement(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("description") String description,
			@FormParam("criteriaType") String criteriaType, @PathParam("contractId") int contractID,
			@PathParam("requirementId") int requirementId) {
		return ac.updateRequirement(httpRequesterId, description, criteriaType, contractID, requirementId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/requirements/{requirementId}")
	public Response getRequirements(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("requirementId") int requirementId) {
		return ac.getRequirements(httpRequesterId, contractId, requirementId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/terms")
	public Response saveTerm(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("description") String description,
			@FormParam("termType") String termType, @PathParam("contractId") int contractId) {
		return ac.saveTerm(httpRequesterId, description, termType, contractId);
	}

	@UserAuthorization
	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/terms/{termId}")
	public Response deleteTerm(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("termId") int termId) {
		return ac.deleteTerm(httpRequesterId, contractId, termId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/terms/{termId}")
	public Response updateTerm(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("description") String description,
			@FormParam("termType") String termType, @PathParam("contractId") int contractId,
			@PathParam("termId") int termId) {
		return ac.updateTerm(httpRequesterId, description, termType, contractId, termId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/terms/{termId}")
	public Response getTerm(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("termId") int termId) {
		return ac.getTerm(httpRequesterId, contractId, termId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidates")
	public Response saveCandidate(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId) {
		return ac.saveCandidate(httpRequesterId, contractId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidates/{candidateId}")
	public Response updateCandidate(@HeaderParam("httpRequesterId") Integer httpRequesterId,
			@FormParam("candidateAccepted") Boolean candidateAccepted,
			@FormParam("candidateDeclined") Boolean candidateDeclined, @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId) {
		return ac.updateCandidate(httpRequesterId, candidateAccepted, candidateDeclined, contractId, candidateId);
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidates/{candidateId}")
	public Response getCandidate(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId) {
		return ac.getCandidate(httpRequesterId, contractId, candidateId);
	}

	@UserAuthorization
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/{candidateId}/offers")
	public Response saveOffer(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate, @FormParam("comment") String comment,
			@FormParam("teleWorkPossible") boolean teleWorkPossible, @PathParam("contractId") int contractId,
			@FormParam("workload") int estimatedWorkload, @FormParam("fee") double fee,
			@PathParam("candidateId") Integer candidateId) {

		return ac.saveOffer(httpRequesterId, startDate, endDate, comment, teleWorkPossible, contractId, estimatedWorkload, fee,
				candidateId);
	}

	@UserAuthorization
	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/offers/{offerId}")
	public Response acceptOffer(@HeaderParam("httpRequesterId") Integer httpRequesterId, @PathParam("offerId") Integer offerId, 
			@PathParam("contractId") Integer contractId) {
		return ac.acceptOffer(httpRequesterId, offerId, contractId);
	}
	
	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/{contractId}/candidate/{candidateId}/offers")
	public Response getOffers(@HeaderParam("httpRequesterId") Integer httpRequesterId,  @PathParam("contractId") int contractId,
			@PathParam("candidateId") int candidateId) {
		return ac.getOffers(httpRequesterId, contractId, candidateId);
	}

	//Get enums
	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/contractstates")
	public Response getContractStates() {
		return ac.getContractStates();
	}
	
	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/contracttypes")
	public Response getContractType() {
		return ac.getContractType();
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/requirements/types")
	public Response getRequirementCriteriaType() {
		return ac.getRequirementCriteriaType();
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/tasks/types")
	public Response getTaskType() {
		return ac.getTaskType();
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/tasks/subtypes")
	public Response getTaskSubType() {
		return ac.getTaskSubType();
	}

	@UserAuthorization
	@Override
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("contracts/terms/types")
	public Response getTermType() {
		return ac.getTermType();
	}

	
}
