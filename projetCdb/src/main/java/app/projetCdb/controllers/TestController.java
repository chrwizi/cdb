package app.projetCdb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	@RequestMapping("dashboard")
	@ResponseBody
	public String get() {
		
		return "Hello world ";
	}

}
