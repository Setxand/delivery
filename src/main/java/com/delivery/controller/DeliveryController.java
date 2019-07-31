package com.delivery.controller;

import com.delivery.dto.CreateOrderingDTO;
import com.delivery.dto.UserDTO;
import com.delivery.repository.UserRepository;
import com.delivery.security.Auth;
import com.delivery.service.OrderingService;
import com.delivery.util.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DeliveryController {

	@Autowired UserRepository userRepository;
	@Autowired OrderingService orderingService;

	@GetMapping("/v1/users")
	public List<UserDTO> getUsers() {
		Auth.courier();
		return userRepository.findAll().stream().map(DtoUtils::user).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/v1/orders")
	public void createOrdering(@Valid @RequestBody CreateOrderingDTO dto) {
		Auth.user();
		orderingService.createOrdering(dto);
	}

	@GetMapping("/v1/users/{userId}/orders")
	public List<CreateOrderingDTO> getUserOrderings(@PathVariable String userId) {
		return orderingService
				.listOrderingsByUser(userId).stream().map(DtoUtils::ordering).collect(Collectors.toList());
	}

}
