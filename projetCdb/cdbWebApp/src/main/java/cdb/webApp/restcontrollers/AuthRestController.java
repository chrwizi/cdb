package cdb.webApp.restcontrollers;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cdb.core.types.TokenWraper;
import cdb.security.jwt.TokenProvider;
import cdb.service.UserService;
import cdb.webApp.validator.LoginForm;

@RestController
@RequestMapping("api/Auth")
@CrossOrigin
public class AuthRestController {

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private TokenProvider tokenProvider;

	public AuthRestController(UserService userService, TokenProvider tokenProvider,
			AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "*")
	public TokenWraper logIn(@Valid @RequestBody LoginForm loginForm) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwToken = tokenProvider.generateToken(authentication);
		return new TokenWraper(jwToken);
	}

	@PostMapping("/logout")
	@CrossOrigin(origins = "*")
	public void logOut(@RequestBody TokenWraper tokenWraper) {
		
	}

}
