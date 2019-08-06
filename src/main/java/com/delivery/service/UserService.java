package com.delivery.service;

import com.delivery.dto.UserDTO;
import com.delivery.model.User;
import com.delivery.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
		Optional<User> user = userRepo.findByEmail(dto.email);

		if (user.isPresent()) throw new
				AccessDeniedException(String.format("User with address %s already exists", dto.email));

		User u = new User();

		u.setEmail(dto.email);
		u.setPassword(encoder.encode(dto.password));
		u.setName(dto.name);
		u.setRole(dto.role);
		return userRepo.saveAndFlush(u);
	}

	@Transactional
	public void deleteUser(String userId) {
		userRepo.deleteById(userId);
	}

	@PostConstruct
	public void createAdmin() {
		List<User> usersByRole = getUsersByRole(User.Role.ADMIN);

		if (usersByRole.isEmpty()) {
			User u = new User();
			u.setRole(User.Role.ADMIN);
			u.setEmail("admin@delivery.com");
			u.setPassword(encoder.encode("1111"));
			userRepo.saveAndFlush(u);
		}
	}

	public List<User> getUsersByRole(User.Role role) {
		return userRepo.findByRole(role);
	}
}
