package de.mpa.infrastructure;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


/**
 * @author 		frank.vogel
 * created on: 	06.01.2018
 * purpose:		Provides services for securing the password and generating / validating the verification token (JSON Web Token)
 */
public class SecurityService {
		
	//Hashes the password with a 512 bit sha encryption
	public String getSecurePw(String pw, String salt) {
		String hashedPW = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			try {
				md.update(salt.getBytes("UTF-8"));
				byte[] bytes = md.digest(pw.getBytes("UTF-8"));
				StringBuilder sb = new StringBuilder();
				for(Byte b : bytes) {
						sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
					}
				hashedPW = sb.toString();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashedPW;
	}

	//Returns the jwt authentication token for a specific user
	public String getToken(String id) {
		
		String token = null;
		
		try {
			Algorithm  algorithm = Algorithm.HMAC256("ThisIsOurOwn");
			token = JWT.create()
				.withIssuer("mpa")
				.withSubject(id)
				.sign(algorithm);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return token;
	}

	//Checks if the client sends the correct authentication token
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
