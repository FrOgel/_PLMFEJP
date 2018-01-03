package de.mpa.infrastructure;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//Provides the rest service in the web container (replaces the web.xml configuration)
@ApplicationPath("/rest")
public class ContractRestServiceApplication extends Application{

}
