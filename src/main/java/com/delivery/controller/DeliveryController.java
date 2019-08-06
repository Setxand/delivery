package com.delivery.controller;

import com.delivery.dto.CreateOrderingDTO;
import com.delivery.dto.UpdateOrderingDTO;
import com.delivery.dto.UserDTO;
import com.delivery.model.User;
import com.delivery.security.Auth;
import com.delivery.service.OrderingService;
import com.delivery.service.UserService;
import com.delivery.util.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DeliveryController {

	@Autowired UserService userService;
	@Autowired OrderingService orderingService;

	@GetMapping("/v1/users")
	public List<UserDTO> getUsers() {
		Auth.courier();
		return userService.getUsersByRole(User.Role.USER).stream().map(DtoUtils::user).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/v1/orders")
	public void createOrdering(@Valid @RequestBody CreateOrderingDTO dto) {
		Auth.courier();
		orderingService.createOrdering(dto);
	}

//	@GetMapping("/v1/users/{userId}/orders")
//	public List<CreateOrderingDTO> getUserOrderings(@PathVariable String userId) {
//		return orderingService
//				.listOrderingsByUser(userId).stream().map(DtoUtils::ordering).collect(Collectors.toList());
//	}

	@GetMapping("/v1/orders")
	public List<CreateOrderingDTO> getUserOrderings() {
		return orderingService
				.listOrderingsByUser().stream().map(DtoUtils::ordering).collect(Collectors.toList());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/v1/orders/{orderId}")
	public void updateOrdering(@PathVariable String orderId, @RequestBody UpdateOrderingDTO dto) {
		Auth.courier();
		dto.orderingId = orderId;
		orderingService.updateOrdering(dto);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/v1/orders/{orderId}")
	public void deleteOrdering(@PathVariable String orderId) {
		orderingService.deleteOrdering(orderId);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/v1/users/{userId}")
	public void deleteUser(@PathVariable String userId) {
		Auth.admin();
		userService.deleteUser(userId);
	}
}
