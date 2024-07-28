package com.calculadoralaser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@GetMapping(value={"/",""})
	public String home() {
		return "redirect:productos/listar";
	}

}
