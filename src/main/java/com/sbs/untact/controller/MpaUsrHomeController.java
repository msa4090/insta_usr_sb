package com.sbs.untact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MpaUsrHomeController {
	@RequestMapping("/mpaUsr/home/main")
	public String showMain() {
		return "mpaUsr/home/main";
	}
}
