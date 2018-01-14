package de.mpa.application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.RollbackException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.mpa.domain.AccountVerification;
import de.mpa.domain.Address;
import de.mpa.domain.CompanyUser;
import de.mpa.domain.ContactPerson;
import de.mpa.domain.PasswordChange;
import de.mpa.domain.PrivateUser;
import de.mpa.domain.Qualification;
import de.mpa.domain.User;
import de.mpa.infrastructure.PersistanceUser;
import de.mpa.infrastructure.SecurityService;
import de.mpa.infrastructure.ToBeEncrypted;

/**
 * @author frank.vogel created on: 06.01.2018 purpose: Class for dealing with
 *         user related logic (login, registration, different models)
 */
@Stateless
public class ApplicationUserService implements _ApplicationUserService {

	private PersistanceUser pu = new PersistanceUser();
	private SecurityService ss = new SecurityService();

	// Persists the company user for registration purposes
	@Override
	public Response createPrivateUser(String mail, String pw, String phoneNumber, String companyName,
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String cpPhone, String mailAddress, String department) {

		pw = ss.getEncryptedKey(pw, ToBeEncrypted.PASSWORD);

		Address uAddress = new Address(country, state, zipCode, city, street, houseNumber);
		ContactPerson cp = new ContactPerson(firstName, surName, cpPhone, mailAddress, department);
		CompanyUser user = new CompanyUser(mail, pw, phoneNumber, companyName, uAddress, cp);

		System.out.println("User object generated...");

		user = (CompanyUser) pu.addObjectToPersistance(user);

		this.createAccountVerification(user.getUserID(), mail);

		return Response.ok(user, MediaType.APPLICATION_JSON).build();

	}

	// Persists the private user for registration purposes
	@Override
	public Response createCompanyUser(String mail, String pw, String phoneNumber, String country, String state,
			String zipCode, String city, String street, String houseNumber, String firstName, String surName,
			String birthday) {

		pw = ss.getEncryptedKey(pw, ToBeEncrypted.PASSWORD);

		Address uAddress = new Address(country, state, zipCode, city, street, houseNumber);
		PrivateUser user = new PrivateUser(mail, pw, phoneNumber, uAddress, firstName, surName, birthday);

		try {
			user = (PrivateUser) pu.addObjectToPersistance(user);
		} catch (RollbackException e) {
			e.printStackTrace();
			if(e.getMessage().contains("Duplicate entry"))
				return Response.status(Status.CONFLICT).entity("Mail address already in use").build();
			throw e;
		}

		this.createAccountVerification(user.getUserID(), mail);

		return Response.ok(user, MediaType.APPLICATION_JSON).build();
	}

	/*
	 * Handles the mail and password based user authentication After successful
	 * authentication a token for state transfer purposes is returned to the client
	 * as a HTTP only cookie to prevent XSS If Authentication fails because of wrong
	 * credentials a HTTP status code 403 is returned If Authentication fails
	 * because of missing account verification a HTTP status code 901 is returned
	 */
	@Override
	public Response userLogin(String mail, String pw) {

		System.out.println("Started");
		int id;
		boolean verified;

		System.out.println("AS: " + mail);

		// Authenticate the user with his credentials. If they are wrong null will be
		// returend
		User user = pu.checkUserCredentials(mail, ss.getEncryptedKey(pw, ToBeEncrypted.PASSWORD));
		if (user != null) {
			id = user.getUserID();
			verified = user.getVerified();
		} else {
			return Response.status(403).build();
		}

		// Check if account is verified
		if (!verified)
			return Response.status(901).build();

		// If the authentication was successful a JSON Web Token will be returned to the
		// client
		String token = ss.getToken(id);
		NewCookie c = new NewCookie("token", token);
		System.out.println(c.toString());
		return Response.ok().header("Set-Cookie", c.toString() + ";HttpOnly;secure;domain=localhost;path=/").build();
	}

	// Calls the token authentication method from the security service
	@Override
	public Response authenticateViaToken(String token) {
		ss.authenticateToken(token);
		return Response.ok("Authenticated").build();
	}

	// Checks if the provided URL parameters are valid with the persisted ones
	@Override
	public Response verifyAccount(int id, String checkSum) {
		User user = (User) pu.getObjectFromPersistanceById(User.class, id);
		AccountVerification av = (AccountVerification) pu.getObjectFromPersistanceById(AccountVerification.class, id);
		System.out.println("Verification in process...");
		if (user == null) {
			return Response.status(Status.NOT_FOUND).entity("No user found").build();
		}

		if (av == null) {
			return Response.status(Status.NOT_FOUND).entity("No verification found").build();
		}

		if (user.getVerified())
			return Response.status(Status.NOT_MODIFIED).entity("Already verified").build();;

		if ((ss.getEncryptedKey(av.getCheckSum(), ToBeEncrypted.VERIFICATION).equals(checkSum))
				&& (Long.parseLong(av.getExpirationDate()) >= Calendar.getInstance().getTimeInMillis())) {
			user = pu.persistVerifiedUser(user, av);
			return Response.ok(user, MediaType.APPLICATION_JSON).build();
		} else {
			
			return Response.status(Status.FORBIDDEN).entity("Verification timed out. Please request a new verification mail.").build();
		}
	}

