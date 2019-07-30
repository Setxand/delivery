package com.delivery.security;


import com.delivery.service.LoginSessionService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final JwtUserDetails jwtUserDetails;
	private final JwtTokenUtil jwtTokenUtil;
	private final LoginSessionService sessionService;

	public JwtRequestFilter(JwtUserDetails jwtUserDetails, JwtTokenUtil jwtTokenUtil,
							LoginSessionService sessionService) {
		this.jwtUserDetails = jwtUserDetails;
		this.jwtTokenUtil = jwtTokenUtil;
		this.sessionService = sessionService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain) throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String email = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				email = jwtTokenUtil.getUsernameFromToken(jwtToken);

				if (!sessionService.validateSession(jwtToken)) throw new AccessDeniedException("Token is disabled");

			} catch (IllegalArgumentException e) {
				throw new AccessDeniedException("Unable to get JWT Token");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		createAuthentication(email, jwtToken, request);
		chain.doFilter(request, response);
	}

	private void createAuthentication(String email, String jwtToken, HttpServletRequest request) {
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetails.loadUserByUsername(email);

			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
	}
}