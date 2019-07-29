package com.delivery.service;

import com.delivery.model.LoginSession;
import com.delivery.repository.LoginSessionRepository;
import com.delivery.security.JwtTokenUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginSessionService {

	private final LoginSessionRepository sessionRepo;

	public LoginSessionService(LoginSessionRepository sessionRepo) {
		this.sessionRepo = sessionRepo;
	}

	@Transactional
	public LoginSession createSession(String email, String token) {
		LoginSession loginSession = new LoginSession(email);
		loginSession.setId(token);
		return sessionRepo.saveAndFlush(loginSession);
	}

	public boolean validateSession(String sessionId) {
		LoginSession session = sessionRepo.findById(sessionId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Session ID"));

		return !session.isDisabled();
	}

	@Transactional
	public void logout(String token) {
		sessionRepo.findById(token)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Token ID"))
					.setDisabled(true);
	}
}
