package de.mpa.infrastructure;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.mpa.application._ApplicationMailingService;

@Path("/mailing")
public class MailingRestService implements _ApplicationMailingService{

	@EJB
	_ApplicationMailingService am;
	
	
	//  https://localhost:8443/MailingService/rest/mailing/verificationMail/frankvogel2@web.de/testlink
	@Override
	@GET
	@Path("/verificationMail/{to}/{id}/{hash}")
	public void sendVerificationMail(@PathParam("to") String to, @PathParam("id") String id, @PathParam("hash") String hash) {
		System.out.println("ok");
		am.sendVerificationMail(to, id, hash);	
	}

}
