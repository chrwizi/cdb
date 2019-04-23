package cdb.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import cdb.security.service.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private TokenProvider tokenProvider;
	private CustomUserDetailsService userDetailsService;

	public JwtAuthenticationFilter(TokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
		this.tokenProvider = tokenProvider;
		this.userDetailsService = userDetailsService;
	}
	/*
	 * @Override public void doFilter(ServletRequest request, ServletResponse
	 * response, FilterChain chain) throws IOException, ServletException {
	 * 
	 * String token = tokenProvider.resolveToken(request); if (token != null) {
	 * Authentication auth=token!=null ?
	 * tokenProvider.getAuthentication(token):null;
	 * SecurityContextHolder.getContext().setAuthentication(auth); } }
	 */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("\n\n >>>> in internal filter ");
		try {
			String jwt = this.getJwtFromRequest(request);
			System.out.println("\n\n >> token : "+jwt);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				System.out.println("\n\n >>> in if token ");
				Long id = tokenProvider.getUserIdFromJWT(jwt);
				UserDetails userDetails = userDetailsService.loadUserById(id);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			System.out.println("Erreur sur jwtAuthenticationFilter ");
			logger.error("Erreur jwtAuthenticationFilter: " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		System.out.println("\n\n >>in getJwtFromRequest ");
		String token = request.getHeader("Authorization");
		if (StringUtils.hasText(token) && token.startsWith("cdb")) {
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
