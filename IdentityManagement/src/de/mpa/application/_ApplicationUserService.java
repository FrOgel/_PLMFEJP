package de.mpa.application;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Interface for guarantee the method consistency between the application and the rest endpoint
 */
@Local
public interface _ApplicationUserService {
	
	public Response createPrivateUser(String mail, String pw, String phoneNumber, String companyName,
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String cpPhone, String mailAddress, String department);
		
	public Response createCompanyUser(String mail, String pw, String phoneNumber, 
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String birthday);
	
	public Response verifyAccount(int id, String uuid);
	
	public Response userLogin(String mail, String pw);
	
	public Response authenticateViaToken(String token);
	
	public Response saveQualificaation(String token, int qualificationId, String designation);

	public Response requestPasswordReset(String mail);

	public Response passwordResetAuthentication(String uuid);

	public Response changePassword(String uuid, String newPassword);

	public Response getUserMailAddress(int userId);
}
