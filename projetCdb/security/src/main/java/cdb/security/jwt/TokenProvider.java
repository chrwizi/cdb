package cdb.security.jwt;

import java.util.Date;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import cdb.core.models.User;
import io.jsonwebtoken.Claims;
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
		User user=(User)authentication.getPrincipal();
		Date authenticatedDate = new Date();
		Date expiryDate = new Date(authenticatedDate.getTime() + expirationTime);
		String token = Jwts.builder().setSubject(Long.toString(user.getUserID())).setIssuedAt(authenticatedDate)
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, secret).compact();
		return token;
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
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


