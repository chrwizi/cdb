package cdb.webApp.restcontrollers;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import cdb.core.models.Role;
import cdb.core.types.EnumRole;
import cdb.core.types.TokenWraper;
import cdb.security.jwt.TokenProvider;
import cdb.service.UserService;
import cdb.webApp.validator.LoginForm;
import cdb.webApp.validator.SignUpForm;

@RestController
@RequestMapping("api/users")

public class User {
	
	private UserService userService;
	private AuthenticationManager authenticationManager;
	private TokenProvider tokenProvider;
	
	public User(UserService userService,TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.tokenProvider=tokenProvider;
	}
	

	@PostMapping
	@CrossOrigin(origins = "*")
	public void signIn(@Valid @RequestBody SignUpForm signUpForm) {	
		userService.createUser(signUpForm.getUsername(), signUpForm.getPassword(), new Role(EnumRole.Premium));
	}
	
	
}



