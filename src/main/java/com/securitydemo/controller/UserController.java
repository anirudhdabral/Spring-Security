package com.securitydemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securitydemo.model.UserEntity;
import com.securitydemo.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {
	@Autowired
	public UserService userService;
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("users")
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("")
	public List<String> getAllUserNames() {
		return userService.getAllUserNames();
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("users/{userName}")
	public UserEntity getUser(@PathVariable String userName) {
		return userService.getUser(userName);
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@PostMapping("users")
	public void addUser(@RequestBody UserEntity user) {
		userService.addUser(user);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("admins")
	public List<UserEntity> getAdminUser() {
		return userService.getAdminUser();
	}
}
