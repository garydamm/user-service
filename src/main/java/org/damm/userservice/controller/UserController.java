package org.damm.userservice.controller;

import org.damm.userservice.pojo.LoginRequest;
import org.damm.userservice.pojo.LoginResponse;
import org.damm.userservice.pojo.User;
import org.damm.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		User user = userService.login(loginRequest);
		return new LoginResponse(user.getName());
	}

	@PostMapping
	public String create(@RequestBody User user) {
		userService.create(user);
		return "ok";
	}

}
