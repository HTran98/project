package com.webAdmin.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webAdmin.entity.UserDetailCustom;

@Controller
@RequestMapping("/")
public class HomeController {
	@GetMapping("login")
	public String login() {
		return "login";
	}
	@GetMapping(value = {"adminHomePage",""})
	public String adminPageHome(@AuthenticationPrincipal UserDetailCustom userInfo, Model model){
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("title", "Home");
		return "index";
	}
	@GetMapping("403")
	public String errorPage() {
		return "403";
	}
}
