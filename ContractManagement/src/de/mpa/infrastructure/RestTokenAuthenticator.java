package de.mpa.infrastructure;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@UserAuthorization
public class RestTokenAuthenticator implements ContainerRequestFilter{

	private SecurityService ss = new SecurityService();
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String token = null;
		
		for(Cookie c : requestContext.getCookies().values()) {
			if(c.getName().equals("token")) {
				token = c.getValue();
			}
		}
		
		try{
			String userId = ss.authenticateToken(token);
			System.out.println("User with ID " + userId + " authorized."); 
		}catch(Exception e) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}
		
	}

}
