package de.mpa.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.RollbackException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.tools.packager.IOUtils;

import de.mpa.domain.AccountVerification;
import de.mpa.domain.Address;
import de.mpa.domain.CompanyUser;
import de.mpa.domain.ConditionDesire;
import de.mpa.domain.ContactPerson;
import de.mpa.domain.GeographicalCondition;
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

	//private PersistanceUser pu = new PersistanceUser();
	@EJB private PersistanceUser pu;
	private SecurityService ss = new SecurityService();

	// Persists the company user for registration purposes
	@Override
	public Response createCompanyUser(String mail, String pw, String phoneNumber, String companyName, String country,
			String state, String zipCode, String city, String street, String houseNumber, String firstName,
			String surName, String cpPhone, String mailAddress, String department) {

		pw = ss.getEncryptedKey(pw, ToBeEncrypted.PASSWORD);

		Address uAddress = new Address(country, state, zipCode, city, street, houseNumber);
		ContactPerson cp = new ContactPerson(firstName, surName, cpPhone, mailAddress, department);
		CompanyUser user = new CompanyUser(mail, pw, phoneNumber, companyName, uAddress, cp);

		try {
			user = (CompanyUser) pu.addObjectToPersistance(user);
		} catch (RollbackException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Duplicate entry"))
				return Response.status(Status.CONFLICT).entity("Mail address already in use").build();
			throw e;
		}

		this.createAccountVerification(user.getUserID(), mail);

		return Response.ok(user, MediaType.APPLICATION_JSON).build();

	}

	//updates a company user
	public Response updateCompanyUser(String token, String mail, String phoneNumber, String companyName) {
		
		if(mail==null || phoneNumber==null || companyName==null)
			return Response.status(Status.BAD_REQUEST).entity("Missing parameter").build();
		
		if(mail.equals("") || phoneNumber.equals("") || companyName.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("Empty parameter").build();
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		System.out.println(userId);
		
		CompanyUser user = (CompanyUser) pu.getObjectFromPersistanceById(CompanyUser.class, userId);
		
		user.setCompanyName(companyName);
		user.setPhoneNumber(phoneNumber);
		user.setMailAddress(mail);
		
		user = (CompanyUser) pu.updateExistingObject(user);
		
		return Response.ok(user, MediaType.APPLICATION_JSON).build();

		
	}
	
	// Persists the private user for registration purposes
	@Override
	public Response createPrivateUser(String mail, String pw, String phoneNumber, String country, String state,
			String zipCode, String city, String street, String houseNumber, String firstName, String surName,
			String birthday) {

		pw = ss.getEncryptedKey(pw, ToBeEncrypted.PASSWORD);

		Address uAddress = new Address(country, state, zipCode, city, street, houseNumber);
		PrivateUser user = new PrivateUser(mail, pw, phoneNumber, uAddress, firstName, surName, birthday);

		try {
			user = (PrivateUser) pu.addObjectToPersistance(user);
		} catch (RollbackException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Duplicate entry"))
				return Response.status(Status.CONFLICT).entity("Mail address already in use").build();
			throw e;
		}

		this.createAccountVerification(user.getUserID(), mail);

		return Response.ok(user, MediaType.APPLICATION_JSON).build();
	}

	//Update a private user
	@Override
	public Response updatePrivateUser(String token, String mail, String phoneNumber, String firstName, String surName, String birthday) {
		
		if(mail==null || phoneNumber==null)
			return Response.status(Status.BAD_REQUEST).entity("Empty parameter").build();
		
		if(mail.equals("") || phoneNumber.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("Empty parameter").build();
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		PrivateUser user = (PrivateUser) pu.getObjectFromPersistanceById(PrivateUser.class, userId);
		
		user.setBirthday(birthday);
		user.setFirstName(firstName);
		user.setPhoneNumber(phoneNumber);
		user.setSurName(surName);
		user.setMailAddress(mail);
		
		user = (PrivateUser) pu.updateExistingObject(user);
		
		return Response.ok(user, MediaType.APPLICATION_JSON).build();
		
	}
	
	//updates the address
	public Response updateAddress(String token, String country, String state, String zipCode, String city, String street, String houseNumber) {
		
		if(country==null || state==null || zipCode==null || city==null || street==null || houseNumber==null)
			return Response.status(Status.BAD_REQUEST).entity("Empty parameter").build();
		
		if(country.equals("") || state.equals("") || zipCode.equals("") || city.equals("") || street.equals("") || houseNumber.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("Empty parameter").build();
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		User user = (User) pu.getObjectFromPersistanceById(User.class, userId);
		
		Address userAddress = user.getUserAddress();
		userAddress.setCountry(country);
		userAddress.setState(state);
		userAddress.setZipCode(zipCode);
		userAddress.setCity(city);
		userAddress.setStreet(street);
		userAddress.setHouseNumber(houseNumber);
		
		userAddress = (Address) pu.updateExistingObject(userAddress);
		
		return Response.ok(userAddress, MediaType.APPLICATION_JSON).build();
		
	}
	
	//Updates companys main contact person
	public Response updateMainContactPerson(String token, String firstName, String surName, String cpPhone, String mailAddress, String department) {
		
		if(firstName==null || surName==null || cpPhone==null || mailAddress==null || department==null)
			return Response.status(Status.BAD_REQUEST).entity("Incomplete parameter").build();
		
		if(firstName.equals("") || surName.equals("") || cpPhone.equals("") || mailAddress.equals("") || department.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("Incomplete parameter").build();
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		CompanyUser user = (CompanyUser) pu.getObjectFromPersistanceById(CompanyUser.class, userId);
		
		ContactPerson cp = user.getMainContactPerson();
		cp.setDepartment(department);
		cp.setFirstName(firstName);
		cp.setMailAddress(mailAddress);
		cp.setPhoneNumber(cpPhone);
		cp.setSurName(surName);
		
		cp = (ContactPerson) pu.updateExistingObject(cp);
		
		return Response.ok(cp, MediaType.APPLICATION_JSON).build();
		
	}
	
	//Delete a user
	public Response deleteUser(String token, String pw){
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		User user = (User) pu.getObjectFromPersistanceById(User.class, userId);
		
		if(user.getPassword().equals(ss.getEncryptedKey(pw, ToBeEncrypted.PASSWORD))) {
			
			if(pu.deleteObjectFromPersistance(User.class, userId)) {
				return Response.ok("Deleted").build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity("Not deleted - PW valid").build();
			}

		}
		
		return Response.status(Status.FORBIDDEN).entity("Invalid pw").build();
		
	}
	
	//get a specific user
	public Response getUser(String token, int userId) {
				
		User user = (User) pu.getObjectFromPersistanceById(User.class, userId);
		
		int requesterId = Integer.parseInt(ss.authenticateToken(token));
		
		String userJson = this.processJsonViewForContract(user, requesterId);
		
		return Response.ok(userJson, MediaType.APPLICATION_JSON).build();
		
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
			return Response.status(Status.NOT_MODIFIED).entity("Already verified").build();
		;

		if ((ss.getEncryptedKey(av.getCheckSum(), ToBeEncrypted.VERIFICATION).equals(checkSum))
				&& (Long.parseLong(av.getExpirationDate()) >= Calendar.getInstance().getTimeInMillis())) {
			user = pu.persistVerifiedUser(user, av);
			return Response.ok(user, MediaType.APPLICATION_JSON).build();
		} else {

			return Response.status(Status.FORBIDDEN)
					.entity("Verification timed out. Please request a new verification mail.").build();
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

		// Check if verification already exists
		if (pu.checkIfValidationExists(id))
			pu.removceSecurityValidation(id);

		this.callVerificationMailService(mail, id, ss.getEncryptedKey(uuid, ToBeEncrypted.VERIFICATION));
		pu.addObjectToPersistance(av);
	}

	//Resets the password
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
				return Response.seeOther(new URI("https://localhost:8443/MPA_Frontend/passwordReset.html?id=" + uuid))
						.build();
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

	//calls the change password service
	private void callPasswordChangeMailService(String mail, String hash) {
		try {
			String link = "https://localhost:8443/MailingService/rest/mailing/passwordChangeMail/" + mail + "/" + hash;
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

	//requests password reset
	@Override
	public Response requestPasswordReset(String mail) {
		PasswordChange pc = new PasswordChange();
		int userId = pu.findUserIdByMail(mail);
		System.out.println(userId);
		pc.setUserID(userId);
		String uuid = pc.generateCheckSum();
		pc.setCheckSum(ss.getEncryptedKey(uuid, ToBeEncrypted.PASSWORD_RESET));

		// Check if password reset validation already exists
		if (pu.checkIfValidationExists(userId))
			pu.removceSecurityValidation(userId);

		this.callPasswordChangeMailService(mail, uuid);
		pu.addObjectToPersistance(pc);

		return Response.ok().build();
	}

	//Changes pw for user
	@Override
	public Response changePassword(String uuid, String newPassword) {
		PasswordChange pc = pu.findPasswordChange(ss.getEncryptedKey(uuid, ToBeEncrypted.PASSWORD_RESET));
		System.out.println("Verification in process...");

		if (pc == null) {
			return Response.status(Status.UNAUTHORIZED).entity("No password reset request").build();
		} else {
			User user = (User) pu.getObjectFromPersistanceById(User.class, pc.getUserID());
			pu.changePassword(user, ss.getEncryptedKey(newPassword, ToBeEncrypted.PASSWORD));
			pu.removceSecurityValidation(pc.getUserID());
			return Response.ok("Password successfully changed").build();
		}
	}

	//Gets a mail adress for a user
	@Override
	public Response getUserMailAddress(int userId) {

		String mail = pu.findUserMailById(userId);

		if (mail != null) {
			return Response.ok(mail, MediaType.TEXT_PLAIN).build();
		} else {
			System.out.println(mail);
			return Response.noContent().build();
		}

	}

	//Saves Condition desire
	@Override
	public Response saveConditionDesire(String token, String startDate, String endDate, String contractType, int maxWorkload, double fee,
			String country, String city, String zipCode, int radius) {
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
			
		User user = (User) pu.getObjectFromPersistanceById(User.class, userId);
		
		if(user==null)
			return Response.status(Status.BAD_REQUEST).entity("No user found").build();
		
		ConditionDesire cd = null;
		
		if(user.getCd()==null) {
			cd = new ConditionDesire();
		} else {
			cd = user.getCd();
		}
		
		System.out.println(cd);
		
		if(!(startDate.equals("")))
			cd.setStartDate(startDate);
		if(!(endDate.equals("")))
			cd.setEndDate(endDate);
		if(maxWorkload!=0)
			cd.setMaxWorkload(maxWorkload);
		if(fee!=0)
			cd.setMinFee(fee);
		if(!(contractType.equals(""))) {
			if(contractType.toUpperCase().charAt(0)=='D') {
				cd.setContractType("Development");
			} else {
				cd.setContractType("Consulting");
			}
		}
		
		GeographicalCondition gc;
		
		if(cd.getPlace()==null) {
			gc = new GeographicalCondition();
		} else {
			gc = cd.getPlace();
		}
		
		if(!(country.equals("")))
			gc.setCountry(country);
		if(!(city.equals("")))
			gc.setPlace(city);
		if(!(zipCode.equals("")))
			gc.setZipCode(zipCode);
		
		gc.setRadius(radius);
		
		String location = this.getLocationGeometryData(country, city, zipCode);
		
		gc.setLatitude(this.getLatFromJson(location));
		gc.setLongitude(this.getLngFromJson(location));	
		
		cd.setPlace(gc);
		
		if(user.getCd()==null) {
			user.setCd(cd);
			user = (User) pu.updateExistingObject(user);
			cd = user.getCd();
		} else {
			cd = (ConditionDesire) pu.updateExistingObject(cd);
		}
		
		
		return Response.ok(cd, MediaType.APPLICATION_JSON).build();
	}

	//saves qualification 
	@Override
	public Response saveQualification(String token, String description) {
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		if(description.equals(""))
			return Response.status(Status.BAD_REQUEST).entity("No description").build();
		
		Qualification q_new = new Qualification();
		q_new.setDescription(description);
		
		q_new = pu.persistQualificationInContract(userId, q_new);
		
		return Response.ok(q_new, MediaType.APPLICATION_JSON).build();
	}
	
	//Update qualification
	public Response updateQualification(String token, String description, int qId) {
			
		Qualification q_new = new Qualification();
		
		if(qId!=0)
			q_new.setQualificationId(qId);
		
		if(description!=null)
			q_new.setDescription(description);
		
		pu.updateExistingObject(q_new);
		
		return Response.ok(q_new).build();
	}
	
	//Delete qualification
	public Response deleteQualification(String token, int qualiId) {
		
		System.out.println(qualiId);
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		if(qualiId==0) return Response.status(Status.BAD_REQUEST).entity("No qualification id").build();
		
		pu.deleteQualificationFromUser(userId, qualiId);
		
		return Response.ok().build();
		
	}
	
	//Get qualification
	public Response getQualifications(String token) {
		
		int userId = Integer.parseInt(ss.authenticateToken(token));
		
		User user = (User) pu.getObjectFromPersistanceById(User.class, userId);
		
		List<Qualification> list = (List<Qualification>) user.getQualificationProfile();
		
		return Response.ok(list, MediaType.APPLICATION_JSON).build();
		
	}
	// Methods for retrieving the longitude and latitude values of a specific
	// (country, postal code, city)
	private String getLocationGeometryData(String country, String city, String zipCode) {

		Client client = ClientBuilder.newClient();

		country = country.replace(" ", "%20");
		zipCode = "+" + zipCode.replace(" ", "%20");
		city = "+" + city.replace(" ", "%20");

		WebTarget webTarget = client
				.target("https://nominatim.openstreetmap.org/search?q=" + country + zipCode + city + "&format=json");

		System.out.println("https://nominatim.openstreetmap.org/search?q=" + country + zipCode + city + "&format=json");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);

		Response response = invocationBuilder.get();

		return (String) response.readEntity(String.class);

	}

	//Get latitiude from JSOn
	private double getLatFromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode geo1 = null;
		System.out.println(json);
		try {
			geo1 = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double lat = geo1.get(0).get("lat").asDouble();

		return lat;
	}

	//Get longtitude from json
	private double getLngFromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode geo1 = null;
		System.out.println(json);
		try {
			geo1 = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double lng = geo1.get(0).get("lon").asDouble();

		return lng;
	}

	// Process user based json view for user objects
	private String processJsonViewForContract(User u, int requesterId) {
		ObjectMapper mapper = new ObjectMapper();
		
		Class<?> viewClass = User.ViewerView.class;
		
		if(requesterId == u.getUserID()) {
			viewClass = User.OwnerView.class;
		} else {
			String relationship = this.getUserContractRelationship(requesterId, u.getUserID());

			System.out.println(relationship);

			switch (relationship) {
			case "VIEWER":
				viewClass = User.ViewerView.class;
			case "CANDIDATE":
				viewClass = User.PartnerView.class;
			case "CLIENT":
				viewClass = User.PartnerView.class;
			}
		}
				
		String result;
		try {
			result = mapper.writerWithView(viewClass).writeValueAsString(u);
		} catch (JsonProcessingException e) {
			result = "Error in view processing";
			e.printStackTrace();
		}

		return result;
	}
	
	//gets the user-contract relation
	private String getUserContractRelationship(int contractId, int userId) {
		Client client = ClientBuilder.newClient();

		WebTarget webTarget = client
				.target("https://localhost:8443/ContractManagement/rest/contract/contracts/" + contractId + "/relationship/" + userId);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);

		Response response = invocationBuilder.get();

		return (String) response.readEntity(String.class);
	}

	// Saves the image of the user in the web content
	@Override
	public Response setUserImage( MultipartFormDataInput input, Integer httpRequesterId) {
		if(httpRequesterId == null || input == null) {
			return Response.status(Status.BAD_REQUEST).entity("No content").build();
		}
		
		String fileName = "";

		List<InputPart> inputParts = input.getParts();

		
		for(InputPart inputPart : inputParts) {
			
			try {
				
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
			
			System.out.println(fileName);
			
			String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
			
			fileName = "image_" + httpRequesterId + "." + fileExtension;
			System.out.println(fileName);
			
			pu.saveImage(inputStream, fileName);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return Response.ok("https://localhost:8443/IdentityManagemen/userpictures/" + fileName).build();
		
	}
	
	// Get the file name of the uploaded image
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	
}
