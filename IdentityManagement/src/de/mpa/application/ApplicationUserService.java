package de.mpa.application;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import de.mpa.domain.AccountVerification;
import de.mpa.domain.Address;
import de.mpa.domain.CompanyUser;
import de.mpa.domain.ContactPerson;
import de.mpa.domain.PrivateUser;
import de.mpa.domain.User;
import de.mpa.infrastructure.PersistanceUser;
import de.mpa.infrastructure.SecurityService;


//Class for abstracting the domain layer from the rest service
//This class will receive the rest service calls and invokes the appropriate domain layer methods
@Stateless
public class ApplicationUserService implements _ApplicationUserService{
	
	private PersistanceUser pu = new PersistanceUser();
	private SecurityService ss = new SecurityService();
		
	//Persists the company user for registration purposes	
	@Override
	public CompanyUser registerCompanyUser(String mail, String pw, String phoneNumber, String companyName,
			String country, String state, String zipCode, String city, String street, String houseNumber, 
			String firstName, String surName, String cpPhone, String mailAddress, String department) {
		
		pw = ss.getSecurePw(pw, "Masse");
		
		Address uAddress = new Address(country, state, zipCode, city, street, houseNumber);
		ContactPerson cp = new ContactPerson(firstName, surName, cpPhone, mailAddress, department);
		CompanyUser user = new CompanyUser(mail, pw, phoneNumber, companyName, uAddress, cp);
		
		System.out.println("User object generated...");
		
		user = (CompanyUser) pu.persistUser(user);
		
		this.createAccountVerification(user.getUserID(), mail);
	
		return user;
			
	}
	
	//Persists the private user for registration purposes
	@Override
	public PrivateUser registerPrivateUser(String mail, String pw, String phoneNumber, 
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String birthday) {
		
		pw = ss.getSecurePw(pw, "Masse");
		
		Address uAddress = new Address(country, state, zipCode, city, street, houseNumber);
		PrivateUser user = new PrivateUser(mail, pw, phoneNumber, uAddress, firstName, surName, birthday);
		
		user = (PrivateUser) pu.persistUser(user);
		
		this.createAccountVerification(user.getUserID(), mail);
		
		return user;
	}

	@Override
	public PrivateUser registerPrivateUsser(PrivateUser pu) {
		
		return null;
	}
	
	/*Handles the mail and password based user authentication
	 *After successful authentication a token for state transfer purposes is returned to the client as a HTTP only cookie to prevent XSS
	 *If Authentication fails because of wrong credentials a HTTP status code 403 is returned
	 *If Authentication fails because of missing account verification a HTTP status code 901 is returned
	 */
	@Override
	public Response userLogin(String mail, String pw) {
		
		System.out.println("Started");
		String id;
		boolean verified;
		
		System.out.println("AS: " + mail);
		
		//Authenticate the user with his credentials. If they are wrong null will be returend
		User user = pu.checkUserCredentials(mail, ss.getSecurePw(pw, "Masse"));
		if(user!=null) {
			id = String.valueOf(user.getUserID());
			verified = user.getVerified();
		}else {
			return Response.status(403).build();
		}
		
		
		//Check if account is verified
		if(!verified) return Response.status(901).build();
		
		//If the authentication was successful a JSON Web Token will be returned to the client
		String token = ss.getToken(id);
		NewCookie c = new NewCookie("token", token);
		System.out.println(c.toString());
		return Response.ok().header("Set-Cookie", c.toString() + ";HttpOnly;secure;domain=localhost;path=/").build();	
	}
	
	//Calls the token authentication method from the security service
	@Override
	public boolean authenticateViaToken(String token) {
		return ss.authenticateToken(token);
	}

	//Checks if the provided URL parameters are valid with the persisted ones
	@Override
	public boolean verifyAccount(String id, String checkSum) {
		User user = pu.getUser(id);
		AccountVerification av = pu.getAccountVerification(id);
		System.out.println("Verification in process...");
		if(user==null) {
			System.out.println("no user"); 
			return false;
		}
		
		if(av==null) {
			System.out.println("no av"); 
			return false;
		}
		
		if(user.getVerified()) return false;
		
		if ((this.encryptUUID(av.getCheckSum()).equals(checkSum)) && (Long.parseLong(av.getExpirationDate())>=Calendar.getInstance().getTimeInMillis())) {
			pu.persistVerifiedUser(user, av);
			return true;
		}else {
			System.out.println("It's false");
			return false;
		}

	}
	
	//Sends the verification mail to the user mail address during the registration process
	private void callVerificationMailService(String mail, int i, String hash) {
		try {
			String link = "https://localhost:8443/MailingService/rest/mailing/verificationMail/" + mail + "/" + i + "/" + hash;
			System.out.println(link);
			URL url = new URL(link);
			HttpsURLConnection  con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.getInputStream();
			con.disconnect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	//Encryption of the creation time of the link parameter for verifying the account
	private String encryptUUID(String time) {
		return ss.getSecurePw(time, "verification");
	}
	
	//Creation of the account verification during the registration process including email
	private void createAccountVerification(int id, String mail) {
		AccountVerification av = new AccountVerification(id);
		String uuid = av.generateCheckSum();
		
		this.callVerificationMailService(mail, id, this.encryptUUID(uuid));
		pu.persistAccountVerification(av);
	}

	
}
