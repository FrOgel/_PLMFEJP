package de.mpa.infrastructure;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;


@UserAuthorization
@Priority(Priorities.AUTHENTICATION)
public class RestTokenAuthenticator implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
