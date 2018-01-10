package de.mpa.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.ejb.Stateless;
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

import de.mpa.domain.BasicCondition;
import de.mpa.domain.Candidate;
import de.mpa.domain.CandidateId;
import de.mpa.domain.Contract;
import de.mpa.domain.ContractType;
import de.mpa.domain.CriteriaType;
import de.mpa.domain.ConditionOffer;
import de.mpa.domain.Requirement;
import de.mpa.domain.Term;
import de.mpa.domain.TermType;
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
		c_new.setPrincipalID(Integer.parseInt(ss.authenticateToken(token))); // ==> Performance optimization potential
																				// through handing over the id instead
																				// of the token
		c_new = pc.persistContract(c_new);

		return Response.ok(c_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteContract(String token, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

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

		c_new.setPrincipalID(Integer.parseInt(ss.authenticateToken(token)));

		c_new = (Contract) pc.updateExistingObject(c_new);

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
	public Response getContract(String token, int contractId) {
		
		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		
		if(c!=null) {
			return Response.ok(c, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Status.BAD_REQUEST).entity("Not contract found").build();
		}

	}
	
	@Override
	public Response saveTask(String token, String description, String type, String subType, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (description.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No description").build();

		if (type.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No type").build();

		if (subType.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No sub type").build();

		Task t_new = new Task();
		t_new.setDescription(description);
		t_new.setType(TaskType.valueOf(type.toUpperCase()));
		t_new.setSubType(TaskSubType.valueOf(subType.toUpperCase()));

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		t_new = pc.persistTaskInContract(c, t_new);

		return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response deleteTask(String token, int contractId, int taskId) {

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

		t_new = (Task) pc.updateExistingObject(t_new);

		return Response.ok(t_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getTasks(String token, int contractId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		if (c != null) {
			return Response.ok(c.getTaskDescription(), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.noContent().build();
		}

	}

	@Override
	public Response saveBasicCondition(String token, String location, String startDate, String endDate, int contractId,
			int radius, int estimatedWorkload, double fee) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (radius == 0)
			return Response.status(Status.BAD_REQUEST).entity("No radius").build();

		if (estimatedWorkload == 0)
			return Response.status(Status.BAD_REQUEST).entity("No workload").build();

		if (fee == 0)
			return Response.status(Status.BAD_REQUEST).entity("No fee").build();

		if (endDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No end date").build();

		if (startDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No start date").build();

		if (endDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No location").build();

		BasicCondition b_new = new BasicCondition();
		b_new.setEndDate(endDate);
		b_new.setStartDate(startDate);
		b_new.setRadius(radius);
		b_new.setLocation(location);
		b_new.setFee(fee);
		b_new.setEstimatedWorkload(estimatedWorkload);

		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		b_new = pc.persistBasicConditionInContract(c, b_new);

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

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (basicConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No conditionId").build();

		BasicCondition b_new = new BasicCondition();

		if (!(endDate.equals("")))
			b_new.setEndDate(endDate);

		if (!(startDate.equals("")))
			b_new.setStartDate(startDate);

		if (estimatedWorkload != 0)
			b_new.setEstimatedWorkload(estimatedWorkload);

		if (!(endDate.equals("")))
			b_new.setLocation(location);

		if (radius != 0)
			b_new.setRadius(radius);

		if (fee != 0)
			b_new.setFee(fee);

		b_new = (BasicCondition) pc.updateExistingObject(b_new);

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
			r_new.setCriteriaType(CriteriaType.valueOf(criteriaType.toUpperCase()));

		r_new = (Requirement) pc.updateExistingObject(r_new);

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
	public Response saveTerm(String token, String description, String termType, int contractId) {

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
		s_new = pc.persistSpecialConditionInContract(c, s_new);

		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();

	}

	@Override
	public Response deleteTerm(String token, int contractId, int specialConditionId) {

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
	public Response updateTerm(String token, String description, String termType, int contractId, int specialConditionId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();
		if (specialConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not conditionId").build();

		Term s_old = (Term) pc.getObjectFromPersistanceById(Term.class,
				specialConditionId);

		if (s_old == null)
			return Response.status(Status.BAD_REQUEST).entity("Specialcondition doesn't exist").build();

		Term s_new = new Term();
		s_new.setTermId(specialConditionId);

		if (description.equals("")) {
			s_new.setDescription(s_old.getDescription());
		} else {
			s_new.setDescription(description);
		}
		
		if(termType.equals("")) {
			s_new.setTermType(s_old.getTermType());
		} else {
			s_new.setTermType(TermType.valueOf(termType.toUpperCase()));
		}

		s_new = (Term) pc.updateExistingObject(s_new);

		return Response.ok(s_new, MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response getTerm(String token, int contractId, int specialConditionId) {

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("Not contract id").build();

		if (specialConditionId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No conditionId").build();

		Term sc = (Term) pc.getObjectFromPersistanceById(Term.class,
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
		
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);
		
		String candidateMail = this.getUserMailAddress(candidateId);
		
		
		
		if (candidateAccepted) {
			String canMailHTML = this.getCandidateAcceptMail("accepted", c.getName(), contractId);
			this.sendCandidateAcceptMail(candidateMail, "You were accepted as a candidate!",canMailHTML);
		} else {
			String canMailHTML = this.getCandidateAcceptMail("declined", c.getName(), contractId);
			this.sendCandidateAcceptMail(candidateMail,"You were declined as a candidate...", canMailHTML);
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

		if (contractId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No contractId").build();

		if (candidateId == 0)
			return Response.status(Status.BAD_REQUEST).entity("No candidateId").build();

		if (radius == 0)
			return Response.status(Status.BAD_REQUEST).entity("No radius").build();

		if (fee == 0)
			return Response.status(Status.BAD_REQUEST).entity("No fee").build();

		if (estimatedWorkload == 0)
			return Response.status(Status.BAD_REQUEST).entity("No workload").build();

		if (endDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No end date").build();

		if (startDate.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No start date").build();

		if (location.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No location").build();

		int tokenSubjectId = Integer.parseInt(ss.authenticateToken(token));
		Contract c = (Contract) pc.getObjectFromPersistanceById(Contract.class, contractId);

		BasicCondition b_new = new BasicCondition();
		b_new.setEndDate(endDate);
		b_new.setStartDate(startDate);
		b_new.setRadius(radius);
		b_new.setLocation(location);
		b_new.setFee(fee);
		b_new.setEstimatedWorkload(estimatedWorkload);

		int senderId;
		int receiverId;

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
	public Response getOffers(String token, int contractId, int candidateId) {

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

	private String getCandidateAcceptMail(String accept, String contractName, int contractId) {
		URL url;
		try {
			url = new URL("https://localhost:8443/ContractManagement/CandidateAcceptMail.jsp?accept=" + accept + "&id=" + contractId + "&name=" + contractName);
			System.out.println(url);
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
	
	private String getUserMailAddress(int userId) {

		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target("https://localhost:8443/IdentityManagement/rest/user").path("getUserMail/" + userId);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				
		Response response = invocationBuilder.get();
		
		return (String) response.readEntity(String.class);
		
	}
	
	private String sendCandidateAcceptMail(String mail, String subject, String html) {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client.target("https://localhost:8443/MailingService/rest/mailing/candidateAccept/" + mail);
		
		Form form = new Form();
		form.param("html", html);
		form.param("subject", subject);
				
		Response response = webTarget.request(MediaType.TEXT_PLAIN).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		
		return (String) response.readEntity(String.class);
	}

}
