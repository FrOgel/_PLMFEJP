package de.mpa.application;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Interface for guarantee the method consistency between the application and the rest endpoint
 */
@Local
public interface _ApplicationUserService {
	
	public Response createCompanyUser(String mail, String pw, String phoneNumber, String companyName,
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String cpPhone, String mailAddress, String department);
		
	public Response updateCompanyUser(String token, String mail, String phoneNumber, String companyName);
	
	public Response updateAddress(String token, String country, String state, String zipCode, String city, String street, String houseNumber);
	
	public Response updateMainContactPerson(String token, String firstName, String surName, String cpPhone, String mailAddress, String department);
	
	public Response createPrivateUser(String mail, String pw, String phoneNumber, 
			String country, String state, String zipCode, String city, String street, String houseNumber,
			String firstName, String surName, String birthday);
		
	public Response updatePrivateUser(String token, String mail, String phoneNumber, String firstName, String surName, String birthday);
	
	public Response deleteUser(String token, String pw);
	
	public Response getUser(String token, int userId);
	
	public Response setUserImage( MultipartFormDataInput input, Integer httpRequesterId);
	
	public Response getUserImage(Integer httpRequesterId);
	
	public Response verifyAccount(int id, String uuid);
	
	public Response userLogin(String mail, String pw);
	
	public Response authenticateViaToken(String token);
	
	public Response requestPasswordReset(String mail);

	public Response passwordResetAuthentication(String uuid);

	public Response changePassword(String uuid, String newPassword);

	public Response getUserMailAddress(int userId);
	
	public Response saveConditionDesire(String token, String startDate, String endDate, String contractType, int maxWorkload, double fee,
			String country, String city, String zipCode, int radius);

	public Response saveQualification(String token, String description);
	
	public Response updateQualification(String token, String description, int qId);

	public Response deleteQualification(String token, int qualiId);
	
	public Response getQualifications(String token);
}
