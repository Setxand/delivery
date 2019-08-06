package com.delivery.controller;


import com.delivery.dto.UserDTO;
import com.delivery.model.User;
import com.delivery.security.JwtTokenUtil;
import com.delivery.security.dto.JwtRequest;
import com.delivery.security.dto.JwtResponse;
import com.delivery.service.LoginSessionService;
import com.delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

	@Autowired LoginSessionService sessionService;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired JwtTokenUtil jwtTokenUtil;
	@Autowired UserService userService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/signup")
	public JwtResponse signUp(@RequestBody UserDTO dto) {
		User user = userService.creteUser(dto);
		String token = createSession(user);
		return new JwtResponse(token);
	}

	@DeleteMapping("/logout")
	public void logOut(@RequestHeader("Authorization") String token) {
		sessionService.logout(token.substring(7));
	}

	@ResponseStatus(HttpStatus.ACCEPTED)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.email, authenticationRequest.password);
		User user = userService.findByEmail(authenticationRequest.email);

		final String token = createSession(user);
		return new JwtResponse(token);
	}

	private String createSession(User user) {
		String token = jwtTokenUtil.generateToken(user);
		sessionService.createSession(user.getEmail(), token);
		return token;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
