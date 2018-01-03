package de.mpa.infrastructure;

import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registerCompanyUser")
	public CompanyUser registerCompanyUser(@FormParam("mail") String mail, @FormParam("password") String pw, @FormParam("phoneNumber") String phoneNumber, 
			@FormParam("companyName") String companyName, @FormParam("country") String country, @FormParam("state") String state, @FormParam("zipCode") String zipCode, 
			@FormParam("city") String city, @FormParam("street") String street, @FormParam("houseNumber") String houseNumber, @FormParam("firstName") String firstName, 
			@FormParam("surName") String surName, @FormParam("cpPhone") String cpPhone, @FormParam("cpMail") String mailAddress, @FormParam("department") String department) {
		return as.registerCompanyUser(mail, pw, phoneNumber, companyName, country, state, zipCode, city, street, houseNumber, firstName, surName, cpPhone, mailAddress, department);
	}
	
	/*TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/registerPrivateUser/frankvogel2@web.de/testPW/123456/germany/baden-w%C3%BCrttemberg/74172/Neckarsulm/bp1/1/Frank/Vogel/24.08.1993
	 */ 
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("registerPrivateUser")
	public PrivateUser registerPrivateUser(@FormParam("mailAddress") String mail, @FormParam("password") String pw, @FormParam("phoneNumber") String phoneNumber, 
			@FormParam("country") String country, @FormParam("state") String state, @FormParam("zipCode") String zipCode, @FormParam("city") String city, 
			@FormParam("street") String street, @FormParam("houseNumber") String houseNumber,
			@FormParam("firstName") String firstName, @FormParam("surName") String surName, @FormParam("birthday") String birthday) {

			System.out.println(mail);
			System.out.println(country);
			
			return as.registerPrivateUser(mail, pw, phoneNumber, country, state, zipCode, city, street, houseNumber, firstName, surName, birthday);
		
	}
	

	/*TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/login/frankvogel2@web.de/test
	 */
	@Override
	@POST
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login")
	public Response userLogin(@FormParam("mailAddress") String mail, @FormParam("password") String pw) {
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
	
	/*TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/registerPU
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("registerPU")
	public PrivateUser registerPrivateUsser(PrivateUser pu) {
		System.out.println(pu.getFirstName());
		return null;
	}
	
}
