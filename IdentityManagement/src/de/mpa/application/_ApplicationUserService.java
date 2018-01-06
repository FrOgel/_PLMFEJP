package de.mpa.application;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import de.mpa.domain.CompanyUser;
import de.mpa.domain.PrivateUser;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Interface for guarantee the method consistency between the application and the rest endpoint
 */
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
	
	public String authenticateViaToken(String token);
	
}
