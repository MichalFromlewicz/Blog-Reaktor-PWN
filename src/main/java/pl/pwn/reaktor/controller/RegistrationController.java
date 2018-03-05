package pl.pwn.reaktor.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.pwn.reaktor.model.User;
import pl.pwn.reaktor.service.UserService;

@Controller
public class RegistrationController {
	
	private UserService userService;
	
	@Autowired
	public RegistrationController (UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/registration")
	public String registration(Model model) {
		
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping("/registration")
	public String save(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "registration";
		}
		
		User savedUser = userService.addUserWithRoleUser(user);
		System.out.println("SavedUser: " + savedUser);
		
		model.addAttribute("succes", "User has been registred successsfully");
		model.addAttribute("user", new User());
		
		return "registration";
	}

}
