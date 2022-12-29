package com.example.demo.Security;



import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokanProvider {

	
	@Value("${app-jwt-secret}")
     private String  jwtSecret;
     
    
//	@Value("${app-jwt-expiration-milliseconds}")
//     private long jwtExpirationDate;
//	
	
	//generate the jwt token
	
	public String generateToken(Authentication authentication) {
		
		String username =authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+TimeUnit.MINUTES.toMillis(10));

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
        return token;
		
	}
	
//	private Key key() {
//		return Keys.hmacShaKeyFor(
//  Decoders.BASE64.decode(jwtSecret));
//			
//	}
	
	//get username from jwt tokan
	 public String getusername(String token) {
		 Claims claims=Jwts.parserBuilder()
				 .setSigningKey(jwtSecret.getBytes())
				 .build()
				 .parseClaimsJws(token)
				 .getBody();
		 
		 String username=claims.getSubject();
		return username;
		 
	 }
	
	 //validate jwt tokwn
	 
	 public boolean validationToken( String token) {
		 
		 try {
		 Jwts.parserBuilder()
		 .setSigningKey(jwtSecret.getBytes())
		 .build()
		 .parse(token);
		return true;
		 } catch (MalformedJwtException ex) {
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
	        } catch (ExpiredJwtException ex) {
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
	        } catch (UnsupportedJwtException ex) {
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
	        }
			 
		 
		 
	 }
}
