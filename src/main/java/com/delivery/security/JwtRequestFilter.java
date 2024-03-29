package com.delivery.security;


import com.delivery.model.User;
import com.delivery.service.LoginSessionService;
import com.delivery.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class JwtRequestFilter extends GenericFilterBean {

	private final JwtUserDetails jwtUserDetails;
	private final JwtTokenUtil jwtTokenUtil;
	private final LoginSessionService sessionService;
	private final UserService userService;

	public JwtRequestFilter(JwtUserDetails jwtUserDetails, JwtTokenUtil jwtTokenUtil,
							LoginSessionService sessionService, UserService userService) {
		this.jwtUserDetails = jwtUserDetails;
		this.jwtTokenUtil = jwtTokenUtil;
		this.sessionService = sessionService;
		this.userService = userService;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		final String requestTokenHeader = ((HttpServletRequest) request).getHeader("Authorization");

		String userId = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				userId = jwtTokenUtil.getuserIdFromToken(jwtToken);

				if (!sessionService.validateSession(jwtToken)) throw new AccessDeniedException("Token is disabled");

			} catch (IllegalArgumentException e) {
				throw new AccessDeniedException("Unable to get JWT Token");
			}
		}

		createAuthentication(userId, jwtToken, (HttpServletRequest) request);
		chain.doFilter(request, response);
	}

	private void createAuthentication(String userId, String jwtToken, HttpServletRequest request) {
		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			User user = userService.getUser(userId);
			UserDetails userDetails = this.jwtUserDetails.createUserDetails(user);
//			if (jwtTokenUtil.validateToken(jwtToken, user)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userId, userDetails, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
//			}
		}
	}
}