package com.bridgelab.token;

import java.util.Date;

import com.bridgelab.model.User;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerator {

	String keyvariable="thesecreatKey";
	 String compactJws=null;
	
	public String createJWT(User user) {
	    //The JWT signature algorithm we will be using to sign the token
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	   long ttlMillis=1000*60;
	    
	    long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis); 
	    //Let's set the JWT Claims
	    compactJws =  Jwts.builder()
	    	    .setId(Integer.toString(user.getId()))
	    	    .setIssuedAt(now)
	    	    .compressWith(CompressionCodecs.DEFLATE)
	    	    .signWith(SignatureAlgorithm.HS512, keyvariable)
	    	    .setExpiration(exp)
	    	    .compact();
	 
	    //if it has been specified, let's add the expiration
	 
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return compactJws;
	}

	@Override
	public String toString() {
		return "TokenGenerator [keyvariable=" + keyvariable + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() +" compactJws "+compactJws +"]";
	}
	
	
	
}
