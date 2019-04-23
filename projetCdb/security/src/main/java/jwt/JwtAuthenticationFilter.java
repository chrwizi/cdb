package jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cdb.security.service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private TokenProvider tokenProvider;
	private CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(TokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
		this.tokenProvider = tokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = this.getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

			}
		} catch (Exception e) {
			logger.error("Erreur jwtAuthenticationFilter: " + e.getMessage());
		}

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7, token.length());
		}
		return null;
	}

	/*
	 * @Override public Authentication attemptAuthentication(HttpServletRequest
	 * request, HttpServletResponse response) throws AuthenticationException {
	 * 
	 * String username=request.getParameter("userName"); String
	 * password=request.getParameter("password");
	 * System.out.println("attemptAuthentication pass : "+password);
	 * 
	 * return authenticationManager.authenticate(new
	 * UsernamePasswordAuthenticationToken(username, password)); }
	 * 
	 * @Override protected void successfulAuthentication(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain chain, Authentication authResult)
	 * throws IOException, ServletException {
	 * System.out.println("\n\n>>> successfulAuthentication <<<\n\n");
	 * super.successfulAuthentication(request, response, chain, authResult); }
	 * 
	 */

}
