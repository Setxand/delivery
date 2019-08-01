package com.delivery.service;

import com.delivery.dto.UserDTO;
import com.delivery.model.User;
import com.delivery.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder encoder;

	public UserService(UserRepository userRepo, PasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.encoder = encoder;
	}


	public User findByEmail(String email) {
		return userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid User Email"));
	}

	public User getUser(String userId) {
		return userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
	}

	@Transactional
	public User creteUser(UserDTO dto) {
		User user = new User();
		user.setEmail(dto.email);
		user.setPassword(encoder.encode(dto.password));
		user.setName(dto.name);
		user.setRole(dto.role);
		return userRepo.saveAndFlush(user);
	}
}
