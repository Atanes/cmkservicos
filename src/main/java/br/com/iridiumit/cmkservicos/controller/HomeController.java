package br.com.iridiumit.cmkservicos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	

	@RequestMapping(method = RequestMethod.GET, path = "/entrar")
	public String entrar() {
		return "entrar";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/")
	public String inicio() {
		return "inicio";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/acessonegado")
	public String acessonegado() {
		return "acessonegado";
	}
	
	
}
