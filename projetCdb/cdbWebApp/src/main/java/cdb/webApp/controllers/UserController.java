package cdb.webApp.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import cdb.core.models.Role;
import cdb.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/create")
	public String create(Model model) {
		List<Role> roles = userService.findRoles();
		System.out.println("\n\n>>> size roles : " + roles.size());
		model.addAttribute("roles", roles);
		return "createUser";
	}

	@PostMapping("/create")
	public RedirectView postUserForm(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password, String idRole) {
		Long id = Long.parseLong(idRole);
		Role role = userService.findRoleById(id);
		userService.createUser(username, password, role);

		return new RedirectView("/projetCdb");
	}

}
