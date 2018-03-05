package pl.pwn.reaktor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.pwn.reaktor.model.User;
import pl.pwn.reaktor.model.UserFilter;
import pl.pwn.reaktor.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private UserService userService;
	
	@Autowired
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/config")
	public String config(Model model, Pageable pageable) {
		
		Page<User> pageUser = userService.getAll(pageable);
		model.addAttribute("users", pageUser);
		PageWrapper<User> page = new PageWrapper<>(pageUser, "/admin/config");
		model.addAttribute("page", page);
		
		model.addAttribute("filter", new UserFilter());
		return "admin/config";
	}
	
	@PostMapping("/config")
	public String filter(@ModelAttribute UserFilter userFilter, Model model, Pageable pageable) {
		
		Page<User> pageUser = userService.getByFilter(userFilter, pageable);
		model.addAttribute("users", pageUser);
		PageWrapper<User> page = new PageWrapper<>(pageUser, "/admin/config");
		model.addAttribute("page", page);
		
		model.addAttribute("filter", userFilter);
		
		return "admin/config";
	}
	

}
