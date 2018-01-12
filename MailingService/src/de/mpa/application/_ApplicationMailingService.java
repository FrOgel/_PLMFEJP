package de.mpa.application;

import javax.ejb.Local;


/**
 * @author 	frank.vogel
 * Date:	06.01.2018
 * Purpose:	Interface for method consistency between the application and the rest endpoint
 */
@Local
public interface _ApplicationMailingService {
	public void sendVerificationMail(String to, String id, String hash);
	
	public void sendPasswordChangeMail(String to, String hash);

	public void sendCandidateAcceptMail(String to, String subject, String html);
	
}
