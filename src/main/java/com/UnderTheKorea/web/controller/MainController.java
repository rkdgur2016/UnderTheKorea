package com.UnderTheKorea.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
 
	@RequestMapping(value = "/{path:[^\\.]*}")
	public String index() {
		return "index.html";
	}
	
}
