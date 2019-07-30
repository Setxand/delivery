package com.delivery.security;

import com.delivery.model.User;
import com.delivery.repository.UserRepository;
import com.delivery.security.JwtTokenUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Component
public class JwtUserDetails implements UserDetailsService  {

	private final UserRepository userRepo;
	private final JwtTokenUtil jwtTokenUtil;

	public JwtUserDetails(UserRepository userRepo, JwtTokenUtil jwtTokenUtil) {
		this.userRepo = userRepo;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid User email"));

		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.authorities(getUserAuthorities(user)).build();

	}

	private List<GrantedAuthority> getUserAuthorities(User user) {
		List<GrantedAuthority> result = new ArrayList<>();
		result.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		return result;
	}
}
