package jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Service
public class TokenProvider {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private String secret = "cdbSecretKey";
	private int expirationTime = 604800000;

	public String generateToken(Authentication authentication) {
		Date authenticatedDate = new Date();
		Date expiryDate = new Date(authenticatedDate.getTime() + expirationTime);
		System.out.println("\n\n>>>  generateToken     <<<<");
		String token= Jwts.builder().setSubject(Long.toString(1)).setIssuedAt(authenticatedDate).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		System.out.println("\n\n >> generated token: "+token);
		return token;
	}

	public boolean validateToken(String authToken) {
		System.out.println("\n\n>>> validateToken  <<<<\\n\\n");
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

	
	
}


