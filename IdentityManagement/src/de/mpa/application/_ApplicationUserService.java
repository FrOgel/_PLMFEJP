package de.mpa.application;

import java.util.Map;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import de.mpa.domain.CompanyUser;
import de.mpa.domain.PrivateUser;


//This interface builds the contract for the ApplicationUserService and the RestService to ensure the consistency between the external and the internal services
@Local
public interface _ApplicationUserService {
	
	public CompanyUser registerCompanyUser(String mail, String pw, String phoneNumber, String companyName,
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String cpPhone, String mailAddress, String department);
		
	public PrivateUser registerPrivateUser(String mail, String pw, String phoneNumber, 
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String birthday);
	
	public boolean verifyAccount(String id, String creationTime);
	
	public Response userLogin(String mail, String pw);
	
	public boolean authenticateViaToken(String token);
	
	public PrivateUser registerPrivateUsser(PrivateUser map);
}
