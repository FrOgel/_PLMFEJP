package de.mpa.infrastructure;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
@UserAuthorization
public class RestTokenAuthenticator implements ContainerRequestFilter {

	private SecurityService ss = new SecurityService();

	@Context
	ResourceInfo resourceInfo;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String token = null;
		String requesterId = "";

		for (Cookie c : requestContext.getCookies().values()) {
			if (c.getName().equals("token")) {
				token = c.getValue();
			}
		}

		try {
			requesterId = ss.authenticateToken(token);
			requestContext.getHeaders().add("httpRequesterId", requesterId);
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}
		
		/*MultivaluedMap<String, String> pathparam = requestContext.getUriInfo().getPathParameters();

		List<String> contractIds = pathparam.get("contractId");
		if (contractIds != null) {

			String contractId = contractIds.get(0);
			if(contractId.equals(""))
				requestContext.abortWith(Response.status(Status.BAD_REQUEST).entity("Contract id expected. None found.").build());
		

			if (!(pc.checkIfContractIdMatchesRequester(Integer.parseInt(contractId), requesterId)))
				requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
		}*/

		System.out.println("User with id " + requesterId + " authorized.");

	}

}
