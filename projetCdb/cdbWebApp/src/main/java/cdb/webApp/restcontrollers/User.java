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
import cdb.core.types.TokenWraper;
import cdb.security.jwt.TokenProvider;
import cdb.service.UserService;
import cdb.webApp.validator.LoginForm;

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
	
	
	@GetMapping
	@CrossOrigin
	public String trest() {
		return "hello ";
	}
	

	@PostMapping("/auth")
	@CrossOrigin(origins = "*")
	public TokenWraper signIn(@Valid @RequestBody LoginForm loginForm) {	
		System.out.println("\n\n >> In SignIn EndPoint <<<\n\n");
		System.out.println("username : "+loginForm.getUsername());
		System.out.println("password : "+loginForm.getPassword());
		
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwToken=tokenProvider.generateToken(authentication);
		return new TokenWraper(jwToken);
	}
	
	
}



