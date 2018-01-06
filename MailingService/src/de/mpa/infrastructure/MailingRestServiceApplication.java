package de.mpa.infrastructure;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Provides the rest service in the web container (replaces the web.xml configuration)
 */
@ApplicationPath("/rest")
public class MailingRestServiceApplication extends Application {

	
	
}
