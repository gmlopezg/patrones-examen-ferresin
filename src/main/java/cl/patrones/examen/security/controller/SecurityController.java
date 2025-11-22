package cl.patrones.examen.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	@GetMapping("/ingresar")
	public String login() {
		return "login";
	}

}
