package com.delivery.controller;


import com.delivery.exception.ErrorResponse;
import com.delivery.security.dto.JwtRequest;
import com.delivery.security.dto.JwtResponse;
import com.delivery.security.JwtTokenUtil;
import com.delivery.service.JwtUserDetailsService;
import com.delivery.service.LoginSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private JwtUserDetailsService userDetailsService;
	@Autowired private PasswordEncoder encoder;
	@Autowired LoginSessionService sessionService;

	@PostMapping("/signup")
	public void signUp(@RequestParam String password) {
		String encode = encoder.encode(password);
	}

	@DeleteMapping("/exit")
	public void logOut(@RequestHeader("Authorization") String token) {
		sessionService.logout(token.substring(7));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.email, authenticationRequest.password);
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.email);

		final String token = jwtTokenUtil.generateToken(userDetails);
		sessionService.createSession(authenticationRequest.email, token);
		return ResponseEntity.ok(new JwtResponse(token));
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

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AccessDeniedException.class)
	public ErrorResponse accessDenied(AccessDeniedException ex) {
		return new ErrorResponse(ex.getMessage(), "ACCESS_DENIED");
	}
}
