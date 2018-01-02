package de.mpa.application;

import javax.ejb.Local;

//This interface builds the contract for the ApplicationMailingService and the RestService to ensure the consistency between the external and the internal services
@Local
public interface _ApplicationMailingService {
	public void sendVerificationMail(String to, String id, String hash);
}