	// Sends the verification mail to the user mail address during the registration
	// process
	private void callVerificationMailService(String mail, int i, String hash) {
		try {
			String link = "https://localhost:8443/MailingService/rest/mailing/verificationMail/" + mail + "/" + i + "/"
					+ hash;
			System.out.println(link);
			URL url = new URL(link);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.getInputStream();
			con.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// Creation of the account verification during the registration process
	// including email
	private void createAccountVerification(int id, String mail) {
		AccountVerification av = new AccountVerification(id);
		String uuid = av.generateCheckSum();

		//Check if verification already exists
		if(pu.checkIfValidationExists(id)) pu.removceSecurityValidation(id);
		
		this.callVerificationMailService(mail, id, ss.getEncryptedKey(uuid, ToBeEncrypted.VERIFICATION));
		pu.addObjectToPersistance(av);
	}

	@Override
	public Response passwordResetAuthentication(String uuid) {
		PasswordChange pc = pu.findPasswordChange(ss.getEncryptedKey(uuid, ToBeEncrypted.PASSWORD_RESET));
		System.out.println("Verification in process...");

		if (pc == null) {
			System.out.println("no pc");
			return Response.status(403).build();
		}

		if ((Long.parseLong(pc.getExpirationDate()) >= Calendar.getInstance().getTimeInMillis())) {
			try {
				return Response.seeOther(new URI("https://localhost:8443/MPA_Frontend/passwordReset.html?id=" + uuid)).build();
			} catch (URISyntaxException e) {
				e.printStackTrace();
				return Response.status(500).build();
			}

		} else {
			System.out.println("It's false");
			pu.removceSecurityValidation(pc.getUserID());
			return Response.status(403).entity("Link expired").build();
		}
	}
	
	private void callPasswordChangeMailService(String mail, String hash) {
		try {
			String link = "https://localhost:8443/MailingService/rest/mailing/passwordChangeMail/" + mail +"/" + hash;
			System.out.println(link);
			URL url = new URL(link);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.getInputStream();
			con.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public Response requestPasswordReset(String mail) {
		PasswordChange pc = new PasswordChange();
		int userId = pu.findUserIdByMail(mail);
		System.out.println(userId);
		pc.setUserID(userId);
		String uuid = pc.generateCheckSum();
		pc.setCheckSum(ss.getEncryptedKey(uuid, ToBeEncrypted.PASSWORD_RESET));
		
		//Check if password reset validation already exists
		if(pu.checkIfValidationExists(userId)) pu.removceSecurityValidation(userId);
		
		this.callPasswordChangeMailService(mail, uuid);
		pu.addObjectToPersistance(pc);

		return Response.ok().build();
	}

	@Override
	public Response changePassword(String uuid, String newPassword) {
		PasswordChange pc = pu.findPasswordChange(ss.getEncryptedKey(uuid, ToBeEncrypted.PASSWORD_RESET));
		System.out.println("Verification in process...");

		if (pc == null) {
			return Response.status(Status.UNAUTHORIZED).entity("No password reset request").build();
		}else {
			User user = (User) pu.getObjectFromPersistanceById(User.class, pc.getUserID());
			pu.changePassword(user, ss.getEncryptedKey(newPassword, ToBeEncrypted.PASSWORD));
			pu.removceSecurityValidation(pc.getUserID());
			return Response.ok("Password successfully changed").build();
		}
	}

	@Override
	public Response saveQualificaation(String token, int qualificationId, String designation) {

		Qualification q_new = new Qualification();
		q_new.setDescription(designation);

		if (qualificationId != 0) {
			Qualification q_old = (Qualification) pu.getObjectFromPersistanceById(Qualification.class, qualificationId);
			q_new = pu.updateQualification(q_old, q_new);
			
		} else {
			q_new = (Qualification) pu.addObjectToPersistance(q_new);
		}
		
		return Response.ok(q_new, MediaType.APPLICATION_JSON).build();
	}

	
	@Override
	public Response getUserMailAddress(int userId) {
		
		String mail = pu.findUserMailById(userId);
		
		if(mail!=null) {
			return Response.ok(mail, MediaType.TEXT_PLAIN).build();
		}else{
			System.out.println(mail);
			return Response.noContent().build();
		}

	}
}
