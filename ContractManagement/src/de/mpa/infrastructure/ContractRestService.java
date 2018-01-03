package de.mpa.infrastructure;

import java.sql.Date;
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
	@Path("create")
	public Contract createContract(@CookieParam("token") String token, @FormParam("designation") String designation) {
		return ac.createContract(token, designation);
	}

	@Override
	public Task createTask(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasicCondition createBasicCondition(String location, String radius, Date startDate, Date endDate,
			int estimatedWorkload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Requirement createRequirement(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpecialCondition createSpecialCondition(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteContract(int contractID) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
