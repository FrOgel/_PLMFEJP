package de.mpa.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.CandidateId;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractState;
import de.mpa.domain.ContractType;
import de.mpa.domain.RequirementCriteriaType;
import de.mpa.domain.PlaceOfPerformance;
import de.mpa.domain.ConditionOffer;
import de.mpa.domain.Requirement;
import de.mpa.domain.Task;
import de.mpa.domain.Term;
import de.mpa.domain.TermType;
import de.mpa.domain.UserMatch;
import de.mpa.domain.UserMatchComparator;
import de.mpa.domain.DevelopmentTask;
import de.mpa.domain.TaskSubType;
import de.mpa.domain.TaskType;
import de.mpa.infrastructure.PersistenceContract;

@Stateless
public class ApplicationContractService implements _ApplicationContractService {

	// DI for handling security and persistence related topics
	@EJB
	LocationService ls;
	@EJB
	private PersistenceContract pc;
	@Resource
	private ManagedExecutorService managedExecutorService;

	// Methods for CRUD operations on the basic contract
	@Override
	public Response saveContract(Integer httpRequesterId, String designation, String contractType,
			String contractSubject) {

		if (designation.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No designation").build();

		if (contractType.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No contract type").build();

		if (contractSubject.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No contract subject").build();

		Contract c_new = new Contract();

		c_new.setSubject(contractSubject);
		c_new.setType(ContractType.valueOf(contractType.toUpperCase()));
		c_new.setName(designation);
		c_new.setPrincipalID(httpRequesterId);

		c_new = (Contract) pc.addObjectToPersistance(c_new);

		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteContract(Integer httpRequesterId, Integer contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (pc.deleteContract(contractId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Contract not deleted.").build();
		}
	}

	@Override
	public Response updateContract(Integer httpRequesterId, String designation, String contractType,
			String contractSubject, String contractState, Integer contractId) {

		Contract c_new = new Contract();

		if (contractId != 0) {
			c_new.setContractID(contractId);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		}

		if (!(designation.equals("")))
			c_new.setName(designation);
		if (!(contractType.equals("")))
			c_new.setType(ContractType.valueOf(contractType.toUpperCase()));
		if (!(contractSubject.equals("")))
			c_new.setSubject(contractSubject);

		c_new.setPrincipalID(httpRequesterId);

		c_new = (Contract) pc.updateExistingObject(c_new);

		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();
	}

	// Returns all contracts of a user
	@Override
	public Response getContracts(Integer httpRequesterId) {

		List<Contract> contracts = pc.findUserContracts(httpRequesterId);
		if (contracts != null) {
			int userId = httpRequesterId;
			List<String> contractList = new ArrayList<String>();
			for (Contract c : contracts) {
				contractList.add(this.processJsonViewForContract(c, userId));
			}
			return Response.ok(contractList, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.noContent().build();
		}

	}

	@Override
	public Response getContract(Integer httpRequesterId, Integer contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		String result = this.processJsonViewForContract(c, httpRequesterId);

		if (c != null) {
			return Response.ok(result, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("Not contract found").build();
		}

	}

	public Response getUserContractRelationship(Integer principalId, Integer userId) {
		if (principalId == 0 || userId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Missing id").build();

		List<Contract> contracts = pc.findUserContracts(principalId);

		if (contracts == null)
			return Response.ok("VIEWER", MediaType.TEXT_PLAIN).build();

		for (Contract c : contracts) {
			List<Candidate> candidates = c.getCandidates();

			if (c.getClientID() == userId)
				return Response.ok("CLIENT", MediaType.TEXT_PLAIN).build();

			if (candidates != null) {
				for (Candidate can : candidates) {
					if (can.getCandidateId().getCandidateId() == userId)
						return Response.ok("CANDIDATE", MediaType.TEXT_PLAIN).build();
				}
			}
		}

		return Response.ok("VIEWER", MediaType.TEXT_PLAIN).build();

	}

	@Override
	public Response createContractSearch(Integer httpRequesterId, String searchText, String country, String zipCode,
			String city, int radius) {

		if (searchText == null)
			return Response.status(Status.BAD_REQUEST).entity("No searchString handed").build();

		if (searchText.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No seaarchString").build();

		List<Contract> contractList = pc.searchContract(searchText);
		List<String> searchResult = new ArrayList<String>();

		if (!(country.equals("")) && (!(zipCode.equals(""))) && (!(city.equals("")))) {
			for (Contract c : contractList) {
				PlaceOfPerformance p_old = c.getPlaceOfPerformance();

				if (p_old != null) {
					if (ls.getDistance(p_old.getCountry(), p_old.getZipCode(), p_old.getPlace(), country, zipCode,
							city) <= radius) {
						searchResult.add(this.processJsonViewForContract(c, httpRequesterId));
					}
				}

			}
		}

		return Response.ok(searchResult, MediaType.APPLICATION_JSON).build();
	}

	// Methods for CRUD operations on the place of performance for a contract
	@Override
	public Response createPlaceOfPerformance(Integer httpRequesterId, String country, String place, String zipCode,
			int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		if (c.getPlaceOfPerformance() != null) {
			return this.updatePlaceOfPerformance(httpRequesterId, country, place, zipCode, contractId);
		}

		if (country.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No country").build();

		if (place.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No place").build();

		if (zipCode.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No zip code").build();

		PlaceOfPerformance p_new = new PlaceOfPerformance();
		p_new.setCountry(country);
		p_new.setPlace(place);
		p_new.setZipCode(zipCode);

		String location = ls.getLocationGeometryData(country, place, zipCode);

		p_new.setLatitude(ls.getLatFromJson(location));
		p_new.setLongitude(ls.getLngFromJson(location));

		p_new = pc.persistPoPInContract(p_new, contractId);

		return Response.ok(p_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response updatePlaceOfPerformance(Integer httpRequesterId, String country, String place, String zipCode,
			int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		PlaceOfPerformance p_new = new PlaceOfPerformance();

		if (!(country.equals("")))
			p_new.setCountry(country);

		if (!(place.equals("")))
			p_new.setPlace(place);

		if (!(zipCode.equals("")))
			p_new.setZipCode(zipCode);

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		PlaceOfPerformance p_old = c.getPlaceOfPerformance();
		p_new.setPlaceId(p_old.getPlaceId());

		if ((!(p_new.getCountry().equals(p_old.getCountry()))) || (!(p_new.getPlace().equals(p_old.getPlace())))
				|| (!(p_new.getZipCode().equals(p_old.getZipCode())))) {
			String location = ls.getLocationGeometryData(country, place, zipCode);

			p_new.setLatitude(ls.getLatFromJson(location));
			p_new.setLongitude(ls.getLngFromJson(location));
		} else {
			p_new.setLatitude(p_old.getLatitude());
			p_new.setLongitude(p_old.getLongitude());
		}

		p_new = (PlaceOfPerformance) pc.updateExistingObject(p_new);

		return Response.ok(p_new, MediaType.APPLICATION_JSON).build();

	}

	// Methods for CRUD operations on the tasks for a contract
	@Override
	public Response saveTask(Integer httpRequesterId, String description, String type, String subType, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (description.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No description").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		if (c.getType().equals(ContractType.DEVELOPMENT)) {

			if (type.equals(""))
				return Response.status(Status.BAD_REQUEST).entity("No type").build();

			if (subType.equals(""))
				return Response.status(Status.BAD_REQUEST).entity("No sub type").build();

			DevelopmentTask t_new = new DevelopmentTask();
			t_new.setDescription(description);
			t_new.setType(TaskType.valueOf(type.toUpperCase()));
			t_new.setSubType(TaskSubType.valueOf(subType.toUpperCase()));
			t_new = (DevelopmentTask) pc.persistTaskInContract(c, t_new);
			return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
		} else {
			Task t_new = new Task();
			t_new.setDescription(description);
			t_new = pc.persistTaskInContract(c, t_new);
			return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
		}

	}

	@Override
	public Response deleteTask(Integer httpRequesterId, int contractId, int taskId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (taskId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (pc.deleteTaskFromContracT(contractId, taskId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Task not deleted.").build();
		}
	}

	@Override
	public Response updateTask(Integer httpRequesterId, String description, String type, String subType, int contractId,
			int taskId) {

		if (taskId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No taskId").build();

		Task t = (Task) pc.getObjectFromPersistanceById(Task.class, taskId);

		if (t instanceof DevelopmentTask) {
			DevelopmentTask t_new = new DevelopmentTask();
			t_new.setTaskID(taskId);
			if (!(description.equals("")))
				t_new.setDescription(description);
			if (!(type.equals("")))
				t_new.setType(TaskType.valueOf(type.toUpperCase()));
			if (!(subType.equals("")))
				t_new.setSubType(TaskSubType.valueOf(subType.toUpperCase()));
			t_new = (DevelopmentTask) pc.updateExistingObject(t_new);

			return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
		} else {
			Task t_new = new Task();
			t_new.setTaskID(taskId);
			if (!(description.equals("")))
				t_new.setDescription(description);
			t_new = (Task) pc.updateExistingObject(t_new);

			return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
		}

	}

	@Override
	public Response getTasks(Integer httpRequesterId, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		if (c != null) {
			return Response.ok(c.getTaskDescription(), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.noContent().build();
		}

	}

	// Methods for CRUD operations on the basic conditions for a contract

	@Override
	public Response saveBasicCondition(Integer httpRequesterId, String startDate, String endDate,
			boolean teleWorkPossible, int contractId, int estimatedWorkload, double fee) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (estimatedWorkload == 0)
			return Response.status(Status.BAD_REQUEST).entity("No workload").build();

		if (fee == 0)
			return Response.status(Status.BAD_REQUEST).entity("No fee").build();

		if (endDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No end date").build();

		if (startDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No start date").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		BasicCondition b_new;
		if (c.getBasicConditions() == null) {
			b_new = new BasicCondition();
		} else {
			b_new = c.getBasicConditions();
		}

		b_new.setEndDate(endDate);
		b_new.setStartDate(startDate);
		b_new.setFee(fee);
		b_new.setEstimatedWorkload(estimatedWorkload);
		b_new.setTeleWorkPossible(teleWorkPossible);

		if (c.getBasicConditions() == null) {
			b_new = pc.persistBasicConditionInContract(c, b_new);
		} else {
			b_new = (BasicCondition) pc.updateExistingObject(b_new);
		}

		return Response.ok(b_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response deleteBasicCondition(Integer httpRequesterId, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (pc.deleteBasicCondition(contractId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.notModified("Basic condition").build();
		}
	}

	@Override
	public Response updateBasicCondition(Integer httpRequesterId, String startDate, String endDate,
			boolean teleWorkPossible, int contractId, int basicConditionId, int estimatedWorkload, double fee) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (basicConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No conditionId").build();

		BasicCondition b_new = new BasicCondition();

		if (fee != 0)
			b_new.setFee(fee);

		if (!(endDate.equals("")))
			b_new.setEndDate(endDate);

		if (!(startDate.equals("")))
			b_new.setStartDate(startDate);

		if (estimatedWorkload != 0)
			b_new.setEstimatedWorkload(estimatedWorkload);

		b_new.setTeleWorkPossible(teleWorkPossible);

		b_new = (BasicCondition) pc.updateExistingObject(b_new);

		return Response.ok(b_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response getBasicCondition(Integer httpRequesterId, int contractId, int basicConditionId) {

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
	public List<BasicCondition> getAllBasicConditions(Integer httpRequesterId) {
		return pc.getAllBasicConditions();
	}

	// Methods for CRUD operations on the requirements for a contract

	@Override
	public Response saveRequirement(Integer httpRequesterId, String description, String criteriaType, int contractId) {

		Requirement r_new = new Requirement();

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (!(description.equals(""))) {
			r_new.setDescription(description);
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		}

		if (!(criteriaType.equals(""))) {
			r_new.setCriteriaType(RequirementCriteriaType.valueOf(criteriaType.toUpperCase()));
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No criteria type").build();
		}

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		r_new = pc.persistRequirementInContract(c, r_new);

		return Response.ok(r_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response deleteRequirement(Integer httpRequesterId, int contractId, int requirementId) {

		if (!(pc.checkIfContractIdMatchesRequester(contractId, httpRequesterId)))
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
	public Response updateRequirement(Integer httpRequesterId, String description, String criteriaType, int contractId,
			int requirementId) {

		if (contractId == 0) {
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		}

		if (requirementId == 0) {
			return Response.status(Status.BAD_REQUEST).entity("No requirementId").build();
		}

		Requirement r_new = new Requirement();

		if (!(description.equals("")))
			r_new.setDescription(description);

		if (!(criteriaType.equals("")))
			r_new.setCriteriaType(RequirementCriteriaType.valueOf(criteriaType.toUpperCase()));

		r_new = (Requirement) pc.updateExistingObject(r_new);

		return Response.ok(r_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getRequirements(Integer httpRequesterId, int contractId, int requirementId) {

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

	// Methods for CRUD operations on the terms for a contract

	@Override
	public Response saveTerm(Integer httpRequesterId, String description, String termType, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (description.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		if (termType.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No termType").build();

		Term s_new = new Term();
		s_new.setDescription(description);
		s_new.setTermType(TermType.valueOf(termType.toUpperCase()));

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		s_new = pc.persistTermInContract(c, s_new);

		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteTerm(Integer httpRequesterId, int contractId, int termId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (termId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not conditionId").build();

		if (pc.deleteTermFromContract(contractId, termId)) {
			return Response.ok(true, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.notModified("Contract").build();
		}
	}

	@Override
	public Response updateTerm(Integer httpRequesterId, String description, String termType, int contractId,
			int termId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (termId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not conditionId").build();

		Term s_old = (Term) pc.getObjectFromPersistanceById(Term.class, termId);

		if (s_old == null)
			return Response.status(Status.BAD_REQUEST).entity("Term doesn't exist").build();

		Term s_new = new Term();
		s_new.setTermId(termId);

		if (description.equals("")) {
			s_new.setDescription(s_old.getDescription());
		} else {
			s_new.setDescription(description);
		}

		if (termType.equals("")) {
			s_new.setTermType(s_old.getTermType());
		} else {
			s_new.setTermType(TermType.valueOf(termType.toUpperCase()));
		}

		s_new = (Term) pc.updateExistingObject(s_new);

		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getTerm(Integer httpRequesterId, int contractId, int termId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (termId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No conditionId").build();

		Term sc = (Term) pc.getObjectFromPersistanceById(Term.class, termId);

		if (sc != null) {
			return Response.ok(sc, MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No requirement found").build();
		}
	}

	// Methods for CRUD operations on the candidates (potential clients) for a
	// contract

	@Override
	public Response saveCandidate(Integer httpRequesterId, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		CandidateId candidateId = new CandidateId(contractId, httpRequesterId);

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
	public Response updateCandidate(Integer httpRequesterId, Boolean candidateAccepted, Boolean candidateDeclined,
			int contractId, int candidateId) {

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		if (c.getPrincipalID() != httpRequesterId)
			return Response.status(Status.UNAUTHORIZED).entity("You are not authorized to do this").build();

		if (contractId == 0 || candidateId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contract or candidate Id").build();

		CandidateId candidateID = new CandidateId(contractId, candidateId);
		Candidate c_old = (Candidate) pc.getObjectFromPersistanceById(Candidate.class, candidateID);

		if (c_old == null)
			return Response.status(Status.BAD_REQUEST).entity("Candidate doesn't exist").build();

		if (candidateAccepted == null)
			return Response.status(Status.BAD_REQUEST).entity("No acceptance retrieved").build();

		c_old.setCandidateAccepted(candidateAccepted);

		String candidateMail = ApplicationContractService.getUserMailAddress(candidateId);

		Callable<?> sendMail = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				if (candidateAccepted) {
					String canMailHTML = ApplicationContractService.getCandidateAcceptMail("accepted", c.getName(),
							contractId);
					ApplicationContractService.sendCandidateAcceptMail(candidateMail,
							"You were accepted as a candidate!", canMailHTML);
				} else {
					String canMailHTML = ApplicationContractService.getCandidateAcceptMail("declined", c.getName(),
							contractId);
					ApplicationContractService.sendCandidateAcceptMail(candidateMail,
							"You were declined as a candidate...", canMailHTML);
				}
				return null;
			}

		};

		managedExecutorService.submit(sendMail);

		c_old = (Candidate) pc.updateExistingObject(c_old);

		return Response.ok(c_old, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getCandidate(Integer httpRequesterId, int contractId, int candidateId) {

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

	// Methods for CRUD operations on the offers during the contract negotiations
	@Override
	public Response saveOffer(Integer httpRequesterId, String startDate, String endDate, String comment,
			boolean teleWorkPossible, int contractId, int estimatedWorkload, double fee, Integer candidateId) {

		if (candidateId == null)
			return Response.status(Status.BAD_REQUEST).entity("Missing candidateid").build();

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (fee == 0)
			return Response.status(Status.BAD_REQUEST).entity("No fee").build();

		if (estimatedWorkload == 0)
			return Response.status(Status.BAD_REQUEST).entity("No workload").build();

		if (endDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No end date").build();

		if (startDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No start date").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		ConditionOffer new_offer = new ConditionOffer();
		new_offer.setEndDate(endDate);
		new_offer.setStartDate(startDate);
		new_offer.setFee(fee);
		new_offer.setEstimatedWorkload(estimatedWorkload);
		new_offer.setCommentary(comment);
		new_offer.setTeleWorkPossible(teleWorkPossible);

		int senderId;
		int receiverId;

		if (httpRequesterId == candidateId) {
			senderId = candidateId;
			receiverId = c.getPrincipalID();
		} else {
			senderId = c.getPrincipalID();
			receiverId = candidateId;
		}

		new_offer.setSenderId(senderId);
		new_offer.setReceiverId(receiverId);

		new_offer = pc.addOfferToCandidateContract(new CandidateId(contractId, candidateId), new_offer);

		return Response.ok(new_offer, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response acceptOffer(Integer httpRequesterId, Integer offerId, Integer contractId) {

		if (httpRequesterId == null || contractId == null || offerId == null)
			return Response.status(Status.BAD_REQUEST).entity("Missing parameters requester, contract or candidate id")
					.build();

		ConditionOffer co = (ConditionOffer) pc.getObjectFromPersistanceById(ConditionOffer.class, offerId);

		if (co == null)
			return Response.status(Status.BAD_REQUEST).entity("Offer doesn't exist").build();

		if (co.getReceiverId() != httpRequesterId && co.getSenderId() != httpRequesterId)
			return Response.status(Status.FORBIDDEN).entity("You are not allowed to change this offer!").build();

		if (co.getReceiverId() != httpRequesterId)
			return Response.status(Status.FORBIDDEN)
					.entity("You are not able to accept this offer. It's your partners turn.").build();

		co.setAccepted(true);

		co = (ConditionOffer) pc.updateExistingObject(co);

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		c.setBasicConditions((BasicCondition) co);
		if (co.getReceiverId() == c.getPrincipalID()) {
			c.setClientID(co.getSenderId());
		} else {
			c.setClientID(co.getReceiverId());
		}

		pc.updateExistingObject(c);

		Callable<?> sendMail = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				String canMailHTML = ApplicationContractService.getCandidateAcceptMail("declined", c.getName(),
						contractId);

				for (Candidate can : c.getCandidates()) {
					if (c.getClientID() != can.getCandidateId().getCandidateId()) {
						String candidateMail = ApplicationContractService
								.getUserMailAddress(can.getCandidateId().getCandidateId());
						ApplicationContractService.sendCandidateAcceptMail(candidateMail,
								"Another users offer was accepted for the contract " + c.getSubject()
										+ "by the contractor",
								canMailHTML);
					}

				}

				return null;
			}

		};

		managedExecutorService.submit(sendMail);

		return Response.ok(co, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response getOffers(Integer httpRequesterId, int contractId, int candidateId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (candidateId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No candidateId").build();

		CandidateId candidateID = new CandidateId(contractId, candidateId);

		Candidate c = (Candidate) pc.getObjectFromPersistanceById(Candidate.class, candidateID);

		if (c != null) {
			return Response.ok(c.getNegotiatedConditions(), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(Status.BAD_REQUEST).entity("No requirement found").build();
		}
	}

	// Methods for retrieving enums
	public Response getContractStates() {
		ContractState[] contractTypes = ContractState.values();

		return Response.ok(contractTypes, MediaType.APPLICATION_JSON).build();

	}

	public Response getContractType() {
		ContractType[] contractTypes = ContractType.values();

		return Response.ok(contractTypes, MediaType.APPLICATION_JSON).build();
	}

	public Response getRequirementCriteriaType() {
		RequirementCriteriaType[] contractTypes = RequirementCriteriaType.values();

		return Response.ok(contractTypes, MediaType.APPLICATION_JSON).build();
	}

	public Response getTaskType() {
		TaskType[] contractTypes = TaskType.values();

		return Response.ok(contractTypes, MediaType.APPLICATION_JSON).build();
	}

	public Response getTaskSubType() {
		TaskSubType[] contractTypes = TaskSubType.values();

		return Response.ok(contractTypes, MediaType.APPLICATION_JSON).build();
	}

	public Response getTermType() {
		TermType[] contractTypes = TermType.values();

		return Response.ok(contractTypes, MediaType.APPLICATION_JSON).build();
	}

	// Private json view processing depending on the user - contract relationship
	private String processJsonViewForContract(Contract c, int userId) {
		ObjectMapper mapper = new ObjectMapper();

		Class<?> viewClass = Contract.Viewer.class;

		if (c.getPrincipalID() == userId) {
			viewClass = Contract.PrincipalView.class;
		} else {
			for (Candidate cd : c.getCandidates()) {
				if (cd.getCandidateId().getCandidateId() == userId) {
					viewClass = Contract.CandidateView.class;
				}
			}
		}

		if (c.getClientID() == userId) {
			viewClass = Contract.ContractorView.class;
		}

		String result;
		try {
			result = mapper.writerWithView(viewClass).writeValueAsString(c);
		} catch (JsonProcessingException e) {
			result = "Error in view processing";
			e.printStackTrace();
		}

		System.out.println(viewClass);

		return result;
	}

	// Methods for mail sending
	private static String getCandidateAcceptMail(String accept, String contractName, int contractId) {
		URL url;
		try {
			url = new URL("https://localhost:8443/ContractManagement/CandidateAcceptMail.jsp?accept=" + accept + "&id="
					+ contractId + "&name=" + contractName);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputline;
			StringBuffer html = new StringBuffer();

			while ((inputline = in.readLine()) != null) {
				html.append(inputline);
			}

			in.close();

			return html.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Wrong URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "IO Exception";
		}

	}

	private static String sendCandidateAcceptMail(String mail, String subject, String html) {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client
				.target("https://localhost:8443/MailingService/rest/mailing/candidateAccept/" + mail);

		Form form = new Form();
		form.param("html", html);
		form.param("subject", subject);

		Response response = webTarget.request(MediaType.TEXT_PLAIN)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		return (String) response.readEntity(String.class);
	}

	private String getPrincipalSuggestionMail(List<UserMatch> matches) {
		URL url;
		try {

			StringBuilder urlStringBuilder = new StringBuilder()
					.append("https://localhost:8443/ContractManagement/PrinciaplSuggestionMail.jsp?");

			UserMatch first = matches.get(0);

			if (first == null)
				return "No match";

			int oldContractId = 0;
			int userIterator = 0;
			int contractIterator = 0;
			boolean firstRun = true;

			for (UserMatch m : matches) {
				System.out.println("UserId" + m.getUserId());
				int newContractId = m.getContractId();
				if (newContractId != oldContractId) {
					contractIterator++;
					userIterator = 1;
					oldContractId = m.getContractId();
					if (firstRun) {
						urlStringBuilder.append("contractId" + contractIterator + "=" + m.getContractId());
						firstRun = false;
					} else {
						urlStringBuilder.append("&contractId" + contractIterator + "=" + m.getContractId());
					}
					urlStringBuilder
							.append("&subject" + contractIterator + "=" + m.getContractSubject().replace(" ", "%20"));
				}

				urlStringBuilder.append("&userId" + contractIterator + userIterator + "=" + m.getUserId());

				userIterator++;
			}

			System.out.println(urlStringBuilder);

			String urlString = urlStringBuilder.toString();

			url = new URL(urlString);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputline;
			StringBuffer html = new StringBuffer();

			while ((inputline = in.readLine()) != null) {
				html.append(inputline);
			}

			in.close();

			return html.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Wrong URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Not available";
		}

	}

	private String getClientSuggestionMail(List<UserMatch> matches) {
		URL url;
		try {

			StringBuilder urlStringBuilder = new StringBuilder()
					.append("https://localhost:8443/ContractManagement/ClientSuggestionMail.jsp?");

			UserMatch first = matches.get(0);

			if (first == null)
				return "No match";

			int oldContractId = 0;
			int contractIterator = 0;
			boolean firstRun = true;

			for (UserMatch m : matches) {
				int newContractId = m.getContractId();
				if (newContractId != oldContractId) {
					contractIterator++;
					oldContractId = m.getContractId();
					if (firstRun) {
						urlStringBuilder.append("contractId" + contractIterator + "=" + m.getContractId());
						firstRun = false;
					} else {
						urlStringBuilder.append("&contractId" + contractIterator + "=" + m.getContractId());
					}
					urlStringBuilder
							.append("&subject" + contractIterator + "=" + m.getContractSubject().replace(" ", "%20"));
				}

			}

			String urlString = urlStringBuilder.toString();

			url = new URL(urlString);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputline;
			StringBuffer html = new StringBuffer();

			while ((inputline = in.readLine()) != null) {
				html.append(inputline);
			}

			in.close();

			return html.toString();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Wrong URL";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Not available";
		}

	}

	private static String getUserMailAddress(int userId) {

		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target("https://localhost:8443/IdentityManagement/rest/user")
				.path("getUserMail/" + userId);

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);

		Response response = invocationBuilder.get();

		return (String) response.readEntity(String.class);

	}

	private String sendUserSuggestionMail(String mail, String subject, String html) {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client
				.target("https://localhost:8443/MailingService/rest/mailing/candidateAccept/" + mail);

		Form form = new Form();
		form.param("html", html);
		form.param("subject", subject);

		Response response = webTarget.request(MediaType.TEXT_PLAIN)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		return (String) response.readEntity(String.class);
	}

	@Schedule(hour = "0", minute = "38", second = "15")
	private void processMatches() {
		List<UserMatch> matches = pc.getContractUserMatches();

		if (matches.isEmpty())
			return;

		Map<Integer, List<UserMatch>> groupedMatches = matches.stream()
				.collect(Collectors.groupingBy(UserMatch::getPrincipalId));

		List<List<UserMatch>> principalMatches = new ArrayList<List<UserMatch>>(groupedMatches.values());

		int i = 0;

		for (List<UserMatch> m : principalMatches) {

			String html = this.getPrincipalSuggestionMail(m);
			String mail = getUserMailAddress(m.get(i).getPrincipalId());
			if (mail == null)
				continue;
			if (!(mail.equals("frankvogel2@web.de")))
				continue;
			this.sendUserSuggestionMail(mail, "Your current matches for your active contracts!", html);

			i++;
		}

		groupedMatches = matches.stream().collect(Collectors.groupingBy(UserMatch::getUserId));

		List<List<UserMatch>> clientMatches = new ArrayList<List<UserMatch>>(groupedMatches.values());

		i = 0;

		for (List<UserMatch> m : clientMatches) {

			String html = this.getClientSuggestionMail(m);
			String mail = getUserMailAddress(m.get(i).getUserId());
			if (mail == null)
				continue;
			if (!(mail.equals("mpadhbw@gmail.com")))
				continue;
			this.sendUserSuggestionMail(mail, "Your current contract matches!", html);

			i++;
		}

	}

}
