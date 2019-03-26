package cdb.security.service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import cdb.core.models.User;
import cdb.persistence.dao.UserDao;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private UserDao userDao;
	private int maxTimeInactive = 60; 

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String username = request.getParameter("username");
		Optional<User> connectedUser = userDao.findByUsername(username);
		if (connectedUser.isPresent()) {
			User user = connectedUser.get();
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(maxTimeInactive);
		}
		response.sendRedirect("/");
	}

}
