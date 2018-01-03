package de.mpa.infrastructure;

import javax.ejb.EJB;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.mpa.application._ApplicationUserService;
import de.mpa.domain.CompanyUser;
import de.mpa.domain.PrivateUser;


//This rest service provides the functionality for managing user registration and authentication
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserRestService implements _ApplicationUserService{
	
	
	//Injecting the ApplicationUserService to invoke the appropriate methods at the application layer
	@EJB
	private _ApplicationUserService as;

	/*TestString for registering a company user
	 * https://localhost:8443/IdentityManagement/rest/user/registerCompanyUser/frankvogel2@web.de/testPW/123456/Bechtle%20AG/germany/baden-w%C3%BCrttemberg/74172/Neckarsulm/bp1/1/Frank/Vogel/0172321412/frank.vogel@haha.de/Consulting
	 */
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registerCompanyUser/{mail}/{pw}/{phoneNumber}/{companyName}/{country}/{state}/{zipCode}/{city}/{street}/{houseNumber}/{firstName}/{surName}/{cpPhone}/{cpMail}/{department}")
	public CompanyUser registerCompanyUser(@PathParam("mail") String mail, @PathParam("pw") String pw, @PathParam("phoneNumber") String phoneNumber, 
			@PathParam("companyName") String companyName, @PathParam("country") String country, @PathParam("state") String state, @PathParam("zipCode") String zipCode, 
			@PathParam("city") String city, @PathParam("street") String street, @PathParam("houseNumber") String houseNumber, @PathParam("firstName") String firstName, 
			@PathParam("surName") String surName, @PathParam("cpPhone") String cpPhone, @PathParam("cpMail") String mailAddress, @PathParam("department") String department) {
		return as.registerCompanyUser(mail, pw, phoneNumber, companyName, country, state, zipCode, city, street, houseNumber, firstName, surName, cpPhone, mailAddress, department);
	}
	
	/*TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/registerPrivateUser/frankvogel2@web.de/testPW/123456/germany/baden-w%C3%BCrttemberg/74172/Neckarsulm/bp1/1/Frank/Vogel/24.08.1993
	 */ 
	@Override
	@GET
	@Path("/registerPrivateUser/{mail}/{pw}/{phoneNumber}/{country}/{state}/{zipCode}/{city}/{street}/{houseNumber}/{firstName}/{surName}/{birthday}")
	public PrivateUser registerPrivateUser(@PathParam("mail") String mail, @PathParam("pw") String pw, @PathParam("phoneNumber") String phoneNumber, 
			@PathParam("country") String country, @PathParam("state") String state, @PathParam("zipCode") String zipCode, @PathParam("city") String city, 
			@PathParam("street") String street, @PathParam("houseNumber") String houseNumber,
			@PathParam("firstName") String firstName, @PathParam("surName") String surName, @PathParam("birthday") String birthday) {

			return as.registerPrivateUser(mail, pw, phoneNumber, country, state, zipCode, city, street, houseNumber, firstName, surName, birthday);
		
	}

	/*TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/login/frankvogel2@web.de/test
	 */
	@Override
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/login/{mail}/{pw}")
	public Response userLogin(@PathParam("mail") String mail, @PathParam("pw") String pw) {
		System.out.println("RS: " + mail);
		return as.userLogin(mail, pw);
	}

	//Token authentication
	@Override
	public boolean authenticateViaToken(@CookieParam("token") String token) {
		return as.authenticateViaToken(token);
	}

	//Account verification
	//https://localhost:8443/IdentityManagement/rest/user/verify
	@Override
	@GET
	@Path("verify/{id}/{creationTime}")
	public boolean verifyAccount(@PathParam("id") String id, @PathParam("creationTime") String creationTime) {
		return as.verifyAccount(id, creationTime);
	}
	
}
