package pl.pwn.reaktor.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.pwn.reaktor.model.User;
import pl.pwn.reaktor.repository.RoleRepository;
import pl.pwn.reaktor.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	private RoleRepository roleRepository;
	
	
	
	public UserController(UserService userService, RoleRepository roleRepository) {
		super();
		this.userService = userService;
		this.roleRepository = roleRepository;
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable long id, Model model) {
		
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("roles", roleRepository.findAll());
		
		return "users/edit";
	}
	
	@PostMapping("/edit")
	public String edit (@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "users/edit";
		}
		
		userService.editUser(user);
		return "redirect:/users/"+ user.getId();
	}
	
	@GetMapping("/{id}")
	public String view(@PathVariable Long id, Model model) {
		
		model.addAttribute("user", userService.getUserById(id));
		return "users/view";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable long id) {
		
		userService.delete(id);
		return "redirect:/admin/config";
	}
	
	@PostMapping("/changePass")
	public String change(@Valid @ModelAttribute User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			
			return "users/edit";
		}
		userService.changePassword(user);
		return "redirect:/users/" +user.getId();
	}
}
