package de.mpa.infrastructure;

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

//This rest service provides the functionality for managing user registration and authentication
/**
 * @author frank.vogel created on: 06.01.2018 purpose: Encapsulates the rest
 *         endpoint from the business logic
 */
@Path("/user")
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class UserRestService implements _ApplicationUserService {

	// Injecting the ApplicationUserService to invoke the appropriate methods at the
	// application layer
	@EJB
	private _ApplicationUserService as;

	/*
	 * TestString for registering a company user
	 * https://localhost:8443/IdentityManagement/rest/user/registerCompanyUser/
	 * frankvogel2@web.de/testPW/123456/Bechtle%20AG/germany/baden-w%C3%BCrttemberg/
	 * 74172/Neckarsulm/bp1/1/Frank/Vogel/0172321412/frank.vogel@haha.de/Consulting
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registerCompanyUser")
	public Response createCompanyUser(@FormParam("mailAddress") String mail, @FormParam("password") String pw,
			@FormParam("phoneNumber") String phoneNumber, @FormParam("companyName") String companyName,
			@FormParam("country") String country, @FormParam("state") String state,
			@FormParam("zipCode") String zipCode, @FormParam("city") String city, @FormParam("street") String street,
			@FormParam("houseNumber") String houseNumber, @FormParam("firstName") String firstName,
			@FormParam("surName") String surName, @FormParam("cpPhone") String cpPhone,
			@FormParam("cpMail") String mailAddress, @FormParam("department") String department) {
		return as.createCompanyUser(mail, pw, phoneNumber, companyName, country, state, zipCode, city, street,
				houseNumber, firstName, surName, cpPhone, mailAddress, department);
	}

	/*
	 * TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/registerPrivateUser/
	 * frankvogel2@web.de/testPW/123456/germany/baden-w%C3%BCrttemberg/74172/
	 * Neckarsulm/bp1/1/Frank/Vogel/24.08.1993
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("registerPrivateUser")
	public Response createPrivateUser(@FormParam("mailAddress") String mail, @FormParam("password") String pw,
			@FormParam("phoneNumber") String phoneNumber, @FormParam("country") String country,
			@FormParam("state") String state, @FormParam("zipCode") String zipCode, @FormParam("city") String city,
			@FormParam("street") String street, @FormParam("houseNumber") String houseNumber,
			@FormParam("firstName") String firstName, @FormParam("surName") String surName,
			@FormParam("birthday") String birthday) {

		System.out.println(mail);
		System.out.println(country);

		return as.createPrivateUser(mail, pw, phoneNumber, country, state, zipCode, city, street, houseNumber,
				firstName, surName, birthday);

	}

	/*
	 * TestString for registering of a private user
	 * https://localhost:8443/IdentityManagement/rest/user/login/frankvogel2@web.de/
	 * test
	 */
	@Override
	@POST
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login")
	public Response userLogin(@FormParam("mailAddress") String mail, @FormParam("password") String pw) {
		System.out.println("RS: " + mail);
		return as.userLogin(mail, pw);
	}

	// Token authentication
	@Override
	public Response authenticateViaToken(@CookieParam("token") String token) {
		return as.authenticateViaToken(token);
	}

	// Account verification
	// https://localhost:8443/IdentityManagement/rest/user/verify
	@Override
	@GET
	@Path("verify/{id}/{uuid}")
	public Response verifyAccount(@PathParam("id") int id, @PathParam("uuid") String creationTime) {
		return as.verifyAccount(id, creationTime);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("saveQualification")
	public Response saveQualificaation(@CookieParam("token") String token,
			@FormParam("qualificationId") int qualificationId, @FormParam("designation") String designation) {
		return as.saveQualificaation(token, qualificationId, designation);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("resetPassword")
	public Response requestPasswordReset(@FormParam("mailAddress") String mail) {
		System.out.println(mail);
		return as.requestPasswordReset(mail);
	}

	@Override
	@GET
	@Path("passwordResetAuthentication/{uuid}")
	public Response passwordResetAuthentication(@PathParam("uuid") String uuid) {
		return as.passwordResetAuthentication(uuid);
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("changePassword")
	public Response changePassword(@FormParam("uuid") String uuid, @FormParam("newPassword") String newPassword) {
		return as.changePassword(uuid, newPassword);
	}

	@Override
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("getUserMail/{id}")
	public Response getUserMailAddress(@PathParam("id") int userId) {
		return as.getUserMailAddress(userId);
	}

	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/user/conditiondesire")
	public Response createConditionDesire(@CookieParam("token") String token, @FormParam("startDate") String startDate, 
			@FormParam("endDate") String endDate, @FormParam("maxWorkload") int maxWorkload, @FormParam("fee") double fee,
			@FormParam("country") String country, @FormParam("city") String city, @FormParam("zipCode") String zipCode,
			@FormParam("radius") int radius) {
		return as.createConditionDesire(token, startDate, endDate, maxWorkload, fee, country, city, zipCode, radius);
	}
}
