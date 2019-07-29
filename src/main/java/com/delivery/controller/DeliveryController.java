package com.delivery.controller;

import com.delivery.model.User;
import com.delivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryController {

	@Autowired UserRepository userRepository;

	@GetMapping("/v1/users")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

}
