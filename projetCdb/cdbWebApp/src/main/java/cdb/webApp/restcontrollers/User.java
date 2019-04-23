package cdb.webApp.restcontrollers;

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
import cdb.security.jwt.TokenProvider;
import cdb.service.UserService;
import cdb.webApp.validator.LoginForm;

@RestController
@RequestMapping("api/users")
@CrossOrigin
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
	public String trest() {
		return "hello ";
	}

/*
	@PostMapping("signup")
	public RedirectView postrForm(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = true) String password, String idRole) {
		Long id = Long.parseLong(idRole);
		
		
		Role role = userService.findRoleById(id);
		userService.createUser(username, password, role);
		

		return new RedirectView("/projetCdb");
	}
	*/
	
	@PostMapping("/auth")
	public String signIn(@RequestBody LoginForm loginForm) {	
		System.out.println("\n\n >> In SignIn EndPoint <<<\n\n");
		System.out.println("username : "+loginForm.getUsername());
		System.out.println("password : "+loginForm.getPassword());
		
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwToken=tokenProvider.generateToken(authentication);
		return jwToken;
	}
	
	
}



