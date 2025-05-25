package com.UnderTheKorea.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/register")
	public String register() {
		
		String ment = "";
		
		return ment;
	}
}
