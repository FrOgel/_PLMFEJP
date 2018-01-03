package de.mpa.infrastructure;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class SecurityService {

public String authenticateToken(String token) {
		
		try {
		Algorithm algorithm = Algorithm.HMAC256("ThisIsOurOwn");
		JWTVerifier verifier = JWT.require(algorithm)
				.withIssuer("mpa")
				.build();
		DecodedJWT jwt = verifier.verify(token);
			return jwt.getSubject();
		} catch(UnsupportedEncodingException exception) {
			return "n/a";
		} catch(JWTVerificationException exception) {
			return "Not verified";
		}
		
	}
	
	
}
