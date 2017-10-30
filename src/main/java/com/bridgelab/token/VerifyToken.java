package com.bridgelab.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;

public class VerifyToken {

	String keyvariable="thesecreatKey";
	
	public Boolean parseJWT(String jwt) {
		try{ 
	    //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()         
	       .setSigningKey(keyvariable)
	       .parseClaimsJws(jwt).getBody();
	    System.out.println("ID: " + claims.getId());
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Issuer: " + claims.getIssuer());
	    System.out.println("Expiration: " + claims.getExpiration());
	   
		} catch (MissingClaimException e) {

		    System.out.println("missing claims "+e);
		    return false;

		} catch (IncorrectClaimException e1) {

			 System.out.println("Incorrect claims "+e1);
			 return false;
		}
		return true;
	}
}
